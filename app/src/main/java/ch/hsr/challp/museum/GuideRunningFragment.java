package ch.hsr.challp.museum;

import org.altbeacon.beacon.Beacon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.challp.museum.helper.FragmentHelper;
import ch.hsr.challp.museum.model.PointOfInterest;


public class GuideRunningFragment extends ServiceFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setStopButtonVisible(true);

        return inflater.inflate(R.layout.fragment_guide_running, container, false);
    }

    @Override
    protected void serviceConnected() {
        super.serviceConnected();
        Beacon currentBeacon = getBeaconScanService().getCurrentBeacon();
        updateBeaconState(currentBeacon);
    }

    @Override
    public void updateBeaconState(Beacon beacon) {
        if (beacon != null) {
            PointOfInterest pointOfInterest = PointOfInterest.findByBeacon(beacon);
            FragmentHelper.showPoi(getFragmentChangeListener(), getFragmentManager(),
                    pointOfInterest.getId());
        }
    }
}
