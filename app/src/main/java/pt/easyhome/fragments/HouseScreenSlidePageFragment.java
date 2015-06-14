package pt.easyhome.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import pt.easyhome.HouseActivity;
import pt.easyhome.MainActivity;
import pt.easyhome.R;
import pt.easyhome.miscellaneous.HousePageAdapter;

/**
 * Created by Peter on 12.5.2015.
 */
public class HouseScreenSlidePageFragment extends Fragment {

    TextView houseNameTextView;
    ImageButton houseButton;
    String houseIpAddress, houseName;
    int houseId, housePort;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.house_fragment_page, container, false);

        final Bundle params = getArguments();

        houseIpAddress = params.getString(MainActivity.HOUSE_IP_ADDRESS_TAG);
        houseId = params.getInt(MainActivity.HOUSE_ID_TAG);
        housePort = params.getInt(MainActivity.HOUSE_PORT_TAG);
        houseName = params.getString(MainActivity.HOUSE_NAME);

        houseNameTextView = (TextView) rootView.findViewById(R.id.housePagerTextView);
        houseNameTextView.setText(houseName);
        houseButton = (ImageButton) rootView.findViewById(R.id.housePagerButton);

        houseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent houseIntent = new Intent(getActivity(), HouseActivity.class);
                houseIntent.putExtras(params);
                startActivity(houseIntent);
            }
        });

        return rootView;
    }

//    public void setHouseNameTextView(String name) {
//        houseNameTextView.setText(name);
//    }
}
