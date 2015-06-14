package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pt.easyhome.HouseActivity;
import pt.easyhome.R;

/**
 * Created by Peter on 16.5.2015.
 */
public class GateControlDialogFragment extends DialogFragment {

    private Button openButton, closeButton;
    private int deviceId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gate_control_layout, container, false);

        Bundle receivedArgs = getArguments();
        deviceId = receivedArgs.getInt(HouseActivity.DEVICE_ID_TAG);
        getDialog().setTitle("Current state: ...");
        openButton = (Button) rootView.findViewById(R.id.gateButtonOpen);
        closeButton = (Button) rootView.findViewById(R.id.gateButtonClose);

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, 1);
            }
        });

        openButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("bttnOPEN", "stlacene");
                        getDialog().setTitle("Current state: Opening...");
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.i("bttnOPEN", "pustene");
                        getDialog().setTitle("Current state: ...");
                        return true;
                }
                return false;
            }
        });

        closeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("bttnCLOSE", "stlacene");
                        getDialog().setTitle("Current state: Closing...");
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.i("bttnCLOSE", "pustene");
                        getDialog().setTitle("Current state: ...");
                        return true;
                }
                return false;
            }
        });
        return rootView;
    }
}
