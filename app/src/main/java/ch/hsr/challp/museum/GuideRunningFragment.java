package ch.hsr.challp.museum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.altbeacon.beacon.Beacon;

import ch.hsr.challp.museum.model.PointOfInterest;


public class GuideRunningFragment extends ServiceFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            PointOfInterestFragment fragment = new PointOfInterestFragment();
            Bundle args = new Bundle();
            args.putInt(PointOfInterestFragment.KEY_POI_ID, pointOfInterest.getId());
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }
}
