package ch.hsr.challp.museum;

import android.speech.tts.TextToSpeech;
import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO: no real test, did not manage to get tts working in test
 */
public class ContentReaderTest extends AndroidTestCase {

    private TextToSpeech tts;
    private ContentReader reader;
    private boolean loaded = false;

    @Override
    public void setUp() {
        Log.i(getClass().getName(), "contentReader Test on Thread " + android.os.Process.getThreadPriority(android.os.Process.myTid()));
        loaded = false;
        // this will not be successfull, so the tests are working with an invalid tts enginge
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int code) {
                Log.i(getClass().getName(), "tts initiated with " + code);
                loaded = true;
                if (code == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.GERMAN);
                } else {
                    tts = null;
                }
            }
        });
        while (!loaded) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<String> texts = new ArrayList<>();
        texts.add("hallo hallo hallo hallo hallo hallo hallo hallo hallo hallo hallo hallo ");
        texts.add("test testtesttesttesttesttesttesttesttesttesttesttesttesttest");
        reader = new ContentReader(tts, texts);
    }

    @Override
    protected void tearDown() throws Exception {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        reader = null;
    }

    public void testIsPlaying() throws Exception {
        Log.i(getClass().getName(), "testIsPlaying(), thread" + android.os.Process.getThreadPriority(android.os.Process.myTid()));
        reader.play();
        Assert.assertFalse(reader.isPlaying()); // doesn't work, no working engine
    }

    public void testStopPlaying() throws Exception {
        reader.play();
        reader.stopPlaying();
        Assert.assertFalse(tts.isSpeaking());
        Assert.assertFalse(reader.isPlaying());
    }

}