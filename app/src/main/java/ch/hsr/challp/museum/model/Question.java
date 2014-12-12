package ch.hsr.challp.museum.model;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.R;

public class Question {

    private static final List<Question> all = new ArrayList<>();

    static {
        String fillText
                = "Eine wunderbare Heiterkeit hat meine ganze Seele eingenommen, gleich den süßen Frühlingsmorgen, die ich mit ganzem Herzen genieße. Ich bin allein und freue mich meines Lebens in dieser Gegend, die für solche Seelen geschaffen ist wie die meine. Ich bin so glücklich, mein Bester, so ganz in dem Gefühle von ruhigem Dasein versunken, daß meine Kunst darunter leidet.";
        all.add(new Question(1, "Warum ist der Bär aus der Schweiz verschwunden?", fillText,
                R.drawable.bear_face, Topic.HISTORIC, Room.ONE));
        all.add(new Question(2,
                "Frisst der Bär auch Erdbeeren oder Himbeeren, sofern sie aus einem biologischen Garten stammen?",
                fillText + " " + fillText, R.drawable.bear_drawing, Topic.FUN, Room.ONE));
        all.add(new Question(3, "Sind die Alpen kalkhaltige Erdkrustenfalten?", fillText,
                R.drawable.bear_drawing, Topic.ADVENTURE, Room.TWO));
        all.add(new Question(4, "Sind die Alpen kalkhaltige Erdkrustenfalten?", fillText,
                R.drawable.bear_drawing, Topic.ADVENTURE, Room.TWO));
        all.add(new Question(5, "Sind die Alpen kalkhaltige Erdkrustenfalten?", fillText,
                R.drawable.bear_drawing, Topic.ADVENTURE, Room.TWO));
    }

    private final int id;
    private final String title;
    private final String text;
    private final int image;
    private final Topic topic;
    private final Room room;

    private Question(int id, String title, String text, int image, Topic topic, Room room) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.topic = topic;
        this.room = room;
    }

    public static List<Question> getAll() {
        return all;
    }

    public static Question getById(Integer id) {
        Question result = null;
        for (Question q : all) {
            if (q.getId() == id) {
                result = q;
                break;
            }
        }
        return result;
    }

    public int getId() {
        return id;
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

    public Topic getTopic() {
        return topic;
    }

    public Room getRoom() {
        return room;
    }
}
