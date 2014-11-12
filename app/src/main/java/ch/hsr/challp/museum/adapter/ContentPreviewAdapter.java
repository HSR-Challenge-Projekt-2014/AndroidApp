package ch.hsr.challp.museum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.hsr.challp.museum.R;
import ch.hsr.challp.museum.model.Content;

public class ContentPreviewAdapter extends BaseAdapter {

    private Context context;
    private List<Content> contents;

    public ContentPreviewAdapter(Context context, List<Content> contents) {
        this.context = context;
        this.contents = contents;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View result = inflater.inflate(R.layout.content_preview, null);

        ((TextView) result.findViewById(R.id.contentPreviewDescription)).setText(contents.get(i).getPresentationTitle());
        ((ImageView) result.findViewById(R.id.contentPreviewImage)).setImageResource(contents.get(i).getPresentationImageResource());

        return result;
    }
}
