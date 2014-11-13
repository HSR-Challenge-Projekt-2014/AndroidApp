package ch.hsr.challp.museum.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.altbeacon.beacon.Beacon;

import ch.hsr.challp.museum.BeaconTest;
import ch.hsr.challp.museum.R;
import ch.hsr.challp.museum.broadcastreceiver.NotificationReceiver;

public class BeaconServiceNotificationProvider {

    private static final Integer NOTIFICATION_ID = 1337;
    private final static Integer VIBRATE_DURATION = 200; // miliseconds

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Context context;


    public BeaconServiceNotificationProvider(Service context) {
        this.context = context;
        this.builder = new NotificationCompat.Builder(context);
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        initNotification();
    }

    private void initNotification() {
        builder.setOngoing(true);
        builder.setPriority(Notification.PRIORITY_MAX);

        // go to activity when clicked
        Intent resultIntent = new Intent(context, BeaconTest.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(BeaconTest.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        // buttons
        Intent killServiceIntent = new Intent(context, NotificationReceiver.class);
        killServiceIntent.setAction("KILL_SERVICE");
        PendingIntent pendingIntentKill = PendingIntent.getBroadcast(context, 0, killServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Beenden", pendingIntentKill);
    }

    public void createServiceRunningNotification() {
        builder.setContentTitle("Museumsbegleiter aktiv");
        builder.setContentText("Zur App wechseln");
        builder.setSmallIcon(R.drawable.ic_guide_running);
        builder.setVibrate(null);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void createPoiNotification(Beacon beacon) {
        builder.setContentText("Neuer Inhalt verfügbar: " + beacon.getId3() + " " + beacon.getDistance() + "m");
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setVibrate(new long[]{0, VIBRATE_DURATION, VIBRATE_DURATION/2, VIBRATE_DURATION});
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void removePoiNotification() {
        createServiceRunningNotification();
    }

    public void removeNotification() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

}
