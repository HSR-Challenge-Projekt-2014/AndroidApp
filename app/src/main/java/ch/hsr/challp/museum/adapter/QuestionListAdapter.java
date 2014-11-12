package ch.hsr.challp.museum.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.hsr.challp.museum.R;
import ch.hsr.challp.museum.model.Question;

/**
 * Created by Michi on 12.11.2014.
 */
public class QuestionListAdapter extends ArrayAdapter {

    private final Context context;
    private List<Question> items;

    public QuestionListAdapter(Context context, List<Question> items) {
        super(context, R.layout.question_row, items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        Question question = (Question) getItem(position);

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = mInflater.inflate(R.layout.question_row, null);
        TextView title = (TextView) view.findViewById(R.id.question_row_title);
        ImageView image = (ImageView) view.findViewById(R.id.question_row_image);
        title.setText(question.getTitle());
        image.setImageResource(question.getImage());
        return view;

    }
}
