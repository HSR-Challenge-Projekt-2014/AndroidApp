package ch.hsr.challp.museum.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ch.hsr.challp.museum.service.BeaconScanService;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("KILL_SERVICE")) {
            Log.d("NotificationReciever", "Attemtping to stop service");
            context.stopService(new Intent(context, BeaconScanService.class));
        }
    }
}
