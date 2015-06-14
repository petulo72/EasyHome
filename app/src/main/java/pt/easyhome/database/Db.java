package pt.easyhome.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import java.util.ArrayList;

import pt.easyhome.miscellaneous.DeviceType;
import pt.easyhome.model.Device;
import pt.easyhome.model.House;
import pt.easyhome.model.Room;


public class Db {

    private SQLHelper sqlHelper;
    private SQLiteDatabase sqLiteDatabase;

    public Db(Context context) {
        sqlHelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }

    public void close() {
        try {
            sqlHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*------------------------------ INSERT ------------------------------*/

    /**
     * hosue inserts
     *
     * @param house
     */
    public void insertHouse(House house) {
        open();
        ContentValues houses = new ContentValues();
        houses.put(SQLHelper.COLUMN_NAME, house.getHouseName());
        houses.put(SQLHelper.COLUMN_HOUSE_IP_ADDRESS, house.getHouseIpAddress());
        houses.put(SQLHelper.COLUMN_HOUSE_PORT, house.getHousePort());
        houses.put(SQLHelper.COLUMN_HOUSE_MAC_ADDRESS, house.getHouseMacAdress());
        houses.put(SQLHelper.COLUMN_SERVER_IP_ADDRESS, house.getServerIpAdress());
        houses.put(SQLHelper.COLUMN_SERVER_PORT, house.getServerPort());
        houses.put(SQLHelper.COLUMN_PASSWORD, house.getPassword());
        sqLiteDatabase.insert(SQLHelper.TABLE_HOUSE, null, houses);
        close();
    }

    /**
     * room insert
     *
     * @param room
     */
    public void insertRoom(Room room) {
        open();
        ContentValues rooms = new ContentValues();
        rooms.put(SQLHelper.COLUMN_NAME, room.getRoomName());
        rooms.put(SQLHelper.COLUMN_HOUSE_ID, room.getHouseId());
        sqLiteDatabase.insert(SQLHelper.TABLE_ROOM, null, rooms);
        close();
    }

    /**
     * insert spotrebicov do databazy
     *
     * @param device
     */
    public void insertDevice(Device device) {
        open();

        ContentValues devices = new ContentValues();
        devices.put(SQLHelper.COLUMN_ID, device.getDeviceId());
        devices.put(SQLHelper.COLUMN_NAME, device.getDeviceName());
        devices.put(SQLHelper.COLUMN_TYPE, device.getDeviceType().toString());
        devices.put(SQLHelper.COLUMN_VALUE, device.getValue());
        devices.put(SQLHelper.COLUMN_ROOM_ID, device.getRoomID());
        sqLiteDatabase.insert(SQLHelper.TABLE_DEVICE, null, devices);
        close();
    }

    public void insertProfile(Device device) {
        open();

        ContentValues profiles = new ContentValues();
        profiles.put(SQLHelper.COLUMN_ID, device.getDeviceId());
        profiles.put(SQLHelper.COLUMN_NAME, device.getDeviceName());
        profiles.put(SQLHelper.COLUMN_TYPE, device.getDeviceType().toString());
        profiles.put(SQLHelper.COLUMN_USE, device.getUse());
        profiles.put(SQLHelper.COLUMN_ROOM_ID, device.getRoomID());
        if (device.getDeviceType().getName() == "thermostat") {
            profiles.put("value0", device.getValues()[0]);
            profiles.put("value1", device.getValues()[1]);
            profiles.put("value2", device.getValues()[2]);
            profiles.put("value3", device.getValues()[3]);
            profiles.put("value4", device.getValues()[4]);
            profiles.put("value5", device.getValues()[5]);
            profiles.put("value6", device.getValues()[6]);
            profiles.put("value7", device.getValues()[7]);
            profiles.put("value8", device.getValues()[8]);
            profiles.put("value9", device.getValues()[9]);
            profiles.put("value10", device.getValues()[10]);
            profiles.put("value11", device.getValues()[11]);
            profiles.put("value12", device.getValues()[12]);
            profiles.put("value13", device.getValues()[13]);
            profiles.put("value14", device.getValues()[14]);
            profiles.put("value15", device.getValues()[15]);
            profiles.put("value16", device.getValues()[16]);
            profiles.put("value17", device.getValues()[17]);
            profiles.put("value18", device.getValues()[18]);
            profiles.put("value19", device.getValues()[19]);
            profiles.put("value20", device.getValues()[20]);
            profiles.put("value21", device.getValues()[21]);
            profiles.put("value22", device.getValues()[22]);
            profiles.put("value23", device.getValues()[23]);
        } else if (device.getDeviceType().getName() == "RGBlight") {
            profiles.put("value0", device.getValues()[0]);
            profiles.put("value1", device.getValues()[1]);
            profiles.put("value2", device.getValues()[2]);
            profiles.put("value3", device.getValues()[3]);
        } else if (device.getDeviceType().getName() == "meteoStation") {
            profiles.put("value0", device.getValues()[0]);
            profiles.put("value1", device.getValues()[1]);
            profiles.put("value2", device.getValues()[2]);
            profiles.put("value3", device.getValues()[3]);
            profiles.put("value4", device.getValues()[4]);
            profiles.put("value5", device.getValues()[5]);
        }
        sqLiteDatabase.insert(SQLHelper.TABLE_PROFILE, null, profiles);
        close();
    }

 /*------------------------------ UPDATE ------------------------------*/

//	public void updateDeviceValue(Device device) {
//		open();
//
//		ContentValues devices = new ContentValues();
//        if(device.getDeviceType().getDeviceId() == 5){
//            devices.put(SQLHelper.COLUMN_VALUE, device.getValue()*100);
//        }else {
//            devices.put(SQLHelper.COLUMN_VALUE, device.getValue());
//        }
//
//        sqLiteDatabase.update(
//                SQLHelper.TABLE_DEVICE,
//                devices,
//                SQLHelper.COLUMN_ID + " = ?",
//                new String[]{"" + device.getDeviceId()});
//        Log.w("device value: ", String.valueOf(device.getValue()));
//		close();
//	}

//	public void selectDevice(int house, int room, int type, int device, int value) {
//		String inner =
//				"SELECT " + SQLHelper.COLUMN_ID + 
//				" FROM " + SQLHelper.TABLE_ROOM +
//				" WHERE " + SQLHelper.COLUMN_HOUSE_ID + " = " + house + 
//				" AND " + SQLHelper.COLUMN_ROOM_NUMBER + " = " + room;
//
//		String update = 
//				"UPDATE" + SQLHelper.TABLE_DEVICE +
//				" SET " + SQLHelper.COLUMN_VALUE + " = " + value +
//				" WHERE " + SQLHelper.COLUMN_DEVICE_NUMBER + " = " + device +
//				" AND " + SQLHelper.COLUMN_TYPE + " = " + type +
//				" AND " + SQLHelper.COLUMN_ROOM_ID + " IN (" + inner + ");";
//		
//		sqLiteDatabase.execSQL(update);
//	}

    public void updateDeviceValue(int deviceId, float value) {
        open();

        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_VALUE, value);
        sqLiteDatabase.update(
                SQLHelper.TABLE_DEVICE,
                values,
                SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + deviceId});
        close();
    }

//    public void updateProfileValues(int deviceId, float[] values) {
//        open();
//        this.get
//        ContentValues profiles = new ContentValues();
//        if (device.getDeviceType().getName() == "thermostat") {
//            profiles.put("value0", values[0]);
//            profiles.put("value1", values[1]);
//            profiles.put("value2", values[2]);
//            profiles.put("value3", values[3]);
//            profiles.put("value4", values[4]);
//            profiles.put("value5", values[5]);
//            profiles.put("value6", values[6]);
//            profiles.put("value7", values[7]);
//            profiles.put("value8", values[8]);
//            profiles.put("value9", values[9]);
//            profiles.put("value10", values[10]);
//            profiles.put("value11", values[11]);
//            profiles.put("value12", values[12]);
//            profiles.put("value13", values[13]);
//            profiles.put("value14", values[14]);
//            profiles.put("value15", values[15]);
//            profiles.put("value16", values[16]);
//            profiles.put("value17", values[17]);
//            profiles.put("value18", values[18]);
//            profiles.put("value19", values[19]);
//            profiles.put("value20", values[20]);
//            profiles.put("value21", values[21]);
//            profiles.put("value22", values[22]);
//            profiles.put("value23", values[23]);
//        } else if (device.getDeviceType().getName() == "RGBlight") {
//            profiles.put("value0", values[0]);
//            profiles.put("value1", values[1]);
//            profiles.put("value2", values[2]);
//            profiles.put("value3", values[3]);
//        } else if (device.getDeviceType().getName() == "meteoStation") {
//            profiles.put("value0", values[0]);
//            profiles.put("value1", values[1]);
//            profiles.put("value2", values[2]);
//            profiles.put("value3", values[3]);
//            profiles.put("value4", values[4]);
//            profiles.put("value5", values[5]);
//        }
//
//        close();
//    }

