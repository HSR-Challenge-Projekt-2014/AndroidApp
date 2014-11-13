package ch.hsr.challp.museum;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.challp.museum.model.Content;


public class ContentActivity extends Activity {

    public static final String P_CONTENT_ID = "ContentId";

    public ContentActivity() {
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }
}
