package ch.hsr.challp.museum;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.adapter.ContentPreviewAdapter;
import ch.hsr.challp.museum.model.Content;
import ch.hsr.challp.museum.model.PointOfInterest;


public class PointOfInterestFragment extends Fragment {

    private final PointOfInterest pointOfInterest;

    public PointOfInterestFragment() {
        /**
         * Mock Data
         */
        List<Content> contents = new ArrayList<Content>();
        contents.add(new Content("Abenteuerliches", R.drawable.content_abenteuer, R.drawable.content_image, "Foobar", "Lorem Ipsum blabla"));
        contents.add(new Content("Historisches", R.drawable.content_historisches, R.drawable.content_image, "Foobar", "Lorem Ipsum blabla"));
        contents.add(new Content("Spass und Spannung", R.drawable.content_spass, R.drawable.content_image, "Foobar", "Lorem Ipsum blabla"));
        contents.add(new Content("Liebesleben", R.drawable.content_liebensleben, R.drawable.content_image, "Foobar", "Lorem Ipsum blabla"));
        contents.add(new Content("Energie", R.drawable.content_energie, R.drawable.content_image, "Foobar", "Lorem Ipsum blabla"));
        this.pointOfInterest = new PointOfInterest("Der Bär in St.Ipsum", R.drawable.poi_header, contents);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
