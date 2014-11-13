package ch.hsr.challp.museum.model;

import java.util.Arrays;
import java.util.List;

public class Room {

    public static Room ONE = new Room("Raum 1");
    public static Room TWO = new Room("Raum 2");
    public static Room ALL_ROOMS = new Room("Alle");
    private static Room THREE = new Room("Raum 3");
    private static List<Room> ALL = Arrays.asList(ALL_ROOMS, ONE, TWO, THREE);
    private String name;

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
