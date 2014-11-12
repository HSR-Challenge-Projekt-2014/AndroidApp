package ch.hsr.challp.museum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import ch.hsr.challp.museum.R;

public class ContentPreviewAdapter extends BaseAdapter {

    public static final int COUNT = 5;
    private Context context;

    public ContentPreviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return COUNT;
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
        return inflater.inflate(R.layout.content_preview, viewGroup);
    }
}
