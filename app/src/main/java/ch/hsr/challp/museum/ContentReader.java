package ch.hsr.challp.museum;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.List;

public class ContentReader {
    public static final int DELAY_MILLIS = 100;
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
                }
            }
        };
        handler.postAtFrontOfQueue(runnable);
    }

}