    public void updateProfileValues(Device device) {
        open();

        ContentValues profiles = new ContentValues();
        if (device.getDeviceType().getName() == "thermostat") {
            profiles.put("value0", device.getValues()[0]);
            profiles.put("value1", device.getValues()[1]);
            profiles.put("value2", device.getValues()[2]);
            profiles.put("value3", device.getValues()[3]);
            profiles.put("value4", device.getValues()[4]);
            profiles.put("value5", device.getValues()[5]);
            profiles.put("value6", device.getValues()[6]);
            profiles.put("value7", device.getValues()[7]);
            profiles.put("value8", device.getValues()[8]);
            profiles.put("value9", device.getValues()[9]);
            profiles.put("value10", device.getValues()[10]);
            profiles.put("value11", device.getValues()[11]);
            profiles.put("value12", device.getValues()[12]);
            profiles.put("value13", device.getValues()[13]);
            profiles.put("value14", device.getValues()[14]);
            profiles.put("value15", device.getValues()[15]);
            profiles.put("value16", device.getValues()[16]);
            profiles.put("value17", device.getValues()[17]);
            profiles.put("value18", device.getValues()[18]);
            profiles.put("value19", device.getValues()[19]);
            profiles.put("value20", device.getValues()[20]);
            profiles.put("value21", device.getValues()[21]);
            profiles.put("value22", device.getValues()[22]);
            profiles.put("value23", device.getValues()[23]);
        } else if (device.getDeviceType().getName() == "RGBlight") {
            profiles.put("value0", device.getValues()[0]);
            profiles.put("value1", device.getValues()[1]);
            profiles.put("value2", device.getValues()[2]);
            profiles.put("value3", device.getValues()[3]);
        } else if (device.getDeviceType().getName() == "meteoStation") {
            profiles.put("value0", device.getValues()[0]);
            profiles.put("value1", device.getValues()[1]);
            profiles.put("value2", device.getValues()[2]);
            profiles.put("value3", device.getValues()[3]);
            profiles.put("value4", device.getValues()[4]);
            profiles.put("value5", device.getValues()[5]);
        }

        sqLiteDatabase.update(SQLHelper.TABLE_PROFILE, profiles, SQLHelper.COLUMN_ID + " = ?", new String[]{"" + device.getDeviceId()});

        close();
    }

