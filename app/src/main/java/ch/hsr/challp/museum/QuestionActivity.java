package ch.hsr.challp.museum;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import ch.hsr.challp.museum.interfaces.ContentReaderCallback;
import ch.hsr.challp.museum.model.Question;


public class QuestionActivity extends Activity implements ContentReaderCallback {

    public static final String P_QUESTION_ID = "QuestionId";

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
        setContentView(R.layout.activity_question);
        setTitle("Beantwortete Frage");

        Integer questionId = getIntent().getIntExtra(P_QUESTION_ID, 0);
        Question question = Question.getById(questionId);

        TextView title = (TextView) findViewById(R.id.page_title);
        title.setText(question.getTitle());
        ImageView image = (ImageView) findViewById(R.id.page_image);
        image.setImageResource(question.getImage());
        TextView text = (TextView) findViewById(R.id.page_text);
        text.setText(question.getText());
        TextView topic = (TextView) findViewById(R.id.page_preview_description);
        topic.setText(question.getTopic().getName());
        TextView location = (TextView) findViewById(R.id.page_preview_location);
        location.setText(question.getRoom().getName());
        ImageView previewImage = (ImageView) findViewById(R.id.page_preview_image);
        previewImage.setImageResource(R.drawable.bear_drawing); // TODO: create preview image
        // TODO add drawer to this activity
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (contentReader.isPlaying()) {
            getMenuInflater().inflate(R.menu.menu_question_playing, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_question, menu);
        }

        return true;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_ask_question) {
            // TODO add question form
            Log.w(getClass().getName(), "question form not yet implemented");
            return true;
        } else if (id == R.id.action_play_content) {
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

    @Override
    protected void onStop() {
        // Important, shut the tts service after leaving the activity
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onStop();
    }
}
