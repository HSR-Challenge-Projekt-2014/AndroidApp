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
import ch.hsr.challp.museum.model.Room;
import ch.hsr.challp.museum.model.Topic;

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private Spinner spinnerTopic;
    private Spinner spinnerRoom;
    private QuestionListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_questions, container, false);
        spinnerTopic = (Spinner) result.findViewById(R.id.spinner_topic);
        spinnerRoom = (Spinner) result.findViewById(R.id.spinner_room);

        ArrayAdapter<Topic> topicAdapter = new ArrayAdapter<>(container.getContext(), android.R.layout.simple_spinner_item, Topic.getAll());
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTopic.setAdapter(topicAdapter);
        spinnerTopic.setOnItemSelectedListener(new SpinnerListener());

        ArrayAdapter<Room> roomAdapter = new ArrayAdapter<>(container.getContext(), android.R.layout.simple_spinner_item, Room.getAll());
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(roomAdapter);
        spinnerRoom.setOnItemSelectedListener(new SpinnerListener());

        mAdapter = new QuestionListAdapter(container.getContext(), Question.getAll());
        ListView list = (ListView) result.findViewById(R.id.question_list_view);
        list.setAdapter(mAdapter);

        list.setOnItemClickListener(this);

        return result;
    }

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
        intent.putExtra(QuestionActivity.P_QUESTION_ID, mAdapter.getItem(i).getId());
        startActivity(intent);
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Topic topic = Topic.getAll().get(spinnerTopic.getSelectedItemPosition());
            Room room = Room.getAll().get(spinnerRoom.getSelectedItemPosition());
            Log.i(getClass().getName(), "Filter for topic " + topic + " and room " + room);
            mAdapter.filter(topic, room);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
