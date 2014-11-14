package ch.hsr.challp.museum.model;

import java.util.Arrays;
import java.util.List;

public class Topic {

    public static final Topic ALL_ITEMS = new Topic(1, "Alle");
    public static final Topic ADVENTURE = new Topic(2, "Abenteuerliches");
    public static final Topic HISTORIC = new Topic(3, "Historisches");
    public static final Topic FUN = new Topic(4, "Spass und Spannung");
    public static final Topic LOVE = new Topic(5, "Liebesleben");
    public static final Topic ENERGY = new Topic(6, "Energie");
    private static final List<Topic> ALL = Arrays.asList(ALL_ITEMS, ADVENTURE, HISTORIC, FUN, LOVE, ENERGY);
    private final String name;
    private Integer id;

    private Topic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Topic> getAll() {
        return ALL;
    }

    public static Topic findById(Integer id) {
        for (Topic topic : ALL) {
            if (topic.getId().equals(id)) {
                return topic;
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
