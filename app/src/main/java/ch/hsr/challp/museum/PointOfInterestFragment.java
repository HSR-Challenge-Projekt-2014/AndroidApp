package ch.hsr.challp.museum;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.challp.museum.adapter.ContentPreviewAdapter;
import ch.hsr.challp.museum.model.PointOfInterest;


public class PointOfInterestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PointOfInterest pointOfInterest = PointOfInterest.getAll().get(0);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_of_interest, container, false);

        ((TextView) view.findViewById(R.id.poiTitle)).setText(pointOfInterest.getTitle());
        ((ImageView) view.findViewById(R.id.poiHeader)).setImageResource(pointOfInterest.getHeaderResource());

        // fill content preview in gridView
        GridView contentView = (GridView) view.findViewById(R.id.POIContentPane);
        contentView.setAdapter(new ContentPreviewAdapter(getActivity(), pointOfInterest.getContents()));

        return view;
    }

}
