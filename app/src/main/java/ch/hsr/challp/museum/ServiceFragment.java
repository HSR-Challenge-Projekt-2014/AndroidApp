package ch.hsr.challp.museum;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import ch.hsr.challp.museum.interfaces.BeaconScanClient;
import ch.hsr.challp.museum.service.BeaconScanService;

public abstract class ServiceFragment extends Fragment implements BeaconScanClient {

    private BeaconScanService beaconScanService = null;

    private ServiceConnection serviceConnectionConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BeaconScanService.LocalBinder binder = (BeaconScanService.LocalBinder) service;
            beaconScanService = binder.getService();
            beaconScanService.registerActivity(ServiceFragment.this);
            serviceConnected();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            beaconScanService = null;
        }
    };

    protected void serviceConnected() {
    }

    @Override
    public void onResume() {
        super.onResume();
        bindServiceNow();
        Log.d(getClass().getName(), "binding service for fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        unbindServiceNow();
        Log.d(getClass().getName(), "unbinding service for fragment");
    }

    protected void bindServiceNow() {
        Intent serviceIntent = new Intent(getActivity(), BeaconScanService.class);
        getActivity().getApplicationContext().bindService(serviceIntent, serviceConnectionConnection, Context.BIND_AUTO_CREATE);
    }

    protected void unbindServiceNow() {
        if (beaconScanService != null) {
            beaconScanService.unregisterActivity(ServiceFragment.this);
            getActivity().getApplicationContext().unbindService(serviceConnectionConnection);
            beaconScanService = null;
        }
    }

    @Override
    public void goToServiceStoppedActivity() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new GuideStoppedFragment()).commit();
    }

    protected BeaconScanService getBeaconScanService() {
        return beaconScanService;
    }
}
