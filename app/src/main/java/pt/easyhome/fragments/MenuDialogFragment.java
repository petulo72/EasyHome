package pt.easyhome.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import pt.easyhome.HouseActivity;
import pt.easyhome.MainActivity;
import pt.easyhome.R;
import pt.easyhome.database.Db;

/**
 * Created by Peter on 6.4.2015.
 */
public class MenuDialogFragment extends DialogFragment {

    private Db database;
    Button editButton, deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_dialog_layout, container, false);
        database = new Db(getActivity());
        editButton = (Button) rootView.findViewById(R.id.editButton);
        deleteButton = (Button) rootView.findViewById(R.id.deleteButton);
        final Bundle args = getArguments();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (args.containsKey(MainActivity.HOUSE_ID_TAG)) {
                    database.removeHouse(args.getInt(MainActivity.HOUSE_ID_TAG));
                    ((MainActivity) getActivity()).refresh();
                    Log.i("House " + args.getInt(MainActivity.HOUSE_ID_TAG), "deleted");
                } else if (args.containsKey(HouseActivity.ROOM_ID_TAG)) {
                    database.removeRoom(args.getInt(HouseActivity.ROOM_ID_TAG));
                    ((HouseActivity) getActivity()).refresh();
                    Log.i("Room " + args.getInt(HouseActivity.ROOM_ID_TAG), "deleted");
                } else if (args.containsKey(HouseActivity.DEVICE_ID_TAG)) {
                    database.removeDevice(args.getInt(HouseActivity.DEVICE_ID_TAG));
                    ((HouseActivity) getActivity()).refresh();
                    Log.i("Device " + args.getInt(HouseActivity.DEVICE_ID_TAG), "deleted");
                } else if (args.containsKey(HouseActivity.PROFILE_ID_TAG)) {
                    database.removeProfile(args.getInt(HouseActivity.PROFILE_ID_TAG));
                    ((HouseActivity) getActivity()).refresh();
                    Log.i("Profile " + args.getInt(HouseActivity.DEVICE_ID_TAG), "deleted");
                }
                dismiss();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }
}
