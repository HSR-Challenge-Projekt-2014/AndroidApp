package ch.hsr.challp.museum.helper;

import java.util.HashMap;
import java.util.Map;

import ch.hsr.challp.museum.R;

public enum FragmentName {

    GUIDE(0, R.string.title_companion, R.drawable.ic_guide, false),
    GUIDE_RUNNING(0, R.string.title_companion, -1, true), GUIDE_STOPPED(0, R.string.title_companion, -1, false), POI(0, R.string.title_companion, -1, true),
    QUESTIONS(1, R.string.title_question, R.drawable.ic_question, true),
    READ_LATER(2, R.string.title_read_later, R.drawable.ic_read_later, true),
    ABOUT(3, R.string.title_about, R.drawable.ic_information, true);
    public static FragmentName[] DRAWER_FRAGMENTS = new FragmentName[]{GUIDE, QUESTIONS, READ_LATER, ABOUT};
    private static Map<Integer, FragmentName> ENUM_BY_ID = new HashMap<>();
    private int drawerPosition;
    private int title;
    private int icon;
    private boolean addToBackStack;

    FragmentName(int drawerPosition, int title, int icon, boolean addToBackStack) {
        this.drawerPosition = drawerPosition;
        this.title = title;
        this.icon = icon;
        this.addToBackStack = addToBackStack;
    }

    static {
        Integer idCounter = 0;
        for (FragmentName name : FragmentName.values()) {
            ENUM_BY_ID.put(idCounter++, name);
        }
    }

    public static Integer getId(FragmentName name) {
        for (Map.Entry<Integer, FragmentName> e : ENUM_BY_ID.entrySet()) {
            if (e.getValue() == name) {
                return e.getKey();
            }
        }
        return null;
    }

    public static FragmentName getFragmentName(Integer id) {
        return ENUM_BY_ID.get(id);
    }

    public int getDrawerPosition() {
        return drawerPosition;
    }

    public int getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public boolean addToBackStack() {
        return addToBackStack;
    }

}
