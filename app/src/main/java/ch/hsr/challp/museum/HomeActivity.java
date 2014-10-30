package ch.hsr.challp.museum;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends Activity {
    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapter;
    private ActionBarDrawerToggle dToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menu = new String[]{"Begleiter", "Fragen ans Museum", "Read at Home", "Fragen"};
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        dList.setAdapter(adapter);
        dList.setSelector(android.R.color.holo_blue_dark);
        dList.setOnItemClickListener(new OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                dLayout.closeDrawers();
                showContent(position);
            }

        });
        dToggle = new ActionBarDrawerToggle(this, dLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        dLayout.setDrawerListener(dToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        showContent(0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = dLayout.isDrawerOpen(dList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void showContent(int position) {
        Fragment fragment;
        String title = menu[position];
        if (position == 0) {
            fragment = new GuideFragment();
        } else if (position == 1) {
            fragment = new QuestionFragment();
        } else {
            Bundle args = new Bundle();
            args.putString("Menu", menu[position]);
            fragment = new DetailFragment();
            fragment.setArguments(args);
        }
        getActionBar().setTitle(title);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

}
