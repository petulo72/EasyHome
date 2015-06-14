package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import pt.easyhome.HouseActivity;
import pt.easyhome.R;

/**
 * Created by Peter on 14.5.2015.
 */
public class BlindsControlDialogFragment extends DialogFragment {

    private ImageButton upButton, downButton;
    private int deviceId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.blinds_control_layout, container, false);
        Bundle params = getArguments();
        deviceId = params.getInt(HouseActivity.DEVICE_ID_TAG);
        getDialog().setTitle("Current state");

        upButton = (ImageButton) rootView.findViewById(R.id.blindsUp);
        downButton = (ImageButton) rootView.findViewById(R.id.blindsDown);

        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        getDialog().setTitle("Opening...");
                        ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, 1);
                        return true;
                    case MotionEvent.ACTION_UP:
                        getDialog().setTitle("Current state");
                        ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, 3);
                        return true;
                }
                return false;
            }
        });

        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        getDialog().setTitle("Closing...");
                        ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, 2);
                        return true;
                    case MotionEvent.ACTION_UP:
                        getDialog().setTitle("Current state");
                        ((HouseActivity) getActivity()).sendValueToControlUnit(deviceId, 3);
                        return true;
                }
                return false;
            }
        });
        return rootView;
    }
}
