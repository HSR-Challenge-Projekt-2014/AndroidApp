package ch.hsr.challp.museum.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.R;
import ch.hsr.challp.museum.model.Content;

public class ReadLaterListAdapter extends ArrayAdapter<Content> {

    private final Context context;
    private final List<Content> allItems;

    public ReadLaterListAdapter(Context context, List<Content> items) {
        super(context, R.layout.preview_row, items);
        this.context = context;
        this.allItems = new ArrayList<>(items);
    }

    @Override
    public Content getItem(int position) {
        return allItems.get(position);
    }

    @Override
    public int getPosition(Content item) {
        return allItems.indexOf(item);
    }

    @Override
    public int getCount() {
        return allItems.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Content content = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = mInflater.inflate(R.layout.preview_row, null);
        TextView title = (TextView) view.findViewById(R.id.preview_row_title);
        ImageView image = (ImageView) view.findViewById(R.id.preview_row_image);
        TextView metaLeft = (TextView) view.findViewById(R.id.preview_row_metainfo_left);
        TextView metaRight = (TextView) view.findViewById(R.id.preview_row_metainfo_right);

        title.setText(content.getTitle());
        image.setImageResource(content.getPreviewImageResource());
        metaLeft.setText(content.getTopic().getName());
        metaRight.setText(content.getRoom().getName());
        return view;
    }


}
