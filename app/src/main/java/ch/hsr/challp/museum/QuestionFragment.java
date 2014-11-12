package ch.hsr.challp.museum;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private Spinner spinnerTopic;
    private Spinner spinnerRoom;
    private Spinner spinnerFilter;

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

        ViewGroup questionLayout = (ViewGroup) result.findViewById(R.id.question_layout);

        for (int i = 0; i <= 15; i++) {
            View question = inflater.inflate(R.layout.question_row, null);
            question.setOnClickListener(this);
            questionLayout.addView(question);
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        Log.i(getClass().getName(), "question selected");
        Intent intent = new Intent(view.getContext(), QuestionActivity.class);
        startActivity(intent);
    }
}
