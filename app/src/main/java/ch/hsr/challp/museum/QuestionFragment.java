package ch.hsr.challp.museum;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import ch.hsr.challp.museum.adapter.QuestionListAdapter;
import ch.hsr.challp.museum.model.Question;

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private Spinner spinnerTopic;
    private Spinner spinnerRoom;
    private Spinner spinnerFilter;
    private QuestionListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_questions, container, false);
        spinnerTopic = (Spinner) result.findViewById(R.id.spinner_topic);
        spinnerRoom = (Spinner) result.findViewById(R.id.spinner_room);
        spinnerFilter = (Spinner) result.findViewById(R.id.spinner_filter);

        ArrayAdapter<CharSequence> topicAdapter = ArrayAdapter.createFromResource(container.getContext(), R.array.topics_list, android.R.layout.simple_spinner_item);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTopic.setAdapter(topicAdapter);

        ArrayAdapter<CharSequence> roomAdapter = ArrayAdapter.createFromResource(container.getContext(), R.array.rooms_list, android.R.layout.simple_spinner_item);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(roomAdapter);

        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(container.getContext(), R.array.filters_list, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);

        //ViewGroup questionLayout = (ViewGroup) result.findViewById(R.id.question_layout);

        //String[] fromColumns = {"Title"};
        //int[] toViews = {R.id.question_row_title}; // The TextView in simple_list_item_1


        mAdapter = new QuestionListAdapter(container.getContext(), Question.getAll());
        ListView list = (ListView) result.findViewById(R.id.question_list_view);
        list.setAdapter(mAdapter);
        //getLoaderManager().initLoader(0, null, this);

//        for (Question question : Question.getAll()) {
//            View row = inflater.inflate(R.layout.question_row, null);
//            ((TextView)row.findViewById(R.id.question_row_title)).setText(question.getTitle());
//            ((ImageView)row.findViewById(R.id.question_row_image)).setImageResource(question.getImage());
//            //row.setOnClickListener(this);
//            questionLayout.addView(row);
//        }
//        for (int i = 0; i <= 15; i++) {
//            View question = inflater.inflate(R.layout.question_row, null);
//            question.setOnClickListener(this);
//            questionLayout.addView(question);
//        }

        list.setOnItemClickListener(this);

        return result;
    }

//    @Override
//    public void onClick(View view) {
//        Log.i(getClass().getName(), "question selected");
//        Intent intent = new Intent(view.getContext(), QuestionActivity.class);
//        startActivity(intent);
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(getClass().getName(), "question selected [i=" + i + ",l=" + l + "]");
        Intent intent = new Intent(view.getContext(), QuestionActivity.class);
        intent.putExtra(QuestionActivity.P_QUESTION_ID, i);
        startActivity(intent);
    }
}
