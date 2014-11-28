package ch.hsr.challp.museum;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
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

import ch.hsr.challp.museum.interfaces.ContentReaderCallback;
import ch.hsr.challp.museum.model.Content;


public class ContentActivity extends Activity implements YouTubePlayer.OnInitializedListener, ContentReaderCallback {

    public static final String P_CONTENT = "ContentId";
    public static final String API_KEY = "AIzaSyB3Lk1ZU2K9ozvL0rrHjK6qa2xMxiim8gM";
    private ContentReader contentReader;
    private String youTubeId;
    private Content content;

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
        setContentView(R.layout.activity_content);
        content = getIntent().getParcelableExtra(P_CONTENT);

        ((ImageView) findViewById(R.id.page_preview_image)).setImageResource(content.getPreviewImageResource());
        ((TextView) findViewById(R.id.page_preview_description)).setText(content.getTopic().getName());
        ((TextView) findViewById(R.id.page_preview_location)).setText(content.getRoom().getName());
        ((TextView) findViewById(R.id.page_title)).setText(content.getTitle());
        ((TextView) findViewById(R.id.page_text)).setText(content.getContentText());
        ((ImageView) findViewById(R.id.page_image)).setImageResource(content.getImageResource());

        if (content.hasYouTubeVideo()) {
            YouTubePlayerFragment youtubeFragment = new YouTubePlayerFragment();
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
        getMenuInflater().inflate(R.menu.menu_content, menu);

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
        if (content != null && Content.getSavedContents().contains(content)) {
            menu.findItem(R.id.action_read_later).setEnabled(false).setVisible(false);
            menu.findItem(R.id.action_remove_read_later).setEnabled(true).setVisible(true);
        } else {
            menu.findItem(R.id.action_read_later).setEnabled(true).setVisible(true);
            menu.findItem(R.id.action_remove_read_later).setEnabled(false).setVisible(false);
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
        if (contentReader != null) {
            contentReader.shutDown();
        }
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_play_content) {
            Log.d(getClass().getName(), "play text2speech Button clicked");
            toggleText2Speech();
        } else if (id == R.id.action_play_stop) {
            Log.d(getClass().getName(), "stop text2speech Button clicked");
            toggleText2Speech();
        } else if (id == R.id.action_read_later) {
            Log.d(getClass().getName(), "read later button clicked");
            Content.saveContent(this.content);
            Toast toast = Toast.makeText(this, content.getTitle() + getString(R.string.read_later_saved), Toast.LENGTH_LONG);
            toast.show();
        } else if (id == R.id.action_remove_read_later) {
            Log.d(getClass().getName(), "remove read later");
            Content.removeSavedContent(content);
            Toast toast = Toast.makeText(this, content.getTitle() + getString(R.string.read_later_removed), Toast.LENGTH_SHORT);
            toast.show();
        }
        invalidateOptionsMenu();
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
