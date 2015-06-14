package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.easyhome.R;

/**
 * Created by Peter on 22.5.2015.
 */
public class RGBControlDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rgb_control_layout, container, false);
        return rootView;
    }
}
