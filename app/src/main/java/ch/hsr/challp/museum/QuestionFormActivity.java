package ch.hsr.challp.museum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ch.hsr.challp.museum.helper.FragmentName;


public class QuestionFormActivity extends Activity {

    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form);
        submit = (Button) findViewById(R.id.button_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.question_success),
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.putExtra(HomeActivity.SECTION, FragmentName.getId(FragmentName.QUESTIONS));
                startActivity(i);
            }
        });
    }

}
