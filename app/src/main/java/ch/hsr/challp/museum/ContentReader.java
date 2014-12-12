package ch.hsr.challp.museum;

import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import ch.hsr.challp.museum.interfaces.ContentReaderCallback;

public class ContentReader {

    public static final int DELAY_MILLIS = 100;
    private TextToSpeech tts;
    private List<String> stringsToSpeech;
    private ContentReaderCallback callback;
    private boolean isPlaying = false;
    private Handler handler;
    private Runnable runnable;

    public ContentReader(final Context context, List<String> stringsToSpeech,
            ContentReaderCallback callback) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int code) {
                if (code == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.GERMAN);
                } else {
                    tts = null;
                    Toast.makeText(context,
                            context.getString(R.string.tts_starting_not_possible_message),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.stringsToSpeech = stringsToSpeech;
        this.callback = callback;
    }

    public ContentReader(TextToSpeech tts, List<String> texts, ContentReaderCallback callback) {
        this.tts = tts;
        this.stringsToSpeech = texts;
        this.callback = callback;
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
        handler = new Handler();
        runnable = new Runnable() {
            int index = 0;

            @Override
            public void run() {
                isPlaying = true;
                if (tts == null) {
                    isPlaying = false;
                    return;
                }

                if (!tts.isSpeaking()) {
                    if (index < stringsToSpeech.size()) {
                        Log.d(getClass().getName(), "Speak " + stringsToSpeech.get(index));
                        tts.speak(stringsToSpeech.get(index), TextToSpeech.QUEUE_FLUSH, null);
                        index++;
                    } else {
                        isPlaying = false;
                    }
                }

                if (isPlaying()) {
                    handler.postDelayed(this, DELAY_MILLIS);
                } else {
                    callback.readerCompleted();
                }
            }
        };
        handler.postAtFrontOfQueue(runnable);
    }

    public void shutDown() {
        tts.stop();
        tts.shutdown();
    }
}
