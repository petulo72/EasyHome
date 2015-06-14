package pt.easyhome.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;

public class DeviceType {

    private int id;
    private String name;

    public DeviceType(int t, String n) {
        this.id = t;
        this.name = n;
    }

    public static DeviceType[] deviceType = {
            new DeviceType(1, "light"),
            new DeviceType(2, "dimmer"),
            new DeviceType(3, "RGBlight"),
            new DeviceType(4, "thermometer"),
            new DeviceType(5, "thermostat"),
            new DeviceType(6, "meteoStation"),
            new DeviceType(7, "jalousie"),
            new DeviceType(9, "gate"),
            new DeviceType(10, "ringer"),
            new DeviceType(11, "energyConsumption")
    };

    public static DeviceType getTypeByString(String type) {
        for (DeviceType t : deviceType) {
            if (t.name.equals(type))
                return t;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
