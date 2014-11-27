package ch.hsr.challp.museum;

import android.app.Activity;
import android.app.Fragment;

import ch.hsr.challp.museum.helper.FragmentHelper;

public class DrawerFragment extends Fragment {

    private FragmentHelper.FragmentActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentHelper.FragmentActivity) activity;
    }

    protected FragmentHelper.FragmentActivity getFragmentChangeListener() {
        return activity;
    }
}
