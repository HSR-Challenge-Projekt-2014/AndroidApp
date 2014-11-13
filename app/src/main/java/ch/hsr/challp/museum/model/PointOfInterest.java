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
    private final List<Content> contents;
    private final BeaconReference beacon;

    public PointOfInterest(int id, String title, int headerResource, List<Content> contents, BeaconReference beacon) {
        this.id = id;
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
        List<Content> contents = new ArrayList<>();
        contents.add(new Content("Abenteuerliches", R.drawable.content_abenteuer, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        contents.add(new Content("Historisches", R.drawable.content_historisches, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        contents.add(new Content("Spass und Spannung", R.drawable.content_spass, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        contents.add(new Content("Liebesleben", R.drawable.content_liebensleben, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        contents.add(new Content("Energie", R.drawable.content_energie, R.drawable.bear_face, "Foobar", "Lorem Ipsum blabla", "Raum 1 Topic 2"));
        Region region1 = new Region("kontakt-beacon-01", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("10244"), Identifier.parse("54936"));
        Region region2 = new Region("kontakt-beacon-02", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("31576"), Identifier.parse("38281"));
        PointOfInterest poi1 = new PointOfInterest(1, "Der Bär in St.Ipsum", R.drawable.poi_header, contents, new BeaconReference(region1, Room.ONE));
        PointOfInterest poi2 = new PointOfInterest(2, "Der Bär in Zürich", R.drawable.poi_header, contents, new BeaconReference(region2, Room.ONE));
        ALL.add(poi1);
        ALL.add(poi2);
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
