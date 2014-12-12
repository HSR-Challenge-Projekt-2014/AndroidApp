package ch.hsr.challp.museum.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.hsr.challp.museum.ContentActivity;
import ch.hsr.challp.museum.R;
import ch.hsr.challp.museum.model.Content;

public class ContentPreviewAdapter extends BaseAdapter {

    private final Context context;
    private final List<Content> contents;

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
        return contents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, final View convertView, final ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View result = convertView;
        if (result == null) {
            result = inflater.inflate(R.layout.content_preview, null);
        }

        result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(getClass().getName(), "content selected: " + contents.get(i));
                Intent intent = new Intent(viewGroup.getContext(), ContentActivity.class);
                intent.putExtra(ContentActivity.P_CONTENT, contents.get(i));
                v.getContext().startActivity(intent);
            }
        });

        ((TextView) result.findViewById(R.id.contentPreviewDescription))
                .setText(contents.get(i).getTopic().getName());
        ((ImageView) result.findViewById(R.id.contentPreviewImage))
                .setImageResource(contents.get(i).getPreviewImageResource());

        return result;
    }
}
