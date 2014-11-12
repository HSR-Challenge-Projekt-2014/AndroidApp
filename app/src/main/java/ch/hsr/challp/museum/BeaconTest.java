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


public class BeaconTest extends Activity implements BeaconScanClient{
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


        Button bindButton = (Button) this.findViewById(R.id.bind_service);
        Button checkButton = (Button) this.findViewById(R.id.check_service);

        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindServiceNow();
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkService();
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
        EditText text = (EditText) this.findViewById(R.id.region_name);
        if (beaconScanService != null) {
            Beacon beacon = beaconScanService.getCurrentBeacon();
            text.setText(beacon.getId3() + "dist: " + beacon.getDistance() + "m");
        } else {
            text.setText("No Service/Beacon");
        }
    }

    @Override
    public void updateBeaconState(Beacon beacon) {
        EditText text = (EditText) this.findViewById(R.id.region_name);
        if (beacon != null) {
            text.setText(beacon.getId3() + "dist: " + beacon.getDistance() + "m");
        } else {
            text.setText("No Service/Beacon");
        }
    }
}
