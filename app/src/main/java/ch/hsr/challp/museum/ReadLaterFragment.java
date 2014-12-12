package ch.hsr.challp.museum;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
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
import android.widget.ListView;

import ch.hsr.challp.museum.adapter.ReadLaterListAdapter;
import ch.hsr.challp.museum.model.Content;

public class ReadLaterFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private ReadLaterListAdapter mAdapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_read_later, container, false);

        ListView list = (ListView) view.findViewById(R.id.read_later_list_view);
        Context context = container.getContext();

        mAdapter = new ReadLaterListAdapter(context, Content.getSavedContents());
        mAdapter.setNotifyOnChange(true);
        list.setAdapter(mAdapter);

        updateElementsVisibility();

        list.setOnItemClickListener(this);

        return view;
    }

    private void updateList() {
        mAdapter.clear();
        mAdapter.addAll(Content.getSavedContents());
        mAdapter.notifyDataSetChanged();
        updateElementsVisibility();
    }

    private void updateElementsVisibility() {
        if (!mAdapter.isEmpty()) {
            view.findViewById(R.id.no_read_later_items_text).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.no_read_later_items_text).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        updateList();
        super.onResume();
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
        Log.i(getClass().getName(), "content selected [i=" + i + ",l=" + l + "]");
        Intent intent = new Intent(view.getContext(), ContentActivity.class);
        intent.putExtra(ContentActivity.P_CONTENT, mAdapter.getItem(i));
        startActivity(intent);
    }

}
