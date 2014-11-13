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
import ch.hsr.challp.museum.model.Question;
import ch.hsr.challp.museum.model.Room;
import ch.hsr.challp.museum.model.Topic;

public class QuestionListAdapter extends ArrayAdapter<Question> {

    private final Context context;
    private final List<Question> allItems;
    private List<Question> shownItems;

    public QuestionListAdapter(Context context, List<Question> items) {
        super(context, R.layout.question_row, items);
        this.context = context;
        this.allItems = new ArrayList<>(items);
        this.shownItems = new ArrayList<>(items);
    }

    @Override
    public Question getItem(int position) {
        return shownItems.get(position);
    }

    @Override
    public int getPosition(Question item) {
        return shownItems.indexOf(item);
    }

    @Override
    public int getCount() {
        return shownItems.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        Question question = getItem(position);

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

    public void filter(Topic topic, Room room) {
        List<Question> filterResult = new ArrayList<>(allItems);
        if (!(topic == null || Topic.ALL_ITEMS.equals(topic))) {
            for (Question q : new ArrayList<>(allItems)) {
                if (!q.getTopic().equals(topic))
                    filterResult.remove(q);
            }
        }
        if (!(room == null || Room.ALL_ROOMS.equals(room))) {
            for (Question q : new ArrayList<>(allItems)) {
                if (!q.getRoom().equals(room))
                    filterResult.remove(q);
            }
        }
        shownItems = filterResult;
        notifyDataSetChanged();
    }

}
