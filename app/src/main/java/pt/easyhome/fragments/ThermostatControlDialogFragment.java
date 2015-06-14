package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import pt.easyhome.HouseActivity;
import pt.easyhome.R;
import pt.easyhome.database.Db;
import pt.easyhome.miscellaneous.VerticalSeekBar;

/**
 * Created by Peter on 18.5.2015.
 */
public class ThermostatControlDialogFragment extends DialogFragment {

    private float currentVal;
    private float thermoValue = 23; //TODO
    private float[] profileValues = new float[24];
    private int deviceId;
    private Db database;

    public static int activeProfile = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.thermostat_control_layout, container, false);
        //TODO zobrazovat z danej hodiny aktualnu teplotu na spodnom termostate
        //TODO podsvietit aktivny termostat.
        Bundle receivedArgs = getArguments();
        profileValues = receivedArgs.getFloatArray("values");
        deviceId = receivedArgs.getInt(HouseActivity.DEVICE_ID_TAG);
        database = new Db(getActivity().getApplicationContext());
        getDialog().setTitle("Aktuálna hodnota: ");

        LinearLayout thermostatLinearLayout = (LinearLayout) rootView.findViewById(R.id.thermostatLinearLayout);
        LinearLayout thermostatLayout = (LinearLayout) thermostatLinearLayout.findViewById(R.id.thermostatLayout);
        ImageButton up = (ImageButton) rootView.findViewById(R.id.thermostatUpImageButton);
        ImageButton down = (ImageButton) rootView.findViewById(R.id.thermostatDownImageButton);
        final TextView temperature = (TextView) rootView.findViewById(R.id.thermostatValueTextView);

        for (int i = 0; i < 24; i++) {
            RelativeLayout seekBarItem = (RelativeLayout) inflater.inflate(R.layout.thermostat_seekbar_layout, container, false);
            VerticalSeekBar seekBar = (VerticalSeekBar) seekBarItem.findViewById(R.id.thermostatSeekBar);
            seekBar.setMax(30); //maximalna hodnota
            final float currentValue = profileValues[i];
            seekBar.setProgress((int) currentValue); //nastavi hodnotu seekbaru z databazy
            TextView time = (TextView) seekBarItem.findViewById(R.id.thermostatTimeTextView);

            final int finalI = i;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    profileValues[finalI] = (float) progress;
                    getDialog().setTitle("Aktuálna hodnota: " + profileValues[finalI]);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    ((HouseActivity) getActivity()).sendValuesToControlUnit(deviceId, profileValues);
//                    database.updateProfileValues(deviceId, profileValues); //TODO updatnut data v databaze + poslat
                }
            });

            if (i < 10) {
                time.setText("0" + i + ":00");
            } else {
                time.setText(i + ":00");
            }
            thermostatLayout.addView(seekBarItem);
        }

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thermoValue < 30.0f) {
                    thermoValue += 0.5f;
                    Math.round(thermoValue);
                    ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, thermoValue);
                    database.updateDeviceValue(deviceId, thermoValue);
                    temperature.setText(String.valueOf(thermoValue));
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Dosiahnutá maximálna hodnota", Toast.LENGTH_SHORT).show();
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thermoValue > 16.0f) {
                    thermoValue -= 0.5f;
                    Math.round(thermoValue);
                    ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, thermoValue);
                    database.updateDeviceValue(deviceId, thermoValue);
                    temperature.setText(String.valueOf(thermoValue));
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Dosiahnutá minimálna hodnota", Toast.LENGTH_SHORT).show();
                }
            }
        });
        temperature.setText(String.valueOf(thermoValue));
        return rootView;
    }

    public int getActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(int value) {
        if (activeProfile == 0) {
            activeProfile = 1;
        } else {
            activeProfile = 0;
        }

    }
}
