package pt.easyhome.miscellaneous;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.easyhome.R;

/**
 * Created by Peter on 9.4.2015.
 */
public class DeviceTypeAdapter extends ArrayAdapter<DeviceType> {


    int layoutResourceId;

    /**
     *
     * @param context
     * @param layoutResourceId layout of one item/row in listView
     */
    public DeviceTypeAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View cv, ViewGroup parent) {
        DeviceType deviceType = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(cv == null)  cv = inflater.inflate(layoutResourceId, null);

        TextView deviceItem = (TextView) cv.findViewById(R.id.deviceItem);

        deviceItem.setText(deviceType.getName());

        return cv;
    }
}
