package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import pt.easyhome.HouseActivity;
import pt.easyhome.R;
import pt.easyhome.database.Db;

/**
 * Created by Peter on 13.4.2015.
 */
public class DimmerControlDialogFragment extends DialogFragment {

    private float dimmerVal;
    private Db database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dimmer_control_layout, container, false);

        Bundle dimmerArgs = getArguments();
        dimmerVal = dimmerArgs.getFloat("dimmer_val");
        final int deviceId = dimmerArgs.getInt(HouseActivity.DEVICE_ID_TAG);

        getDialog().setTitle("Current value: " + Float.valueOf(dimmerVal));

        SeekBar dimmerControlSeekBar = (SeekBar) rootView.findViewById(R.id.dimmerControlSeekBar);
        dimmerControlSeekBar.setMax(255);
        dimmerControlSeekBar.setProgress((int) dimmerVal);
        dimmerControlSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDimmerValue((float) progress);
                getDialog().setTitle("Actual value: " + Float.valueOf(dimmerVal));
                ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, getDimmerValue());
                //TODO odpamatavat si staru hodnotu - pri posilani requestu. Porovnavat staru hodnotu s novou (cez Math.abs() aby bolo jedno ci sa zvysila ci znizila) a ked je rozdiel vacsi ako 5 tak poslat nanovo request a odpamatat hodnotu
                //TODO vypnuta - obrazok vypnutej a opacne
                //TODO RGB
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, getDimmerValue());
                database = new Db(getActivity().getApplicationContext());
                database.updateDeviceValue(deviceId, dimmerVal);
                ((HouseActivity) getActivity()).refresh();
            }
        });
        return rootView;
    }

    public float getDimmerValue() {
        return dimmerVal;
    }

    public void setDimmerValue(float dimmerValue) {
        this.dimmerVal = dimmerValue;
    }
}
