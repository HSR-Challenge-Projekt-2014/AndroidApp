package ch.hsr.challp.museum;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ch.hsr.challp.museum.model.Content;


public class ContentActivity extends Activity {

    public static final String P_CONTENT_ID = "ContentId";
    private TextToSpeech tts;
    private ContentReader contentReader;

    @Override
    protected void onStart() {
        super.onStart();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int code) {
                if (code == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.GERMAN);
                } else {
                    tts = null;
                    Toast.makeText(getApplicationContext(), "Text2Speech kann nicht gestartet werden, bitte überprüfen Sie Ihre Einstellungen.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        final ArrayList<String> texts = new ArrayList<>();
        texts.add(((TextView) findViewById(R.id.page_title)).getText().toString());
        texts.add(((TextView) findViewById(R.id.page_text)).getText().toString());
        contentReader = new ContentReader(tts, texts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Content content = getIntent().getParcelableExtra(P_CONTENT_ID);

        ((ImageView) findViewById(R.id.page_preview_image)).setImageResource(content.getPreviewImageResource());
        ((TextView) findViewById(R.id.page_preview_description)).setText(content.getPreviewTitle());
        ((TextView) findViewById(R.id.page_preview_location)).setText(content.getPreviewLocation());
        ((TextView) findViewById(R.id.page_title)).setText(content.getTitle());
        ((TextView) findViewById(R.id.page_text)).setText(content.getContentText());
        ((ImageView) findViewById(R.id.page_image)).setImageResource(content.getImageResource());

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_activity_content);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    protected void onStop() {
        // Important, shut the tts service after leaving the activity
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_play_content) {
            Log.d(getClass().getName(), "Text2Speech Button clicked");
            toggleText2Speech();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleText2Speech() {
        if (contentReader.isPlaying()) {
            contentReader.stopPlaying();
        } else {
            contentReader.play();
        }
    }


    private class ContentReader {

        private TextToSpeech tts;
        private List<String> stringsToSpeech;
        private boolean isPlaying = false;
        private Handler handler;
        private Runnable runnable;

        public ContentReader(TextToSpeech tts, List<String> stringsToSpeech) {
            this.tts = tts;
            this.stringsToSpeech = stringsToSpeech;
        }

        public boolean isPlaying() {
            return isPlaying;
        }

        public void stopPlaying() {
            handler.removeCallbacks(runnable);
            if (tts != null) {
                tts.stop();
            }
            isPlaying = false;
        }

        public void play() {
            isPlaying = true;
            handler = new Handler();
            runnable = new Runnable() {
                int index = 0;

                @Override
                public void run() {
                    if (tts != null && index < stringsToSpeech.size()) {
                        if (!tts.isSpeaking()) {
                            tts.speak(stringsToSpeech.get(index), TextToSpeech.QUEUE_FLUSH, null);
                            index++;
                        }
                        handler.postDelayed(this, 100);
                    } else {
                        isPlaying = false;
                    }
                }
            };
            handler.postDelayed(runnable, 100);
        }

    }
}


