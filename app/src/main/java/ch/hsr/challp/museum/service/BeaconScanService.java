package ch.hsr.challp.museum.service;

import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ch.hsr.challp.museum.HomeActivity;
import ch.hsr.challp.museum.R;
import ch.hsr.challp.museum.emulator.Emulator;
import ch.hsr.challp.museum.emulator.TimedBeaconSimulator;
import ch.hsr.challp.museum.helper.FragmentName;
import ch.hsr.challp.museum.interfaces.BeaconScanClient;
import ch.hsr.challp.museum.model.PointOfInterest;


public class BeaconScanService extends Service implements BeaconConsumer {
    // change current beacon after new beacon was measured x times
    private final static Integer BEACON_CHANGE_DELAY = 5;

    private IBinder binder = new LocalBinder();
    private BeaconManager beaconManager;

    private List<BeaconScanClient> clients;
    private final BroadcastReceiver bluetoothBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        if (clients.isEmpty()) {
                            killSelf();
                            Intent stopServiceIntent = new Intent(context, HomeActivity.class);
                            stopServiceIntent.putExtra(HomeActivity.SECTION, FragmentName.getId(FragmentName.GUIDE_STOPPED));
                            TaskStackBuilder stopStackBuilder = TaskStackBuilder.create(context);
                            stopStackBuilder.addParentStack(HomeActivity.class);
                            stopStackBuilder.addNextIntent(stopServiceIntent);
                            PendingIntent stopServicePendingIntent = stopStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
                            try {
                                stopServicePendingIntent.send(context, 0, intent);
                            } catch (PendingIntent.CanceledException e) {
                                Log.e(getClass().getName(), "Could not start Stopped Service Fragment.", e);
                            }
                        } else {
                            for (BeaconScanClient client : clients) {
                                client.goToServiceStoppedActivity();
                            }
                        }
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Toast.makeText(context, getString(R.string.bluetooth_off_stopping), Toast.LENGTH_SHORT).show();
                        break;
                    // no case for STATE_ON and STATE_TURNING_ON needed
                }
            }
        }
    };
    private Beacon currentBeacon;
    private Beacon nextBeacon;
    private Integer nextBeaconChangeDelay = 0;
    private Integer noBeaconChangeDelay = 0;
    private List<Region> regions;
    private BeaconServiceNotificationProvider notificationProvider;
    private Region wildcardRegion;

    public Beacon getCurrentBeacon() {
        return currentBeacon;
    }

    private void setCurrentBeacon(Collection<Beacon> beacons) {
        if (!beacons.isEmpty()) {
            noBeaconChangeDelay = 0;
            Beacon closestBeacon = getClosestBeacon(beacons);
            // closest beacon is not already current beacon
            Log.d(getClass().getName(), "Closest beacon is " + closestBeacon.getId3());
            if (!closestBeacon.equals(currentBeacon)) {
                if (!closestBeacon.equals(nextBeacon)) { // closest beacon is new candidate
                    nextBeaconChangeDelay = 1;
                    nextBeacon = closestBeacon;
                } else { // closest beacon is recurring
                    ++nextBeaconChangeDelay;
                    if (nextBeaconChangeDelay >= BEACON_CHANGE_DELAY) {
                        notificationProvider.createPoiNotification(closestBeacon);
                        currentBeacon = closestBeacon;
                        updateClients();
                    }
                }
            }
        } else {
            noBeaconChangeDelay++;
            if (noBeaconChangeDelay >= BEACON_CHANGE_DELAY) {
                noBeaconChangeDelay = 0;
                notificationProvider.removePoiNotification();
                currentBeacon = null;
                updateClients();
            }
            Log.d(getClass().getName(), "No beacons in range.");
        }
    }

    private Beacon getClosestBeacon(Collection<Beacon> beacons) {
        Beacon closestBeacon = null;
        for (Beacon beacon : beacons) {
            if (!isKnownBeacon(beacon)) continue;
            Log.d(getClass().getName(), "Beacon " + beacon.getId3() + " with distance " + beacon.getDistance());
            if (closestBeacon == null) {
                closestBeacon = beacon;
            } else {
                closestBeacon = closestBeacon.getDistance() > beacon.getDistance() ? beacon : closestBeacon;
            }
        }
        return closestBeacon;
    }

    private boolean isKnownBeacon(Beacon beacon) {
        for (Region region : regions) {
            if (beacon.getId3().equals(region.getId3())) {
                return true;
            }
        }
        return false;
    }

    public void registerActivity(BeaconScanClient beaconTest) {
        if (clients == null) {
            clients = new LinkedList<>();
        }
        clients.add(beaconTest);
        Log.d(getClass().getName(), "registered, new clients: " + clients.size());
    }

    public void unregisterActivity(BeaconScanClient beaconTest) {
        if (clients == null) return;
        clients.remove(beaconTest);
        Log.d(getClass().getName(), "unregistered, remaining clients: " + clients.size());
    }

    public void killSelf() {
        stopSelf();
    }

    @Override
    public void onCreate() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // altbeacon
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=aabb,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        // kontakt beacons
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        // magic?
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

        // Emulate beacon on Android device emulators
        if (Emulator.isEmulator()) {
            BeaconManager.setBeaconSimulator(new TimedBeaconSimulator());
        }

        regions = PointOfInterest.getAllRegions();
        wildcardRegion = new Region("Museum", null, null, null);

        notificationProvider = new BeaconServiceNotificationProvider(this);
        notificationProvider.createServiceRunningNotification();

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "BeaconScanService starting", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(getClass().getName(), "Bind Service");
        return binder;
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "BeaconScanService stopping", Toast.LENGTH_SHORT).show();
        notificationProvider.removeNotification();

        try {
            beaconManager.stopRangingBeaconsInRegion(wildcardRegion);
        } catch (RemoteException e) {
            Log.d(getClass().getName(), "Error while stopping beacon ranging, " + e.getMessage());
        }
        beaconManager.unbind(this);
        unregisterReceiver(bluetoothBroadcastReceiver);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                setCurrentBeacon(removeUnknownBeacons(beacons));
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(wildcardRegion);
        } catch (RemoteException e) {
            Log.d(getClass().getName(), "Error while starting beacon ranging, " + e.getMessage());
        }
    }

    private Collection<Beacon> removeUnknownBeacons(Collection<Beacon> beacons) {
        Collection<Beacon> result = new ArrayList<>();
        for (Beacon beacon : beacons) {
            if (isKnownBeacon(beacon)) {
                result.add(beacon);
            }
        }
        return result;
    }

    private void updateClients() {
        if (clients == null) return;

        // consider using other way to communicate with the UI
        try {
            Handler lo = new Handler(Looper.getMainLooper());
            lo.post(new Runnable() {
                public void run() {
                    for (BeaconScanClient client : clients) {
                        client.updateBeaconState(currentBeacon);
                    }
                }
            });
        } catch (Throwable t) {
            Log.e(getClass().getName(), t.getMessage(), t);
        }
    }

    public class LocalBinder extends Binder {
        public BeaconScanService getService() {
            return BeaconScanService.this;
        }
    }
}
