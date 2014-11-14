package ch.hsr.challp.museum.emulator;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.List;

public class TimedBeaconSimulator implements org.altbeacon.beacon.simulator.BeaconSimulator {
    private List<Beacon> beacons;

    public TimedBeaconSimulator() {
        beacons = new ArrayList<>();
        Beacon beacon = new AltBeacon.Builder().setId1("F7826DA6-4FA2-4E98-8024-BC5B71E0893E")
                .setId2("10244").setId3("54936").setRssi(-55).setTxPower(-55).build();
        beacons.add(beacon);
    }

    @Override
    public List<Beacon> getBeacons() {
        return beacons;
    }


}