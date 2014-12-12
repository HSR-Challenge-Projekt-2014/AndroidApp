package ch.hsr.challp.museum.model;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.R;

public class Question {

    private static final List<Question> all = new ArrayList<>();

    static {
        all.add(new Question(6, "Warum pinkeln Pandabären im Handstand?",
                "Pandas pinkeln tatsächlich im "
                        + "Handstand, damit die Duftmarke weiter oben ist. Während der Paarungszeit täuscht "
                        + "das Männlein damit Größe vor. Die weiblichen Pandas lassen sich davon täuschen und "
                        + "glaube es handelt sich um einen großen männlichen Pandabären.\n"
                        + "\n", R.drawable.panda,
                Topic.FUN, Room.ONE));
        all.add(new Question(1, "Warum haben Hummer blaues Blut?",
                "Hämocyanin ist der blaue Blutfarbstoff der "
                        + "Gliederfüßer (u. a. Krebse; bei Insekten, Spinnen, die Tracheen besitzen, tritt es in "
                        + "geringerem Maße auf) und Weichtiere (u. a. Muscheln, Schnecken und Tintenfische). "
                        + "Er dient dem Sauerstofftransport.\n\n"
                        + "Anders als beim roten, eisenhaltigen Hämoglobin wird der Sauerstoff im Hämocyanin von "
                        + "zwei Kupfer-Ionen gebunden. Außerdem besitzt Hämocyanin keine Porphyrin-Struktur wie "
                        + "Häm sondern die Kupferionen sind über Aminosäure-Reste (Histidin) an das Protein gebunden. "
                        + "Das Kupfer verleiht dem Hämocyanin auch seine blaue Farbe.\n\n"
                        + "Die Bindung des Sauerstoff ist stärker als beim Hämoglobin. Im Vergleich zum Hämoglobin "
                        + "hat das Hämocyanin eine wesentlich stärkere Sauerstoffbindungskapazität, was zu einer "
                        + "geringeren Transportkapazität für Sauerstoff führt.",
                R.drawable.hummer, Topic.FUN, Room.ONE));
        all.add(new Question(2,
                "Wie entstehen Fossilien?",
                "Im Laufe der Zeit wird der tote Körper immer tiefer eingegraben, da sich neue Sedimentschichten auf "
                        + "ihn legen. Durch den so entstehenden Druck wird Schlamm und Sand langsam zu Stein zusammengepresst. "
                        + "Der steigende Druck wirkt sich auch auf die eingegrabenen Reste der Lebewesen aus. Diese werden "
                        + "dadurch flachgedrückt und alles Wasser wird aus ihnen herausgepresst. Durch den Boden sickert "
                        + "Wasser mit Stoffen, die alle noch übrigen organischen Bestandteile des Körpers auflösen und durch "
                        + "Mineralien ersetzen – man nennt dies Umkristallisation. So entsteht eine „Steinkopie“ der "
                        + "Überreste, eine Versteinerung, die dann irgendwann nach vielen Jahren vielleicht wieder sichtbar "
                        + "wird und von einem scharfäugigen Fossiliensammler gefunden werden kann.",
                R.drawable.fossil, Topic.HISTORIC, Room.ONE));
        all.add(new Question(3, "Wie sind die Alpen entstanden?", "Die Alpen entstanden durch das "
                + "Zusammenprallen der beiden Kontinente Afrika und Europa vor etwa 25 Millionen Jahren. "
                + "Die Kontinente sahen damals noch anders aus; die adriatische Platte, die heute Teile "
                + "Italiens, des Balkans, der Alpen und des adriatischen Meers bildet, war noch Teil von Afrika, "
                + "und wurde während Jahrmillionen wie ein Keil in die Südküste von Europa getrieben. Durch dieses "
                + "Zusammenprallen falteten sich die Ränder der afrikanischen und der europäischen Platte dort auf, "
                + "wo heute die Alpen sind.",
                R.drawable.alpen, Topic.HISTORIC, Room.TWO));
        all.add(new Question(4, "Kann ich bestimmen, welcher Strom aus meiner Steckdose kommt?",
                "Der gesamte Strom, welcher produziert wird, wird im\n"
                        + "europaweiten Stromnetz zusammengeführt – dem sogenannten\n"
                        + "Stromsee. Aus Ihrer Steckdose fliesst deshalb\n"
                        + "immer ein Mix aus der gesamten produzierten Elektrizität.\n"
                        + "Mit dem Kauf von Ökostrom oder Strom aus erneuerbaren\n"
                        + "Energien können Sie diesen Mix beeinflussen und bestimmen,\n"
                        + "wie der Strom erzeugt wird.",
                R.drawable.naturstrom, Topic.ENERGY, Room.TWO));
        all.add(new Question(5, "Warum sterben so viele Igel auf der Strasse?",
                "Igel leben heute zum grossen Teil im Siedlungsraum, der dicht von Strassen durchzogen ist. "
                        + "Das bedeutet Gefahr. Vor allem Männchen, die während der Paarungszeit weite Strecken "
                        + "zurücklegen um Weibchen aufzusuchen, werden häufig überfahren. Das müsste nicht sein. "
                        + "Igel zeigen nämlich auf der Strasse nicht das für sie typische Einrollverhalten sondern "
                        + "flüchten vor den Autos. Bei angemessenem Tempo der Autofahrer sollte es für Igel häufiger "
                        + "möglich sein, die Strasse erfolgreich zu überqueren.",
                R.drawable.igel_auto, Topic.HISTORIC, Room.TWO));
        all.add(new Question(7, "Kann man von Hand Fische fangen?",
                "Fischen ist eigentlich bekannt als gemütliche und friedliche Angelegenheit. Nicht aber, "
                        + "wenn man sich einen Wels angeln will. Von Hand. Der Süsswasserfisch lebt vor "
                        + "allem in Löchern oder in ruhigen, mit Wasserpflanzen bewachsenen Bereichen. "
                        + "Beim Wels-Fischen muss man mit seiner Hand direkt in diese Löcher greifen. "
                        + "Als gewöhnliche Reaktion schnappt der Fisch zu. Der Trick ist es dann, die "
                        + "Hand sofort in dessen Hals zu stecken und den Fisch aus seinem Loch zu ziehen. "
                        + "Obwohl einige der Fischer Handschuhe tragen, kann es dabei zu üblen Verletzungen "
                        + "kommen. Schlimmer noch: Es besteht die Gefahr, Finger zu verlieren, oder zu ertrinken.",
                R.drawable.noodling, Topic.ADVENTURE, Room.ONE));
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