    /**
     * ziska zoznam domov z databazy
     *
     * @return zoznam domov
     */
    public ArrayList<House> getHouses() {
        open();
        // 1.tabulka,2. stlpce,3. podmienka, 4.argumenty(namiesto otaznika hodi
        // 1 z dalsieho parametra, group by, having
        Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_HOUSE, null, null, null, null, null, null);
        ArrayList<House> houses = new ArrayList<House>();
        // ak cursor neni prazdny a zaroven sa treba spytat mateja na to co
        // vracia a kedy vracia moveToFirst tak sa vykonava do while
        if (c != null && c.moveToFirst()) {
            do {
                House house = new House(
                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_HOUSE_IP_ADDRESS)),
                        c.getInt(c.getColumnIndex(SQLHelper.COLUMN_HOUSE_PORT)),
                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_HOUSE_MAC_ADDRESS)),
                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_SERVER_IP_ADDRESS)),
                        c.getInt(c.getColumnIndex(SQLHelper.COLUMN_SERVER_PORT)),
                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_PASSWORD)));
                house.setHouseId(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)));
                houses.add(house);
            } while (c.moveToNext());
        }
        close();
        return houses;
    }

    /**
     * ziska izby z databazy, vlozi do zoznamu
     *
     * @param houseID
     * @return
     */
    public ArrayList<Room> getRoomsByHouseID(int houseID) {
        open();
        Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_ROOM, null, SQLHelper.COLUMN_HOUSE_ID + " = ?",
                new String[]{houseID + ""}, null, null, null);
        ArrayList<Room> rooms = new ArrayList<Room>();
        if (c != null && c.moveToFirst()) {
            do {
                Room room = new Room(c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                        houseID);
                room.setRoomId(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)));
                rooms.add(room);
            } while (c.moveToNext());
        }
        close();
        return rooms;
    }

