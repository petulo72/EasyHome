package pt.easyhome;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.easyhome.database.Db;
import pt.easyhome.fragments.BlindsControlDialogFragment;
import pt.easyhome.fragments.DimmerControlDialogFragment;
import pt.easyhome.fragments.MenuDialogFragment;
import pt.easyhome.fragments.NewDeviceDialogFragment;
import pt.easyhome.fragments.NewRoomDialogFragment;
import pt.easyhome.fragments.RGBControlDialogFragment;
import pt.easyhome.fragments.ThermostatControlDialogFragment;
import pt.easyhome.fragments.WeatherStationDialogFragment;
import pt.easyhome.model.Device;
import pt.easyhome.model.Room;

import static pt.easyhome.R.id.device_item_imageButton;


public class HouseActivity extends FragmentActivity {
    public static String ROOM_ID_TAG = "room_id", DEVICE_ID_TAG = "device_id";
    public static String PROFILE_ID_TAG = "profile_id";
    private LinearLayout roomLayout, roomScrollLinearLayout;
    private View roomListView, roomHeadTextView;
    private Db database;
    private ArrayList<Room> roomList;
    private ArrayList<Device> deviceList, profileList;
    private Intent houseIntent;
    private int houseId, housePort;
    private String houseIpAddress;
    //TODO stavy pripojenia: cerverna - ziadne pripojenie, modra - birdge komunikacia, zelena - lokalne
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        Log.i("---HouseActivity---", "onCreate");

        database = new Db(this.getApplicationContext());
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        roomScrollLinearLayout = (LinearLayout) findViewById(R.id.roomVerticalScrollLinearLayout);
        houseIntent = getIntent();
        /* ziskane argumenty z aktivity MAIN*/
        Bundle args = houseIntent.getExtras();
        houseId = args.getInt(MainActivity.HOUSE_ID_TAG);
        houseIpAddress = args.getString(MainActivity.HOUSE_IP_ADDRESS_TAG);
        housePort = args.getInt(MainActivity.HOUSE_PORT_TAG);

        /*listener na swipeToRefresh*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        getState(houseIpAddress, housePort);
                    }
                }, 5000);
            }
        });
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.black);
        refresh();
        getState(houseIpAddress, housePort);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void refresh() {
        roomList = database.getRoomsByHouseID(houseId);
        Log.i("RoomList obtained:", "" + roomList.toString());
        roomScrollLinearLayout.removeAllViews();

        for (final Room room : roomList) {
            LinearLayout roomLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.room_item_layout, null);
            ViewGroup newRoomItem = (ViewGroup) roomLayout.findViewById(R.id.newRoomItem);
            View addDeviceButton = newRoomItem.findViewById(R.id.addDeviceButton);
            TextView roomHeadTextView = (TextView) newRoomItem.findViewById(R.id.roomNameTextView);
            LinearLayout deviceLinearLayout = (LinearLayout) newRoomItem.findViewById(R.id.devicesHorizontalScrollLayout);
            View roomDivider = newRoomItem.findViewById(R.id.roomDivider);

            roomHeadTextView.setText(room.getRoomName());
            addDeviceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewDeviceDialogFragment newDeviceDialog = new NewDeviceDialogFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt(ROOM_ID_TAG, room.getRoomId());
                    newDeviceDialog.setArguments(arguments);
                    newDeviceDialog.show(getFragmentManager(), "newDeviceDialog");
                }
            });
            roomHeadTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MenuDialogFragment menuDialogFragment = new MenuDialogFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt(ROOM_ID_TAG, room.getRoomId());
                    menuDialogFragment.setArguments(arguments);
                    menuDialogFragment.show(getFragmentManager(), "roomMenuDialog");
                    return true;
                }
            });

            /*---adding devices into rooms from database---*/
            ArrayList<Device> devicesByRoomList = database.getDevicesByRoomID(room.getRoomId());
            ArrayList<Device> profilesByRoomList = new ArrayList<>();
            profilesByRoomList = database.getProfilesByRoomID(room.getRoomId());
            Log.i("obtainedDevices:", " " + devicesByRoomList.toString());
            Log.i("obtainedProfiles:", " " + profilesByRoomList.toString());

