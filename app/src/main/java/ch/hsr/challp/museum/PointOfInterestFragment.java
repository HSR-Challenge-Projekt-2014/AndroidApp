package ch.hsr.challp.museum;

import org.altbeacon.beacon.Beacon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.challp.museum.adapter.ContentPreviewAdapter;
import ch.hsr.challp.museum.helper.FragmentHelper;
import ch.hsr.challp.museum.helper.FragmentName;
import ch.hsr.challp.museum.model.PointOfInterest;


public class PointOfInterestFragment extends ServiceFragment {

    public static final String KEY_POI_ID = "POI-ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        int poiId = getArguments().getInt(KEY_POI_ID);
        PointOfInterest pointOfInterest = PointOfInterest.findById(poiId);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_of_interest, container, false);

        ((TextView) view.findViewById(R.id.poiTitle)).setText(pointOfInterest.getTitle());
        ((ImageView) view.findViewById(R.id.poiHeader))
                .setImageResource(pointOfInterest.getHeaderResource());

        // fill content preview in gridView
        GridView contentView = (GridView) view.findViewById(R.id.POIContentPane);
        contentView.setAdapter(
                new ContentPreviewAdapter(getActivity(), pointOfInterest.getContents()));

        ((HomeActivity) getActivity()).setStopButtonVisible(true);

        return view;
    }

    @Override
    public void updateBeaconState(Beacon beacon) {
        if (beacon != null) {
            PointOfInterest pointOfInterest = PointOfInterest.findByBeacon(beacon);
            FragmentHelper.show(getFragmentChangeListener(), getFragmentManager(), FragmentName.POI,
                    pointOfInterest.getId());
        } else {
            FragmentHelper.show(getFragmentChangeListener(), getFragmentManager(),
                    FragmentName.GUIDE_RUNNING, null);
        }
    }
}
