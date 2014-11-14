package ch.hsr.challp.museum.model;

import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.R;

public class PointOfInterest {
    private final static List<PointOfInterest> ALL = new ArrayList<>();
    private final String title;
    private final int headerResource;
    private final List<Content> contents;
    private final BeaconReference beacon;

    public PointOfInterest(String title, int headerResource, List<Content> contents, BeaconReference beacon) {
        this.title = title;
        this.headerResource = headerResource;
        this.contents = contents;
        this.beacon = beacon;
    }

    public static List<PointOfInterest> getAll() {
        return ALL;
    }

    public static List<Region> getAllRegions() {
        List<Region> result = new ArrayList<>();
        for (PointOfInterest poi : ALL) {
            result.add(poi.getBeacon().getRegion());
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public int getHeaderResource() {
        return headerResource;
    }

    public List<Content> getContents() {
        return contents;
    }


    public BeaconReference getBeacon() {
        return beacon;
    }

    static {
        List<Content> contents = new ArrayList<>();
        contents.add(new Content("Abenteuerliches", R.drawable.content_abenteuer, R.drawable.bear_face, "Foobar", "Da ihr gewiß schon die Abenteuer von Tom Sawyer gelesen habt, so brauche ich mich euch nicht vorzustellen. Jenes Buch hat ein gewisser Mark Twain geschrieben und was drinsteht ist wahr – wenigstens meistenteils. Hie und da hat er etwas dazugedichtet, aber das tut nichts. Ich kenne niemand, der nicht gelegentlich einmal ein bißchen lügen täte, ausgenommen etwa Tante Polly oder die Witwe Douglas oder Mary. Toms Tante Polly und seine Schwester Mary und die Witwe Douglas kommen alle in dem Buche vom Tom Sawyer vor, das wie gesagt, mit wenigen Ausnahmen eine wahre Geschichte ist. – Am Ende von dieser Geschichte wird erzählt, wie Tom und ich das Geld fanden, das die Räuber in der Höhle verborgen hatten, wodurch wir nachher sehr reich wurden. Jeder von uns bekam sechstausend Dollars, lauter Gold. Es war ein großartiger Anblick, als wir das Geld auf einem Haufen liegen sahen. Kreisrichter Thatcher bewahrte meinen Teil auf und legte ihn auf Zinsen an, die jeden Tag einen Dollar für mich ausmachen. Ich weiß wahrhaftig nicht, was ich mit dem vielen Geld anfangen soll. Die Witwe Douglas nahm mich als Sohn an und will versuchen, mich zu sievilisieren wie sie sagt. Das schmeckt mir aber schlecht, kann ich euch sagen, das Leben wird mir furchtbar sauer in dem Hause mit der abscheulichen Regelmäßigkeit, wo immer um dieselbe Zeit gegessen und geschlafen werden soll, einen Tag wie den andern. Einmal bin ich auch schon durchgebrannt, bin in meine alten Lumpen gekrochen, und – hast du nicht gesehen, war ich draußen im Wald und in der Freiheit. Tom Sawyer aber, mein alter Freund Tom, spürte mich wieder auf, versprach, er wolle eine Räuberbande gründen und ich solle Mitglied werden, wenn ich noch einmal zu der Witwe zurückkehre und mich weiter ›sievilisieren‹ lasse. Da tat ich's denn.", "Raum 1 Topic 2"));
        contents.add(new Content("Historisches", R.drawable.content_historisches, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        contents.add(new Content("Spass und Spannung", R.drawable.content_spass, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        contents.add(new Content("Liebesleben", R.drawable.content_liebensleben, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        contents.add(new Content("Energie", R.drawable.content_energie, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        Region region1 = new Region("kontakt-beacon-01", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("10244"), Identifier.parse("54936"));
        PointOfInterest poi = new PointOfInterest("Der Bär in St.Ipsum", R.drawable.poi_header, contents, new BeaconReference(region1, Room.ONE));
        //Region region2 = new Region("kontakt-beacon-02", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("31576"), Identifier.parse("38281"));
        ALL.add(poi);
    }

    private static class BeaconReference {
        private Region region;
        private Room room;

        public BeaconReference(Region region, Room room) {
            this.region = region;
            this.room = room;
        }

        public Region getRegion() {
            return region;
        }

        public Room getRoom() {
            return room;
        }
    }
}
