package ch.hsr.challp.museum.model;

import java.util.List;

public class PointOfInterest {
    private String title;
    private int headerResource;
    private List<Content> contents;

    public PointOfInterest(String title, int headerResource, List<Content> contents) {
        this.title = title;
        this.headerResource = headerResource;
        this.contents = contents;
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
}