//	public ArrayList<Device> getDevicesByDeviceType(DeviceType type){
//		open();
//		
//		Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_DEVICE, null, SQLHelper.COLUMN_TYPE + " = ?",
//				new String[] {  + "" }, null, null, null);
//		
//	}

    public ArrayList<Device> getDevicesByRoomID(int roomID) {
        open();
        Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_DEVICE, null, SQLHelper.COLUMN_ROOM_ID + " = ?",
                new String[]{roomID + ""}, null, null, null);
        ArrayList<Device> devices = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                Device dev = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                        DeviceType.getTypeByString(c.getString(c.getColumnIndex(SQLHelper.COLUMN_TYPE))),
                        c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                        c.getFloat(c.getColumnIndex(SQLHelper.COLUMN_VALUE)));
//                dev.setDeviceId(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)));
                devices.add(dev);
            } while (c.moveToNext());
        }
        close();
        return devices;
    }

    public ArrayList<Device> getProfilesByRoomID(int roomID) {
        open();
        Cursor c = sqLiteDatabase.rawQuery("select * from profile", null);

//        Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_PROFILE, null, SQLHelper.COLUMN_ROOM_ID + " = ?",
//                new String[]{roomID + ""}, null, null, null);
        ArrayList<Device> profiles = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                DeviceType type = DeviceType.getTypeByString((c.getString(c.getColumnIndex(SQLHelper.COLUMN_TYPE))));
                String devName = c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME));
                if (type.getName() == "thermostat") {
                    Device dev = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                            c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                            type, c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                            new float[]{c.getFloat(c.getColumnIndex("value0")),
                                    c.getFloat(c.getColumnIndex("value1")),
                                    c.getFloat(c.getColumnIndex("value2")),
                                    c.getFloat(c.getColumnIndex("value3")),
                                    c.getFloat(c.getColumnIndex("value4")),
                                    c.getFloat(c.getColumnIndex("value5")),
                                    c.getFloat(c.getColumnIndex("value6")),
                                    c.getFloat(c.getColumnIndex("value7")),
                                    c.getFloat(c.getColumnIndex("value8")),
                                    c.getFloat(c.getColumnIndex("value9")),
                                    c.getFloat(c.getColumnIndex("value10")),
                                    c.getFloat(c.getColumnIndex("value11")),
                                    c.getFloat(c.getColumnIndex("value12")),
                                    c.getFloat(c.getColumnIndex("value13")),
                                    c.getFloat(c.getColumnIndex("value14")),
                                    c.getFloat(c.getColumnIndex("value15")),
                                    c.getFloat(c.getColumnIndex("value16")),
                                    c.getFloat(c.getColumnIndex("value17")),
                                    c.getFloat(c.getColumnIndex("value18")),
                                    c.getFloat(c.getColumnIndex("value19")),
                                    c.getFloat(c.getColumnIndex("value20")),
                                    c.getFloat(c.getColumnIndex("value21")),
                                    c.getFloat(c.getColumnIndex("value22")),
                                    c.getFloat(c.getColumnIndex("value23")),
                            });
                    dev.setUse(0);
                    profiles.add(dev);
                } else if (type.getName() == "meteoStation") {
                    Device dev = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                            c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                            type, c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                            new float[]{c.getFloat(c.getColumnIndex("value0")),
                                    c.getFloat(c.getColumnIndex("value1")),
                                    c.getFloat(c.getColumnIndex("value2")),
                                    c.getFloat(c.getColumnIndex("value3")),
                                    c.getFloat(c.getColumnIndex("value4")),
                                    c.getFloat(c.getColumnIndex("value5")),
                            });
                    dev.setUse(0);
                    profiles.add(dev);
                } else if (type.getName() == "RGBlight") {
                    Device dev = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                            c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                            type, c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                            new float[]{c.getFloat(c.getColumnIndex("value0")),
                                    c.getFloat(c.getColumnIndex("value1")),
                                    c.getFloat(c.getColumnIndex("value2")),
                                    c.getFloat(c.getColumnIndex("value3")),
                            });
                    dev.setUse(0);
                    profiles.add(dev);
                }
            } while ((c.moveToNext()));
        }
        close();
        return profiles;

    }

