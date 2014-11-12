package ch.hsr.challp.museum;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class PointOfInterestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_of_interest, container, false);

        // fill content preview in gridView
        GridView contentView = (GridView) view.findViewById(R.id.POIContentPane);
        contentView.setAdapter(new ContentPreviewAdapter(getActivity()));

        return view;
    }

}