            for (final Device device : devicesByRoomList) {
                switch (device.getDeviceType().toString()) {
                    case "light":
                        LinearLayout lightItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView lightName = (TextView) lightItem.findViewById(R.id.device_item_textView);
                        final ImageView deviceItemButton = (ImageView) lightItem.findViewById(device_item_imageButton);
                        lightName.setText(device.getDeviceName());
                        if (device.getValue() == 0) {
                            deviceItemButton.setImageDrawable(getResources().getDrawable(R.drawable.light_bulb_off_dark));
                        } else {
                            deviceItemButton.setImageDrawable(getResources().getDrawable(R.drawable.light_bulb_on_dark));
                        }
                        deviceItemButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (device.getValue() == 0.0f) {
                                    device.setValue((float) 1.0);
                                    deviceItemButton.setImageDrawable(getResources().getDrawable(R.drawable.light_bulb_on_dark));
                                    database.updateDeviceValue(device.getDeviceId(), device.getValue());
                                } else {
                                    device.setValue((float) 0.0);
                                    deviceItemButton.setImageDrawable(getResources().getDrawable(R.drawable.light_bulb_off_dark));
                                    database.updateDeviceValue(device.getDeviceId(), device.getValue());
                                }
                                sendRequest(houseIpAddress, housePort, device.getDeviceId(), device.getValue());
                            }
                        });
                        deviceItemButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(lightItem);
                        break;
                    case "dimmer":
                        LinearLayout dimmerItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView dimmerName = (TextView) dimmerItem.findViewById(R.id.device_item_textView);
                        final ImageView dimmerItemButton = (ImageView) dimmerItem.findViewById(device_item_imageButton);
                        dimmerName.setText(device.getDeviceName());
                        dimmerItemButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DimmerControlDialogFragment dimmerControl = new DimmerControlDialogFragment();
                                Bundle devValues = new Bundle();
                                devValues.putFloat("dimmer_val", device.getValue());
                                Log.i("DIMMER -- ", "val: " + device.getValue());
                                devValues.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                dimmerControl.setArguments(devValues);
                                dimmerControl.show(getFragmentManager(), "dimmerControl");
                            }
                        });
                        dimmerItemButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(dimmerItem);
                        break;
                    case "thermometer":
                        LinearLayout thermometerItem = (LinearLayout) getLayoutInflater().inflate(R.layout.termometer_item_layout, null);
                        TextView thermoName = (TextView) thermometerItem.findViewById(R.id.termometer_item_textView);
                        final Button thermoButton = (Button) thermometerItem.findViewById(R.id.termometer_button);
                        thermoButton.setBackgroundColor(Color.TRANSPARENT);
                        float deviceValue = device.getValue();
                        thermoName.setText(device.getDeviceName());
                        thermoButton.setTextSize(25);
                        thermoButton.setText(deviceValue + " °C");//todo parsovane data, nie tieto
                        thermoButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(thermometerItem);
                        break;
                    case "jalousie":
                        LinearLayout blindsItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView blindsName = (TextView) blindsItem.findViewById(R.id.device_item_textView);
                        final ImageView blindsItemButton = (ImageView) blindsItem.findViewById(device_item_imageButton);
                        blindsItemButton.setImageDrawable(getResources().getDrawable(R.drawable.jalousie1)); //TODO ikona
                        blindsItemButton.setBackgroundColor(Color.TRANSPARENT);
                        blindsName.setText(device.getDeviceName());
                        blindsItemButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BlindsControlDialogFragment blindsControl = new BlindsControlDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                blindsControl.setArguments(args);
                                blindsControl.show(getFragmentManager(), "blinds_control_dialog");
                            }
                        });
                        blindsItemButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(blindsItem);
                        break;
                    case "gate":
                        LinearLayout gateItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView gateName = (TextView) gateItem.findViewById(R.id.device_item_textView);
                        ImageButton gateItemButton = (ImageButton) gateItem.findViewById(device_item_imageButton);
                        gateName.setText(device.getDeviceName());
                        gateItemButton.setBackground(getResources().getDrawable(R.drawable.gate));
                        gateItemButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendValueToControlUnit(device.getDeviceId(), 1);
