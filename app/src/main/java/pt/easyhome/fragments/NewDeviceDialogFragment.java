package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

import pt.easyhome.HouseActivity;
import pt.easyhome.R;
import pt.easyhome.database.Db;
import pt.easyhome.miscellaneous.DeviceType;
import pt.easyhome.miscellaneous.DeviceTypeAdapter;
import pt.easyhome.model.Device;

/**
 * Created by Peter on 8.4.2015.
 */
public class NewDeviceDialogFragment extends DialogFragment {

    Db database;
    private String deviceName;
    private int deviceId, roomId;
    private DeviceType deviceType;
    private ListView deviceTypeListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_device_dialog_layout, container, false);
        database = new Db(getActivity());
        getDialog().setTitle("Nový spotrebič");
        Bundle args = getArguments();
        roomId = args.getInt(HouseActivity.ROOM_ID_TAG);

        final DeviceTypeAdapter deviceTypeAdapter = new DeviceTypeAdapter(getActivity(), R.layout.device_list_item);
        //add all types of devices into custom ViewAdapter (deviceTypeAdapter)
        deviceTypeAdapter.addAll(Arrays.asList(DeviceType.deviceType));

        final EditText deviceNameEditText = (EditText) rootView.findViewById(R.id.deviceNameEditText);
        EditText deviceIdEditText = (EditText) rootView.findViewById(R.id.deviceIdEditText);
        deviceTypeListView = (ListView) rootView.findViewById(R.id.deviceTypeListView);
        Button okButton = (Button) rootView.findViewById(R.id.newDeviceOkButton);
        Button cancelButton = (Button) rootView.findViewById(R.id.newDeviceCancelButton);


        deviceTypeListView.setAdapter(deviceTypeAdapter);

        deviceNameEditText.addTextChangedListener(new TextWatcher() {
                                                      @Override
                                                      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                      }

                                                      @Override
                                                      public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                      }

                                                      @Override
                                                      public void afterTextChanged(Editable s) {
                                                          deviceName = s.toString();
                                                      }
                                                  }
        );
        deviceIdEditText.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                        deviceId = Integer.parseInt(s.toString());
                                                    }
                                                }
        );
        //set listener of items in deviceTypeList
        deviceTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                      @Override
                                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                          TextView deviceTypeItem = (TextView) view.findViewById(R.id.deviceItem);
//                                                          String typeName = deviceTypeItem.getText().toString();
                                                          deviceType = deviceTypeAdapter.getItem(position);
                                                      }
                                                  }

        );

        okButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (deviceType.getName() == "thermostat" || deviceType.getName() == "meteoStation" || deviceType.getName() == "RGBlight") {
                                                Device insertingDevice = new Device(deviceId, deviceName, deviceType, roomId);
                                                database.insertProfile(insertingDevice);
                                            } else {
                                                Device insertingDevice = new Device(deviceId, deviceName, deviceType, roomId);
                                                database.insertDevice(insertingDevice);
                                            }
                                            ((HouseActivity) getActivity()).refresh();
                                            dismiss();
                                        }
                                    }
        );
        cancelButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dismiss();
                                            }
                                        }
        );

        return rootView;
    }
}