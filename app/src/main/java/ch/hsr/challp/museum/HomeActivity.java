package ch.hsr.challp.museum;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.challp.museum.adapter.NavDrawerListAdapter;
import ch.hsr.challp.museum.model.NavDrawerItem;
import ch.hsr.challp.museum.service.BeaconScanService;

public class HomeActivity extends Activity {
    public static final int SECTION_GUIDE = 0;
    public static final int SECTION_QUESTION = 1;
    public static final int SECTION_READ_LATER = 2;
    public static final int SECTION_INFORMATION = 3;
    public static final int SECTION_GUIDE_STOPPED = 4;
    public static final String NOTIFICATIONS = "NOTIFICATIONS";
    public static final String SETTINGS = "SETTINGS";
    public static final String SECTION = "SECTION";
    private String[] titles;
    private DrawerLayout dLayout;
    private ListView dList;
    private ListAdapter adapter;
    private ActionBarDrawerToggle dToggle;
    private MenuItem stopItem;
    private Menu menu;
    private Integer activeSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        titles = new String[]{
                getString(R.string.title_companion),
                getString(R.string.title_question),
                getString(R.string.title_read_later),
                getString(R.string.title_about),
                getString(R.string.title_stopped)
        };
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer_list);
        List<NavDrawerItem> items = new ArrayList<>();
        items.add(new NavDrawerItem(getString(R.string.title_companion), R.drawable.ic_guide));
        items.add(new NavDrawerItem(getString(R.string.title_question), R.drawable.ic_question));
        items.add(new NavDrawerItem(getString(R.string.title_read_later), R.drawable.ic_read_later));
        items.add(new NavDrawerItem(getString(R.string.title_about), R.drawable.ic_information));

        // notification switch
        Switch notificationSwitch = (Switch) dLayout.findViewById(R.id.switch_notification);
        final SharedPreferences prefs = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        notificationSwitch.setChecked(prefs.getBoolean(NOTIFICATIONS, true));
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (prefs.getBoolean(NOTIFICATIONS, true)) {
                    prefs.edit().putBoolean(NOTIFICATIONS, false).commit();
                } else {
                    prefs.edit().putBoolean(NOTIFICATIONS, true).commit();
                }
            }
        });

        adapter = new NavDrawerListAdapter(getApplicationContext(), items);
        dList.setAdapter(adapter);
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

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }

        showContent(getSectionIdFromExtras());
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
    protected void onRestoreInstanceState(Bundle in) {
        super.onRestoreInstanceState(in);
        activeSection = in.getInt(SECTION);
        showContent(activeSection);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        out.putInt(SECTION, activeSection);
        super.onSaveInstanceState(out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        stopItem = menu.findItem(R.id.stop_guide);
        if (isBeaconScanServiceActive()) {
            stopItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        if (item.getItemId() == R.id.stop_guide) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new GuideStoppedFragment()).commit();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showContent(int position) {
        activeSection = position;
        Fragment fragment;
        if (position == SECTION_GUIDE) {
            if (isBeaconScanServiceActive()) {
                fragment = new GuideRunningFragment();
            } else {
                fragment = new GuideFragment();
            }
        } else if (position == SECTION_QUESTION) {
            fragment = new QuestionFragment();
        } else if (position == SECTION_GUIDE_STOPPED) {
            fragment = new GuideStoppedFragment();
        } else {
            Bundle args = new Bundle();
            args.putString("Menu", titles[position]);
            fragment = new DetailFragment();
            fragment.setArguments(args);
        }
        setTitleByFragment(position);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        dList.setItemChecked(position, true);
        dList.setSelection(position);
    }

    private boolean isBeaconScanServiceActive() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (BeaconScanService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setTitleByFragment(int position) {
        String title = titles[position];
        if (getActionBar() != null) getActionBar().setTitle(title);
    }

    private int getSectionIdFromExtras() {
        Intent previousIntent = getIntent();
        if (previousIntent != null) {
            Bundle extras = previousIntent.getExtras();
            if (extras != null) {
                return extras.getInt(SECTION, 0);
            }
        }
        return 0;
    }

    public void setStopButtonVisible(boolean visible) {
        if (stopItem == null) {
            if (menu != null) {
                stopItem = menu.findItem(R.id.stop_guide);
                stopItem.setVisible(visible);
            }
        } else {
            stopItem.setVisible(visible);
        }
    }
}
