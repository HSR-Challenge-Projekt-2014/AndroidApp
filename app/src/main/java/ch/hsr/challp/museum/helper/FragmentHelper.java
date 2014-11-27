package ch.hsr.challp.museum.helper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import ch.hsr.challp.museum.AboutFragment;
import ch.hsr.challp.museum.DetailFragment;
import ch.hsr.challp.museum.GuideFragment;
import ch.hsr.challp.museum.GuideRunningFragment;
import ch.hsr.challp.museum.GuideStoppedFragment;
import ch.hsr.challp.museum.PointOfInterestFragment;
import ch.hsr.challp.museum.QuestionFragment;
import ch.hsr.challp.museum.R;

public class FragmentHelper {

    public static void show(FragmentActivity activity, FragmentManager fragmentManager, FragmentName name, Integer poiId) {
        Fragment fragment = null;
        if (name == FragmentName.GUIDE) {
            fragment = new GuideFragment();
        } else if (name == FragmentName.QUESTIONS) {
            fragment = new QuestionFragment();
        } else if (name == FragmentName.GUIDE_RUNNING) {
            fragment = new GuideRunningFragment();
        } else if (name == FragmentName.GUIDE_STOPPED) {
            fragment = new GuideStoppedFragment();
        } else if (name == FragmentName.POI) {
            showPoi(activity, fragmentManager, poiId);
            return;
        } else if (name == FragmentName.ABOUT) {
            fragment = new AboutFragment();
        } else {
            Bundle args = new Bundle();
            args.putString("Menu", name.toString());
            fragment = new DetailFragment();
            fragment.setArguments(args);
        }
        FragmentTransaction tr = fragmentManager.beginTransaction().replace(R.id.content_frame, fragment);
        if (name.addToBackStack()) tr = tr.addToBackStack("fragment");
        tr.commit();
        activity.onFragmentChanged(name, null);
    }

    public static void showPoi(FragmentActivity activity, FragmentManager fragmentManager, int id) {
        PointOfInterestFragment fragment = new PointOfInterestFragment();
        Bundle args = new Bundle();
        args.putInt(PointOfInterestFragment.KEY_POI_ID, id);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        activity.onFragmentChanged(FragmentName.POI, id);
    }

    public interface FragmentActivity {
        public void onFragmentChanged(FragmentName newFragment, Integer poi);

    }

}
