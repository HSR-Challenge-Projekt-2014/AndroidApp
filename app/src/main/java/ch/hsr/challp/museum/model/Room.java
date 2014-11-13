package ch.hsr.challp.museum.model;

import java.util.Arrays;
import java.util.List;

public class Room {

    public static final Room ONE = new Room("Raum 1");
    public static final Room TWO = new Room("Raum 2");
    public static final Room ALL_ROOMS = new Room("Alle");
    private static final Room THREE = new Room("Raum 3");
    private static final List<Room> ALL = Arrays.asList(ALL_ROOMS, ONE, TWO, THREE);
    private final String name;

    private Room(String name) {
        this.name = name;
    }

    public static List<Room> getAll() {
        return ALL;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
