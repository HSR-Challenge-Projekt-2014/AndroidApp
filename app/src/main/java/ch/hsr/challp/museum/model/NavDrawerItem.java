package ch.hsr.challp.museum.model;

public class NavDrawerItem {

    private final String title;
    private final int icon;

    public NavDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }

}