//	public ArrayList<DeviceType> getDeviceTypesByRoomID(int roomID){
//		open();
//		
//		Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_DEVICE, SQLHelper.COLUMN_TYPE, SQLHelper.COLUMN_ROOM_ID + " = ?",
//				new String[] { roomID + "" }, null, null, null);
//		
//		close();
//	}

    /**
     * vymazanie domov z databazy
     *
     * @param h
     */
    public void removeHouse(House h) {
        open();
        sqLiteDatabase.delete(
                SQLHelper.TABLE_HOUSE,
                SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + h.getHouseId()});
        close();
    }

    public void removeHouse(int houseID) {
        open();
        sqLiteDatabase.delete(
                SQLHelper.TABLE_HOUSE,
                SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + houseID});
        close();
    }

    public void removeDevice(Device d) {
        open();
        sqLiteDatabase.delete(
                SQLHelper.TABLE_DEVICE,
                SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + d.getDeviceId()});
        close();
    }

    public void removeDevice(int deviceId) {
        open();
        sqLiteDatabase.delete(
                SQLHelper.TABLE_DEVICE,
                SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + deviceId});
        close();
    }

    public void removeRoom(int roomId) {
        open();
        sqLiteDatabase.delete(
                SQLHelper.TABLE_ROOM,
                SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + roomId});
        close();
    }

    public void removeRoom(Room r) {
        open();
        sqLiteDatabase.delete(
                SQLHelper.TABLE_ROOM,
                SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + r.getRoomId()});
        close();
    }

    public void removeProfile(int deviceId) {
        open();
        sqLiteDatabase.delete(SQLHelper.TABLE_PROFILE, SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + deviceId
                });
        close();
    }

    public void removeProfile(Device device) {
        open();
        sqLiteDatabase.delete(SQLHelper.TABLE_PROFILE, SQLHelper.COLUMN_ID + " = ?",
                new String[]{"" + device.getDeviceId()
                });
        close();
    }

    public ArrayList<Device> getDevices() {
        open();
        Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_DEVICE, null, null, null, null, null, null);
        ArrayList<Device> devices = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                Device device = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                        DeviceType.getTypeByString(c.getString(c.getColumnIndex(SQLHelper.COLUMN_TYPE))),
                        c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                        c.getFloat(c.getColumnIndex(SQLHelper.COLUMN_VALUE)));
                devices.add(device);
            } while (c.moveToNext());
        }
        close();
        return devices;
    }

    public ArrayList<Device> getProfiles() {
        open();
        Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_PROFILE, null, null, null, null, null, null);
        ArrayList<Device> profiles = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                DeviceType type = DeviceType.getTypeByString((c.getString(c.getColumnIndex(SQLHelper.COLUMN_TYPE))));
                if (type.getName() == "thermostat") {
                    Device profile = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                            c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                            type, c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                            new float[]{c.getFloat(c.getColumnIndex("value0")),
                                    c.getFloat(c.getColumnIndex("value1")),
                                    c.getFloat(c.getColumnIndex("value2")),
                                    c.getFloat(c.getColumnIndex("value3")),
                                    c.getFloat(c.getColumnIndex("value4")),
                                    c.getFloat(c.getColumnIndex("value5")),
                                    c.getFloat(c.getColumnIndex("value6")),
                                    c.getFloat(c.getColumnIndex("value7")),
                                    c.getFloat(c.getColumnIndex("value8")),
                                    c.getFloat(c.getColumnIndex("value9")),
                                    c.getFloat(c.getColumnIndex("value10")),
                                    c.getFloat(c.getColumnIndex("value11")),
                                    c.getFloat(c.getColumnIndex("value12")),
                                    c.getFloat(c.getColumnIndex("value13")),
                                    c.getFloat(c.getColumnIndex("value14")),
                                    c.getFloat(c.getColumnIndex("value15")),
                                    c.getFloat(c.getColumnIndex("value16")),
                                    c.getFloat(c.getColumnIndex("value17")),
                                    c.getFloat(c.getColumnIndex("value18")),
                                    c.getFloat(c.getColumnIndex("value19")),
                                    c.getFloat(c.getColumnIndex("value20")),
                                    c.getFloat(c.getColumnIndex("value21")),
                                    c.getFloat(c.getColumnIndex("value22")),
                                    c.getFloat(c.getColumnIndex("value23")),
                            });
                    profile.setUse(0);
                    Log.i("thermostatName: ", " " + c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)));
                    profiles.add(profile);
                    Log.i("added termostatName", ": -" + profile.getDeviceName());
                } else if (type.getName() == "meteoStation") {
                    Device dev = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                            c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                            type, c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                            new float[]{c.getFloat(c.getColumnIndex("value0")),
                                    c.getFloat(c.getColumnIndex("value1")),
                                    c.getFloat(c.getColumnIndex("value2")),
                                    c.getFloat(c.getColumnIndex("value3")),
                                    c.getFloat(c.getColumnIndex("value4")),
                                    c.getFloat(c.getColumnIndex("value5")),
                            });
                    dev.setUse(0);
                    profiles.add(dev);
                    Log.i("added profile METEO", ": -" + dev.getDeviceName());
                } else if (type.getName() == "RGBlight") {
                    Device dev = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
                            c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
                            type, c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
                            new float[]{c.getFloat(c.getColumnIndex("value0")),
                                    c.getFloat(c.getColumnIndex("value1")),
                                    c.getFloat(c.getColumnIndex("value2")),
                                    c.getFloat(c.getColumnIndex("value3")),
                            });
                    dev.setUse(0);
                    Log.i("meteoName: ", " " + c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)));
                    profiles.add(dev);
                    Log.i("added profile", ": -" + dev.getDeviceName());
                }
            } while (c.moveToNext());
        }
        close();
        return profiles;
    }


//    public Device getDeviceById(int devId) {
//        open();
//        Device dev;
//        Cursor c = sqLiteDatabase.query(SQLHelper.TABLE_DEVICE, null, SQLHelper.COLUMN_ID + " = ?",
//                new String[]{devId + ""}, null, null, null);
//        if (c != null && c.moveToFirst()) {
//            do {
//                dev = new Device(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)),
//                        c.getString(c.getColumnIndex(SQLHelper.COLUMN_NAME)),
//                        DeviceType.getTypeByString(c.getString(c.getColumnIndex(SQLHelper.COLUMN_TYPE))),
//                        c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ROOM_ID)),
//                        c.getFloat(c.getColumnIndex(SQLHelper.COLUMN_VALUE)));
////                dev.setDeviceId(c.getInt(c.getColumnIndex(SQLHelper.COLUMN_ID)));
//            } while (c.moveToNext());
//        }
//        close();
//        return dev;
//    }


}
