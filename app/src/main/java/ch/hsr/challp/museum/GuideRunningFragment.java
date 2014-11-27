package ch.hsr.challp.museum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.altbeacon.beacon.Beacon;

import ch.hsr.challp.museum.helper.FragmentHelper;
import ch.hsr.challp.museum.helper.FragmentName;
import ch.hsr.challp.museum.model.PointOfInterest;


public class GuideRunningFragment extends ServiceFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity)getActivity()).setStopButtonVisible(true);

        View fragment = inflater.inflate(R.layout.fragment_guide_running, container, false);
        fragment.findViewById(R.id.button_questions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentHelper.show(getFragmentChangeListener(), getFragmentManager(), FragmentName.QUESTIONS, null);
            }
        });
        return fragment;
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
            FragmentHelper.showPoi(getFragmentChangeListener(), getFragmentManager(), pointOfInterest.getId());
        }
    }
}
