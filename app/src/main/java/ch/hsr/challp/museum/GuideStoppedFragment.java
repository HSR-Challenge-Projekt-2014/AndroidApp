package ch.hsr.challp.museum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.altbeacon.beacon.Beacon;


public class GuideStoppedFragment extends ServiceFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide_stopped, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getClass().getName(), "OnResum Guide Stopped");
    }

    @Override
    protected void serviceConnected() {
        getBeaconScanService().killSelf();
        unbindServiceNow();
    }

    @Override
    public void updateBeaconState(Beacon beacon) {
        // should never be called: service is dead baby, service is dead.
    }
}
