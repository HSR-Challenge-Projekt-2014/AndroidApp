package ch.hsr.challp.museum.model;

import java.util.Arrays;
import java.util.List;

public class Room {

    public static final Room ALL_ROOMS = new Room(0, "Alle");
    public static final Room ONE = new Room(1, "Raum 1");
    public static final Room TWO = new Room(2, "Raum 2");
    private static final Room THREE = new Room(3, "Raum 3");
    private static final List<Room> ALL = Arrays.asList(ALL_ROOMS, ONE, TWO, THREE);
    private final Integer id;
    private final String name;

    private Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Room> getAll() {
        return ALL;
    }

    public static Room findById(Integer id) {
        for (Room room : ALL) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (!id.equals(room.id)) return false;
        if (name != null ? !name.equals(room.name) : room.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}


