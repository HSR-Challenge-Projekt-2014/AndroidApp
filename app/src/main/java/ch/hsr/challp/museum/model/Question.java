package ch.hsr.challp.museum.model;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.R;

/**
 * Created by Michi on 12.11.2014.
 */
public class Question {
    private static List<Question> all = new ArrayList<Question>();
    private static String LOREM_IPSUM = "Eine wunderbare Heiterkeit hat meine ganze Seele eingenommen, gleich den süßen Frühlingsmorgen, die ich mit ganzem Herzen genieße. Ich bin allein und freue mich meines Lebens in dieser Gegend, die für solche Seelen geschaffen ist wie die meine. Ich bin so glücklich, mein Bester, so ganz in dem Gefühle von ruhigem Dasein versunken, daß meine Kunst darunter leidet.";

    static {
        all.add(new Question("Warum ist der Bär aus der Schweiz verschwunden?", LOREM_IPSUM, R.drawable.bear_face));
        all.add(new Question("Frisst der Bär auch Erdbeeren oder Himbeeren, sofern sie aus einem biologischen Garten stammen?", LOREM_IPSUM + " " + LOREM_IPSUM, R.drawable.bear_drawing));
        all.add(new Question("Sind die Alpen kalkhaltige Erdkrustenfalten?", LOREM_IPSUM, R.drawable.bear_drawing));
    }

    private String title;
    private String text;
    private int image;

    public Question(String title, String text, int image) {
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public static List<Question> getAll() {
        return all;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }

}
