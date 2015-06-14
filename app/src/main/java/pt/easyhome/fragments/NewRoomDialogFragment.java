package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import pt.easyhome.MainActivity;
import pt.easyhome.R;
import pt.easyhome.database.Db;
import pt.easyhome.model.Room;

/**
 * Created by Peter on 7.4.2015.
 */
public class NewRoomDialogFragment extends DialogFragment {

    private Db database;
    private View okButton, cancelButton;
    private String roomName;
    private int houseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_room_dialog_layout, container, false);
        database = new Db(getActivity());
        getDialog().setTitle("Nová izba");
        EditText roomNameEditText = (EditText) rootView.findViewById(R.id.roomNameEditText);
        okButton = rootView.findViewById(R.id.newRoomOkButton);
        cancelButton = rootView.findViewById(R.id.newRoomCancelButton);
        final Bundle houseArgs = getArguments();
        houseId = houseArgs.getInt(MainActivity.HOUSE_ID_TAG);

        roomNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                roomName = s.toString();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomName != null) {
                    Room insertingRoom = new Room(roomName, houseId);
                    database.insertRoom(insertingRoom);
                    dismiss();
                    Toast.makeText(getActivity(), "New room " + roomName + " created! for houseId: " + houseId, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please, insert room name!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}
