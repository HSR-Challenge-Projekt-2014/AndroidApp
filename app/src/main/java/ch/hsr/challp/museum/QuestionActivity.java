package ch.hsr.challp.museum;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.challp.museum.model.Question;


public class QuestionActivity extends Activity {

    public static final String P_QUESTION_ID = "QuestionId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setTitle("Beantwortete Frage");

        Integer questionId = getIntent().getIntExtra(P_QUESTION_ID, 0);
        Question question = Question.getById(questionId);

        TextView title = (TextView) findViewById(R.id.question_title);
        title.setText(question.getTitle());
        ImageView image = (ImageView) findViewById(R.id.question_image);
        image.setImageResource(question.getImage());
        TextView text = (TextView) findViewById(R.id.question_text);
        text.setText(question.getText());
        TextView topic = (TextView) findViewById(R.id.question_topic);
        topic.setText(question.getTopic().getName());
        TextView location = (TextView) findViewById(R.id.question_location);
        location.setText(question.getRoom().getName());
        // TODO add drawer to this activity
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ask_question) {
            // TODO add question form
            Log.w(getClass().getName(), "question form not yet implemented");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
