package ch.hsr.challp.museum;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.altbeacon.beacon.BeaconManager;

public class GuideFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button startButton = (Button) view.findViewById(R.id.guide_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyBluetooth();
            }
        });
        return view;
    }

    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(getActivity()).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Enable Bluetooth now?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (enableBluetooth()) {
                            Intent serviceIntent = new Intent(getActivity(), BeaconScanService.class);
                            getActivity().startService(serviceIntent);
                            Intent intent = new Intent(getActivity(), BeaconTest.class);
                            startActivity(intent);
                        } else {
                            goHome();
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goHome();
                    }
                });
                builder.show();
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    getActivity().finish();
                    System.exit(0);
                }

            });
            builder.show();
        }
    }

    private void goHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    private Boolean enableBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.enable();
    }
}