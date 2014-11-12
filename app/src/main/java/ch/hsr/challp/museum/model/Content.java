package ch.hsr.challp.museum.model;

public class Content {

    private int presentationImageResource;
    private String presentationTitle;


    public Content(String presentationTitle, int presentationImageResource) {
        this.presentationImageResource = presentationImageResource;
        this.presentationTitle = presentationTitle;
    }

    public int getPresentationImageResource() {
        return presentationImageResource;
    }

    public String getPresentationTitle() {
        return presentationTitle;
    }

}
