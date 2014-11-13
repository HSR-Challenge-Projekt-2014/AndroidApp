package ch.hsr.challp.museum.model;

import java.util.Arrays;
import java.util.List;

public class Topic {

    public static final Topic ALL_ITEMS = new Topic("Alle");
    public static final Topic ADVENTURE = new Topic("Abenteuerliches");
    public static final Topic HISTORIC = new Topic("Historisches");
    public static final Topic FUN = new Topic("Spass und Spannung");
    private static final List<Topic> ALL = Arrays.asList(ALL_ITEMS, ADVENTURE, HISTORIC, FUN);

    private final String name;

    private Topic(String name) {
        this.name = name;
    }

    public static List<Topic> getAll() {
        return ALL;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
