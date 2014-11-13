package ch.hsr.challp.museum.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ch.hsr.challp.museum.interfaces.BeaconScanClient;


public class BeaconScanService extends Service implements BeaconConsumer {
    private final static String TAG = "BeaconScanService";

    // change current beacon after new beacon was measured x times
    private final static Integer BEACON_CHANGE_DELAY = 5;

    private IBinder binder = new LocalBinder();
    private BeaconManager beaconManager;

    private List<BeaconScanClient> clients;

    private Beacon currentBeacon;
    private Beacon nextBeacon;
    private Integer nextBeaconChangeDelay = 0;
    private Region region;
    private BeaconServiceNotificationProvider notificationProvider;

    public Beacon getCurrentBeacon() {
        return currentBeacon;
    }

    private void setCurrentBeacon(Collection<Beacon> beacons) {
        if (!beacons.isEmpty()) {
            Beacon closestBeacon = getClosestBeacon(beacons);
            // closest beacon is not already current beacon
            if (!closestBeacon.equals(currentBeacon)) {
                if (!closestBeacon.equals(nextBeacon)) { // closest beacon is new candidate
                    nextBeaconChangeDelay = 1;
                    nextBeacon = closestBeacon;
                } else { // closest beacon is recurring
                    ++nextBeaconChangeDelay;
                    if (nextBeaconChangeDelay >= BEACON_CHANGE_DELAY) {
                        notificationProvider.createPoiNotification(closestBeacon);
                        currentBeacon = closestBeacon;
                    }
                }
            }
            updateClients();
        } else {
            notificationProvider.removePoiNotification();
        }
    }

    private Beacon getClosestBeacon(Collection<Beacon> beacons) {
        Beacon closestBeacon = null;
        for (Beacon beacon : beacons) {
            if (closestBeacon == null) {
                closestBeacon = beacon;
            } else {
                closestBeacon = closestBeacon.getDistance() > beacon.getDistance() ? beacon : closestBeacon;
            }
        }
        return closestBeacon;
    }

    public void registerActivity(BeaconScanClient beaconTest) {
        if (clients == null) {
            clients = new LinkedList<>();
        }
        clients.add(beaconTest);
    }

    public void unregisterActivity(BeaconScanClient beaconTest) {
        if (clients == null) return;
        clients.remove(beaconTest);
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
        region = new Region("Museum", null, null, null);
        notificationProvider = new BeaconServiceNotificationProvider(this);
        notificationProvider.createServiceRunningNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "BeaconScanService starting", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Bind Service");
        return binder;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "BeaconScanService stopping", Toast.LENGTH_SHORT).show();
        notificationProvider.removeNotification();

        try {
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            Log.d(TAG, "Error while stopping beacon ranging, " + e.getMessage());
        }
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                setCurrentBeacon(beacons);
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            Log.d(TAG, "Error while starting beacon ranging, " + e.getMessage());
        }
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
            t.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {
        public BeaconScanService getService() {
            return BeaconScanService.this;
        }
    }
}