//                                GateControlDialogFragment gateControl = new GateControlDialogFragment();
//                                Bundle args = new Bundle();
//                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
//                                gateControl.setArguments(args);
//                                gateControl.show(getFragmentManager(), "gateControlDialog");
                            }
                        });
                        gateItemButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(gateItem);
                        break;
                    case "ringer":
                        LinearLayout ringerItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView ringerText = (TextView) ringerItem.findViewById(R.id.device_item_textView);
                        ringerText.setText(device.getDeviceName());
                        ImageButton ringerButton = (ImageButton) ringerItem.findViewById(device_item_imageButton);
                        ringerButton.setImageDrawable(getResources().getDrawable(R.drawable.ringer));
                        ringerButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendValueToControlUnit(device.getDeviceId(), 1);
                            }
                        });
                        ringerButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, device.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(ringerItem);
                        break;
                    default:
                        break;
                }
            }

            for (final Device p : profilesByRoomList) {
                switch (p.getDeviceType().toString()) {
                    case "thermostat":
                        LinearLayout thermostatItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView thermostatTextView = (TextView) thermostatItem.findViewById(R.id.device_item_textView);
                        ImageButton thermostatButton = (ImageButton) thermostatItem.findViewById(device_item_imageButton);
                        thermostatTextView.setText(p.getDeviceName());
                        final float[] values = p.getValues();
                        thermostatButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ThermostatControlDialogFragment thermostatControl = new ThermostatControlDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, p.getDeviceId());
                                args.putFloatArray("values", values);
                                thermostatControl.setArguments(args);
                                thermostatControl.show(getFragmentManager(), "thermostatControl");
                            }
                        });
                        thermostatButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(PROFILE_ID_TAG, p.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(thermostatItem);
                        break;
                    case "meteoStation":
                        LinearLayout meteoItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView meteoTextView = (TextView) meteoItem.findViewById(R.id.device_item_textView);
                        meteoTextView.setText(p.getDeviceName());
                        ImageButton meteoButton = (ImageButton) meteoItem.findViewById(device_item_imageButton);
                        final float[] meteovalues = p.getValues();//TODO set Icon
                        meteoButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WeatherStationDialogFragment meteo = new WeatherStationDialogFragment();
                                Bundle args = new Bundle();
                                args.putFloatArray("meteoValues", meteovalues);
                                args.putInt(DEVICE_ID_TAG, p.getDeviceId());
                                meteo.setArguments(args);
                                meteo.show(getFragmentManager(), "meteoStation");
                            }
                        });
                        meteoButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(PROFILE_ID_TAG, p.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(meteoItem);
                        break;

                    case "RGBlight":
                        LinearLayout rgbItem = (LinearLayout) getLayoutInflater().inflate(R.layout.device_item_layout, null);
                        TextView rgbText = (TextView) rgbItem.findViewById(R.id.device_item_textView);
                        rgbText.setText(p.getDeviceName());
                        ImageButton rgbButton = (ImageButton) rgbItem.findViewById(device_item_imageButton);
                        rgbButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RGBControlDialogFragment rgbControl = new RGBControlDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, p.getDeviceId());
                                rgbControl.setArguments(args);
                                rgbControl.show(getFragmentManager(), "rgbControl");
                            }
                        });
                        rgbButton.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                MenuDialogFragment menu = new MenuDialogFragment();
                                Bundle args = new Bundle();
                                args.putInt(DEVICE_ID_TAG, p.getDeviceId());
                                menu.setArguments(args);
                                menu.show(getFragmentManager(), "menuDialog");
                                return false;
                            }
                        });
                        deviceLinearLayout.addView(rgbItem);
                        break;
                    default:
                        break;
                }
            }
            roomScrollLinearLayout.addView(roomLayout);
        }
        createAddRoomButton();
    }

    /*
    on change seekbarValue, sent new value to Arduino
     */
    public void sendValueToControlUnit(int deviceId, float value) {
        sendRequest(houseIpAddress, housePort, deviceId, value);
    }

    public void sendValuesToControlUnit(int deviceId, float[] values) {
//        sendRequest(houseIpAddress, housePort, deviceId, values); //TODO
    }

    /*
    add button for adding room to house, if roomList will be empty, this will be the only one item in list
     */
    private void createAddRoomButton() {
        LinearLayout newRoomButton = (LinearLayout) getLayoutInflater().inflate(R.layout.item_image_button, roomScrollLinearLayout);
        ImageView addButton = (ImageView) newRoomButton.findViewById(R.id.contentItem);
        newRoomButton.setGravity(Gravity.CENTER);
        addButton.setImageDrawable(getResources().getDrawable(R.drawable.plus));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewRoomDialogFragment newRoomDialog = new NewRoomDialogFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(MainActivity.HOUSE_ID_TAG, houseId);
                newRoomDialog.setArguments(arguments);
                newRoomDialog.show(getFragmentManager(), "newRoomDialog");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_house, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * send data from devices which have only one value to send
     *
     * @param houseIP
     * @param housePort
     * @param deviceId
     * @param value
     */
    public void sendRequest(String houseIP, int housePort, int deviceId, float value) {
        String url = "http://" + houseIP + ":" + housePort + "/setstate?" + deviceId + ":" + value;
        SendRequest asyncTask = new SendRequest();
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    public void getState(String houseIP, int housePort) {
        String url = "http://" + houseIP + ":" + housePort + "/getstate?";
        SendRequest asyncTask = new SendRequest();

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    private class SendRequest extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            final String url = params[0];
            String finalResponse = "";

            try {
                finalResponse = run(url);
                setValuesFromResponse(finalResponse);

            } catch (ConnectException e) {
                Log.e("connection", "connection error");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * creates request, sends ot server, get response
         *
         * @param url ip address of server
         * @return response from server
         * @throws IOException
         */
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    /**
     * parsing response from arduino
     *
     * @param json jsonString
     * @return list of floats
     */
    public List<float[]> parse(String json) {
        List<float[]> list = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                String dataByDevice = array.getString(i);
                String[] values = dataByDevice.split(":");
                list.add(new float[values.length]);
                for (int j = 0; j < values.length; j++) {
                    list.get(i)[j] = Float.parseFloat(values[j]);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
        parses responseData from arduino and sets values to devices
     */
    public void setValuesFromResponse(String response) {
        deviceList = database.getDevices();
        profileList = database.getProfiles();
        List<float[]> parsedData = parse(response);

        for (int i = 0; i < parsedData.size(); i++) {
            float[] line = parsedData.get(i);
            int parsedDeviceId = (int) line[0];
            float[] values = new float[line.length - 1];
            for (int j = 1; j < line.length; j++) {
                values[j - 1] = parsedData.get(i)[j];
            }
            Log.i("parsedID: " + parsedDeviceId, "valuesCOUNT: " + values.length);
            Log.i("parsedValues -- ", "" + Arrays.toString(values));

            for (Device devInList : deviceList) {
                if (devInList.getDeviceId() == parsedDeviceId) {
                    if (values.length == 1) {
                        devInList.setValue(values[0]);
                        database.updateDeviceValue(parsedDeviceId, values[0]);
                    }
                }
            }

            for (Device profileInList : profileList) {
                if (profileInList.getDeviceId() == parsedDeviceId) {
                    if (values.length == 4 || values.length == 6 || values.length == 24) {
                        profileInList.setValues(values);
                        database.updateProfileValues(profileInList);
                    }
                }
            }
        }
    }
}