package ch.hsr.challp.museum;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.challp.museum.model.Content;


public class ContentActivity extends Activity {

    private Content content;
    public static final String P_CONTENT_ID = "ContentId";

    public ContentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);
        content = getIntent().getParcelableExtra(P_CONTENT_ID);

        ((ImageView) findViewById(R.id.contentPreviewImage)).setImageResource(content.getPreviewImageResource());
        ((TextView) findViewById(R.id.contentPreviewDescription)).setText(content.getPreviewTitle());
        ((TextView) findViewById(R.id.contentTitle)).setText(content.getTitle());
        ((TextView) findViewById(R.id.contentText)).setText(content.getContentText());
        ((ImageView) findViewById(R.id.contentImage)).setImageResource(content.getImageResource());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
