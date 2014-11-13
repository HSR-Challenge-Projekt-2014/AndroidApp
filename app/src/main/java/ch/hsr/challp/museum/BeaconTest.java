package ch.hsr.challp.museum;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.altbeacon.beacon.Beacon;

import ch.hsr.challp.museum.interfaces.BeaconScanClient;
import ch.hsr.challp.museum.service.BeaconScanService;


public class BeaconTest extends Activity implements BeaconScanClient {
    private BeaconScanService beaconScanService = null;

    private ServiceConnection serviceConnectionConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BeaconScanService.LocalBinder binder = (BeaconScanService.LocalBinder) service;
            beaconScanService = binder.getService();
            beaconScanService.registerActivity(BeaconTest.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            beaconScanService.unregisterActivity(BeaconTest.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_test);
        Button killServiceButton = (Button) this.findViewById(R.id.kill_service);
        killServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                killService();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindServiceNow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindServiceNow();
    }

    private void bindServiceNow() {
        Intent serviceIntent = new Intent(this, BeaconScanService.class);
        getApplicationContext().bindService(serviceIntent, serviceConnectionConnection, BIND_AUTO_CREATE);
        if (beaconScanService != null) {
            checkService();
        }
    }

    private void unbindServiceNow() {
        if (beaconScanService != null) {
            getApplicationContext().unbindService(serviceConnectionConnection);
        }
    }

    private void checkService() {
        if (beaconScanService != null) {
            setStatusText(beaconScanService.getCurrentBeacon());
        }
    }

    private void killService() {
        if (beaconScanService != null) {
            unbindServiceNow();
            beaconScanService.killSelf();
            beaconScanService = null;
        }
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateBeaconState(Beacon beacon) {
        setStatusText(beacon);
    }

    private void setStatusText(Beacon beacon) {
        EditText text = (EditText) this.findViewById(R.id.beacon_info);
        if (beacon != null) {
            text.setText(beacon.getId3() + "dist: " + beacon.getDistance() + "m");
        } else {
            text.setText("No Service/Beacon");
        }
    }
}
