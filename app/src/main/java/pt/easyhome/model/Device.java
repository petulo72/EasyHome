package pt.easyhome.model;

import pt.easyhome.miscellaneous.DeviceType;

public class Device {

    private int deviceId, use, roomID;
    private float value;
    private DeviceType deviceType;
    private String deviceName;
    private float[] values = new float[24];

    /**
     * device constructor
     *
     * @param deviceId
     * @param deviceName
     * @param type
     * @param roomID
     */
    public Device(int deviceId, String deviceName, DeviceType type, int roomID) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.deviceType = type;
        this.roomID = roomID;
        value = 0;
        use = 0;
    }

    public Device(int deviceId, String deviceName, DeviceType type, int roomID, float[] values) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.deviceType = type;
        this.roomID = roomID;
        this.values = values;
        use = 0;
    }

    /**
     * device constructor + value
     *
     * @param deviceId
     * @param deviceName
     * @param type
     * @param roomID
     * @param value
     */
    public Device(int deviceId, String deviceName, DeviceType type, int roomID, float value) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.deviceType = type;
        this.roomID = roomID;
        this.value = value;
    }

    @Override
    public String toString() {
        return deviceName;
    }

    public int getUse() {
        return use;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public float[] getValues() {
        return values;
    }

   public int getValuesCount(){
    return values.length;
   }

    public void setValues(float[] values) {
        this.values = values;
    }


}
