package pt.easyhome.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pt.easyhome.MainActivity;
import pt.easyhome.R;
import pt.easyhome.database.Db;
import pt.easyhome.miscellaneous.HousePageAdapter;
import pt.easyhome.model.House;

/**
 * Created by Peter on 12.4.2015.
 */
public class NewHouseDialogFragment extends android.support.v4.app.DialogFragment {
    private Db database;
    private View okButton, cancelButton;
    String houseName, houseIpAddress, houseMacAddress, serverIpAddress, housePassword;
    private int housePort, serverPort;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_house_dialog_layout, container, false);
        database = new Db(getActivity());
        getDialog().setTitle("Nový dom");
        EditText houseNameEditText = (EditText) rootView.findViewById(R.id.houseNameEditText);
        EditText houseAddressEditText = (EditText) rootView.findViewById(R.id.houseAdressEditText);
        final EditText housePortEditText = (EditText) rootView.findViewById(R.id.housePortEditText);
        EditText houseMacAddressEditText = (EditText) rootView.findViewById(R.id.houseMacAdressEditText);
        EditText serverAddressEditText = (EditText) rootView.findViewById(R.id.serverAdressEditText);
        final EditText serverPortEditText = (EditText) rootView.findViewById(R.id.serverPortEditText);
        EditText housePasswordEditText = (EditText) rootView.findViewById(R.id.housePasswordEditText);

        okButton = rootView.findViewById(R.id.newHouseOkButton);
        cancelButton = rootView.findViewById(R.id.newHouseCancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                House insertingHouse = new House(houseName, houseIpAddress, housePort, houseMacAddress, serverIpAddress, serverPort, housePassword);
                database.insertHouse(insertingHouse);
                ((MainActivity) getActivity()).refresh();
                dismiss();
            }
        });

        houseNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                houseName = s.toString();
            }
        });
        houseAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                houseIpAddress = s.toString();
            }
        });
        housePortEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                housePort = Integer.parseInt(housePortEditText.getText().toString());
            }
        });
        houseMacAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                houseMacAddress = s.toString();
            }
        });
        serverAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serverIpAddress = s.toString();
            }
        });
        serverPortEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serverPort = Integer.parseInt(serverPortEditText.getText().toString());
            }
        });
        housePasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                housePassword = s.toString();
            }
        });

        return rootView;
    }
}
