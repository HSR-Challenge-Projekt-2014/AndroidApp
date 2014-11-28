package ch.hsr.challp.museum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ch.hsr.challp.museum.interfaces.ContentReaderCallback;
import ch.hsr.challp.museum.model.Question;


public class QuestionActivity extends Activity implements ContentReaderCallback {

    public static final String P_QUESTION_ID = "QuestionId";

    private ContentReader contentReader;

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayList<String> texts = new ArrayList<>();
        texts.add(((TextView) findViewById(R.id.page_title)).getText().toString());
        texts.add(((TextView) findViewById(R.id.page_text)).getText().toString());
        contentReader = new ContentReader(this, texts, this);
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
        getMenuInflater().inflate(R.menu.menu_question, menu);

        invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (contentReader.isPlaying()) {
            menu.findItem(R.id.action_play_content).setEnabled(false).setVisible(false);
            menu.findItem(R.id.action_play_stop).setEnabled(true).setVisible(true);
        } else {
            menu.findItem(R.id.action_play_content).setEnabled(true).setVisible(true);
            menu.findItem(R.id.action_play_stop).setEnabled(false).setVisible(false);
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
            Intent i = new Intent(getApplicationContext(), QuestionFormActivity.class);
            startActivity(i);
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
        if (contentReader != null) {
            contentReader.shutDown();
        }
        super.onStop();
    }
}
