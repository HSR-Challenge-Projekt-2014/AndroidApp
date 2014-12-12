package ch.hsr.challp.museum;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
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
import ch.hsr.challp.museum.helper.FragmentHelper;
import ch.hsr.challp.museum.helper.FragmentName;
import ch.hsr.challp.museum.model.NavDrawerItem;
import ch.hsr.challp.museum.service.BeaconScanService;

public class HomeActivity extends Activity implements FragmentHelper.FragmentActivity {

    public static final String NOTIFICATIONS = "NOTIFICATIONS";
    public static final String SETTINGS = "SETTINGS";
    public static final String SECTION = "SECTION";
    public static final String POI = "POI";
    private DrawerLayout dLayout;
    private ListView dList;
    private ListAdapter adapter;
    private ActionBarDrawerToggle dToggle;
    private MenuItem stopItem;
    private Menu menu;
    private FragmentName activeFragment;
    private Integer activePOI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer_list);
        List<NavDrawerItem> items = new ArrayList<>();
        for (FragmentName fragmentName : FragmentName.DRAWER_FRAGMENTS) {
            items.add(
                    new NavDrawerItem(getString(fragmentName.getTitle()), fragmentName.getIcon()));
        }

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
                showContent(FragmentName.DRAWER_FRAGMENTS[position]);
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
        activeFragment = FragmentName.getFragmentName(in.getInt(SECTION));
        activePOI = in.getInt(POI);
        showContent(activeFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        out.putInt(SECTION, FragmentName.getId(activeFragment));
        if (activePOI != null) {
            out.putInt(POI, activePOI);
        }
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
        if (FragmentName.QUESTIONS == activeFragment || FragmentName.POI == activeFragment) {
            menu.findItem(R.id.action_ask_question).setVisible(true);
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
            FragmentHelper.show(this, getFragmentManager(), FragmentName.GUIDE_STOPPED, null);
        } else if (item.getItemId() == R.id.action_ask_question) {
            startActivity(new Intent(getApplicationContext(), QuestionFormActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentChanged(FragmentName newFragment, Integer poiID) {
        activeFragment = newFragment;
        activePOI = poiID;
        int position = newFragment.getDrawerPosition();
        dList.setItemChecked(position, true);
        dList.setSelection(position);
        setTitle(getString(newFragment.getTitle()));
    }

    @Override
    public FragmentName getActiveFragment() {
        return activeFragment;
    }

    private void showContent(FragmentName name) {
        if (name == FragmentName.GUIDE && isBeaconScanServiceActive()) {
            // TODO show POI, if available
            name = FragmentName.GUIDE_RUNNING;
        }
        FragmentHelper.show(this, getFragmentManager(), name, activePOI);
    }

    private boolean isBeaconScanServiceActive() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (BeaconScanService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setTitle(String title) {
        if (getActionBar() != null) {
            getActionBar().setTitle(title);
        }
    }

    private FragmentName getSectionIdFromExtras() {
        Intent previousIntent = getIntent();
        if (previousIntent != null) {
            Bundle extras = previousIntent.getExtras();
            if (extras != null) {
                return FragmentName.getFragmentName(extras.getInt(SECTION, 0));
            }
        }
        return FragmentName.GUIDE;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                this.finish();
                return false;
            } else {
                if (isBeaconScanServiceActive()
                        && getFragmentManager().getBackStackEntryCount() == 1) {
                    this.finish();
                    return false;
                }
                getFragmentManager().popBackStack();
                int index = getFragmentManager().getBackStackEntryCount() - 1;
                FragmentManager.BackStackEntry backStackEntryAt = getFragmentManager()
                        .getBackStackEntryAt(index);
                Integer fragmentId = Integer.parseInt(backStackEntryAt.getName());
                onFragmentChanged(FragmentName.getFragmentName(fragmentId), activePOI);
                removeCurrentFragment();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void removeCurrentFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment currentFrag = getFragmentManager().findFragmentById(R.id.content_frame);
        if (currentFrag != null) {
            transaction.remove(currentFrag);
        }
        transaction.commit();
    }

}
