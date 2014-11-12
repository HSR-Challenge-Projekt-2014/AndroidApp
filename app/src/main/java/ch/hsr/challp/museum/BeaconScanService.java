package ch.hsr.challp.museum;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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


public class BeaconScanService extends Service implements BeaconConsumer {
    private final static String TAG = "BeaconScanService";

    private IBinder binder = new LocalBinder();
    private BeaconManager beaconManager;
    private List<BeaconScanClient> clients;


    public Beacon getCurrentBeacon() {
        return currentBeacon;
    }

    //private Region currentRegion;
    private Beacon currentBeacon;

    public void registerActivity(BeaconScanClient beaconTest) {
        if (clients == null) {
            clients = new LinkedList<BeaconScanClient>();
        }
        clients.add(beaconTest);
    }

    public void unregisterActivity(BeaconScanClient beaconTest) {
        if (clients == null) return;
        clients.remove(beaconTest);
    }


    public class LocalBinder extends Binder {
        BeaconScanService getService() {
            return BeaconScanService.this;
        }
    }

    @Override
    public void onCreate() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=aabb,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Bind Service");
        return binder;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Beacon closestBeacon = null;
                    for (Beacon beacon : beacons) {
                        if (closestBeacon == null) {
                            closestBeacon = beacon;
                        } else {
                            closestBeacon = closestBeacon.getDistance() > beacon.getDistance() ? beacon : closestBeacon;
                        }
                    }

                    if (!closestBeacon.equals(currentBeacon)) {
                        if (currentBeacon != null) {
                            removeNotification(currentBeacon);
                        }
                    }
                    updateClients();
                    showNotification(closestBeacon);
                    currentBeacon = closestBeacon;
                }
            }
        });

        try {
            //Region region1 = new Region("kontakt-beacon-01", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("10244"), Identifier.parse("54936"));
            //Region region2 = new Region("kontakt-beacon-02", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("31576"), Identifier.parse("38281"));
            //Region region3 = new Region("simulated-beacon-01", Identifier.parse("F7826DA6-4FA2-4E98-8024-BC5B71E0893E"), Identifier.parse("1"), Identifier.parse("2"));
            //beaconManager.startMonitoringBeaconsInRegion(region1);
            //beaconManager.startMonitoringBeaconsInRegion(region2);
            //beaconManager.startMonitoringBeaconsInRegion(region3);
            beaconManager.startRangingBeaconsInRegion(new Region("Museum", null, null, null));
        } catch (RemoteException e) {

        }
    }

    private void updateClients() {
        if (clients == null) return;
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

    private void showNotification(Beacon beacon) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("Neuer POI " + beacon.getId3() + " dist: " + beacon.getDistance() + "m");
        notificationBuilder.setContentText("Neuer Inhalt verfügbar: " + beacon.getId3());
        notificationBuilder.setSmallIcon(R.drawable.ic_stat_notification);
        Intent resultIntent = new Intent(this, BeaconTest.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(BeaconTest.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(beacon.getId3().toInt(), notificationBuilder.build());
    }

    private void removeNotification(Beacon beacon) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(beacon.getId3().toInt());
    }
}
