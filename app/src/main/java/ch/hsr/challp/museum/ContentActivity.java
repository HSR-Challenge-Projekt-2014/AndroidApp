package ch.hsr.challp.museum;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;
import java.util.Locale;

import ch.hsr.challp.museum.interfaces.ContentReaderCallback;
import ch.hsr.challp.museum.model.Content;


public class ContentActivity extends Activity implements YouTubePlayer.OnInitializedListener, ContentReaderCallback {

    public static final String P_CONTENT_ID = "ContentId";
    public static final String API_KEY = "AIzaSyB3Lk1ZU2K9ozvL0rrHjK6qa2xMxiim8gM";
    private TextToSpeech tts;
    private ContentReader contentReader;
    private YouTubePlayerFragment youtubeFragment;
    private String youTubeId;

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
                    Toast.makeText(getApplicationContext(), getString(R.string.tts_starting_not_possible_message),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        final ArrayList<String> texts = new ArrayList<>();
        texts.add(((TextView) findViewById(R.id.page_title)).getText().toString());
        texts.add(((TextView) findViewById(R.id.page_text)).getText().toString());
        contentReader = new ContentReader(tts, texts, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Content content = getIntent().getParcelableExtra(P_CONTENT_ID);

        ((ImageView) findViewById(R.id.page_preview_image)).setImageResource(content.getPreviewImageResource());
        ((TextView) findViewById(R.id.page_preview_description)).setText(content.getTopic().getName());
        ((TextView) findViewById(R.id.page_preview_location)).setText(content.getRoom().getName());
        ((TextView) findViewById(R.id.page_title)).setText(content.getTitle());
        ((TextView) findViewById(R.id.page_text)).setText(content.getContentText());
        ((ImageView) findViewById(R.id.page_image)).setImageResource(content.getImageResource());

        if (content.hasYouTubeVideo()) {
            youtubeFragment = new YouTubePlayerFragment();
            getFragmentManager().beginTransaction().replace(R.id.page_video, youtubeFragment).commit();
            youtubeFragment.initialize(API_KEY, this);
            youtubeFragment.setRetainInstance(true);
            youTubeId = content.getYouTubeId();
        }

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_activity_content);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (contentReader.isPlaying()) {
            getMenuInflater().inflate(R.menu.menu_content_playing, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_content, menu);
        }
        return true;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(youTubeId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    protected void onStop() {
        // Important, shut the tts service after leaving the activity
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_play_content) {
            Log.d(getClass().getName(), "Text2Speech Button clicked");
            toggleText2Speech();
            invalidateOptionsMenu();
            return true;
        } else if (id == R.id.action_play_stop) {
            toggleText2Speech();
            invalidateOptionsMenu();
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

    @Override
    public void readerCompleted() {
        invalidateOptionsMenu();
    }
}
