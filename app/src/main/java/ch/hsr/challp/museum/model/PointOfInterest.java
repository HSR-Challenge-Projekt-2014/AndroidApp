package ch.hsr.challp.museum.model;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.R;

public class PointOfInterest {
    private final static List<PointOfInterest> ALL = new ArrayList<>();
    private final int id;
    private final String title;
    private final int headerResource;
    private final List<Content> contents = new ArrayList<>();
    private final BeaconReference beacon;

    public PointOfInterest(int id, String title, int headerResource, BeaconReference beacon) {
        this.id = id;
        this.title = title;
        this.headerResource = headerResource;
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

    public static PointOfInterest findByBeacon(Beacon beacon) {
        for (PointOfInterest pointOfInterest : ALL) {
            if (beacon.getId3().equals(pointOfInterest.getBeacon().getRegion().getId3())) {
                return pointOfInterest;
            }
        }
        return null;
    }

    public static PointOfInterest findById(int poiId) {
        for (PointOfInterest pointOfInterest : ALL) {
            if (poiId == pointOfInterest.getId()) {
                return pointOfInterest;
            }
        }
        return null;
    }

    public int getId() {
        return id;
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
        /*
            BÄR in St. Gallen
         */
        Region region1 = new Region("kontakt-beacon-01", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("10244"), Identifier.parse("54936"));
        PointOfInterest poi1 = new PointOfInterest(1, "Der Bär in St.Gallen", R.drawable.poi_header_bear, new BeaconReference(region1, Room.ONE));
        poi1.addContent(new Content(Topic.ADVENTURE, R.drawable.content_abenteuer, R.drawable.bear_bittner, "Grizzlys in Alaska", "Endlich ist der bekannte Berner Biologe und Bärenfotograf " +
                "David Bittner aus der einsamen Wildnis in Alaska zurück. Mitgebracht hat er faszinierendes und neues Bildmaterial.", "KaKcjpmnA6c"));
        poi1.addContent(new Content(Topic.HISTORIC, R.drawable.content_historisches, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla"));
        poi1.addContent(new Content(Topic.FUN, R.drawable.content_spass, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla"));
        poi1.addContent(new Content(Topic.LOVE, R.drawable.content_liebensleben, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla"));
        poi1.addContent(new Content(Topic.ENERGY, R.drawable.content_energie, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla"));
        /*
            FUCHS in Zürich
         */
        Region region2 = new Region("kontakt-beacon-02", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("31576"), Identifier.parse("38281"));
        PointOfInterest poi2 = new PointOfInterest(2, "Der Fuchs in Zürich", R.drawable.poi_header_fox, new BeaconReference(region2, Room.TWO));
        poi2.addContent(new Content(Topic.HISTORIC, R.drawable.content_historisches, R.drawable.fox_stats, "Entwicklung der Fuchspopulation im 20. Jahrhundert", "Die Jagdstrecke des Fuchses " +
                "in der Schweiz seit 1930 illustriert die Populationsentwicklung des Fuchses im 20. Jahrhundert: fluktuierende Bestände bis Mitte der 1950er Jahre, anschliessend eine Zunahme, " +
                "die von der Tollwutepizootie unterbrochen wird und schliesslich einen rapiden Anstieg von 1984 bis 1995. Die Jagdstrecke nahm in dieser Zeit um den Faktor 4,0 zu, obwohl die " +
                "Motivation vieler Jäger, Füchse zu schiessen, wegen des Zusammenbruchs der Fellpreise gesunken ist. Parallel zur Zunahme der Jagdstrecke stieg die Zahl der tot gefundenen " +
                "Füchse um den Faktor 3,6 an. Die Fuchspopulation scheint heute die Tragfähigkeit des Lebensraumes erreicht zu haben. \n" +
                "Der starke Anstieg der Fuchspopulation seit den 1980er Jahren ist die Folge der wirksamen Bekämpfung und Ausrottung der " +
                "Tollwut in der Schweiz. Der langfristige Anstieg seit den 1930er Jahren muss aber auf Veränderungen im Lebensraum beruhen. " +
                "Die landwirtschaftliche Produktion hat zum Beispiel zwischen 1930 und 1990 in der Schweiz um das Vierfache zugenommen. Die " +
                "Mechanisierung der Ernte nach dem zweiten Weltkrieg hat zu einer Steigerung des Nahrungsangebotes für Füchse geführt, da mehr " +
                "Feldfrüchte zurückgelassen werden als bei der Handernte. Mit der Zunahme der Fuchspopulationen nach der Tollwutepitzootie wurden " +
                "auch immer häufiger Füchse in urbanen Gebieten beobachte. Einzelne Füchse dürften zwar schon immer in den Wohngebieten des Menschen " +
                "gelebt haben, ab Mitte der 1980er Jahre nahm dieses Phänomen jedoch stark zu.\n"));
        poi2.addContent(new Content(Topic.FUN, R.drawable.content_spass, R.drawable.fox_mouse, "Mit Feigen fängt man Füchse", "Dass er Dörrobst, Datteln, getrocknete " +
                "Feigen und Bananen, Baumnüsse und sogar Schokolade als Köder auslegt, erstaunt den Laien, den Fachmann wundert's nicht. Denn Füchse sind Allesfresser, " +
                "die nicht nur Gänse, Hühner und Kaninchen stehlen, sondern sich in freier Wildbahn auch von Beeren, Nüssen und Obst ernähren. " +
                "Sie sind Hundeartige und von Gebiss und Verdauungssystem her nicht ausschliesslich auf den Verzehr von tierischem Eiweiss ausgelegt.\n" +
                "\n", "rTyrTn9VXmQ"));
        ALL.add(poi1);
        ALL.add(poi2);
    }

    private void addContent(Content content) {
        contents.add(content);
        content.setRoom(beacon.getRoom());
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
