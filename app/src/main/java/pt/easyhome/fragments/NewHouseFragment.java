package pt.easyhome.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pt.easyhome.R;

/**
 * Created by Peter on 12.5.2015.
 */
public class NewHouseFragment extends Fragment {
    private ImageView addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_house_fragment_layout, container, false);

        addButton = (ImageView) rootView.findViewById(R.id.houseAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewHouseDialogFragment newHouse = new NewHouseDialogFragment();
                newHouse.show(getFragmentManager(),"newHouseDialog");
            }
        });


        return rootView;
    }
}
