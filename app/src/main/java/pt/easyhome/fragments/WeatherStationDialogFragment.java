package pt.easyhome.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.easyhome.HouseActivity;
import pt.easyhome.R;

/**
 * Created by Peter on 18.5.2015.
 */
public class WeatherStationDialogFragment extends DialogFragment {

//    Vonkajšia Teplota,
//    • Vonkajšia Vlhkost,
//    • Priemerná rýchlost vetra,
//    • Rýchlost vetra v nárazoch,
//    • Smer vetra,
//    • Zrážky.

    private float temperature, humidity, windSpeed, windgust, precipitation, windDirection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wheather_station_layout, container, false);
        getDialog().setTitle("Meteostanica");

        Bundle receivedArgs = getArguments();
        temperature = receivedArgs.getFloatArray("meteoValues")[0];
        humidity = receivedArgs.getFloatArray("meteoValues")[1];
        windSpeed = receivedArgs.getFloatArray("meteoValues")[2];
        windgust = receivedArgs.getFloatArray("meteoValues")[3];
        precipitation = receivedArgs.getFloatArray("meteoValues")[4];
        windDirection = receivedArgs.getFloatArray("meteoValues")[5];

        TextView temperatureTextView = (TextView) rootView.findViewById(R.id.meteoTemperatureTextView);
        TextView humidityTextView = (TextView) rootView.findViewById(R.id.meteoHumidityTextView);
        TextView windSpeedTextView = (TextView) rootView.findViewById(R.id.meteoWindSpeedTextView);
        TextView windGustTextView = (TextView) rootView.findViewById(R.id.meteoWindGustTextView);
        TextView windDirectionTextView = (TextView) rootView.findViewById(R.id.meteoWindDirectionTextView);
        TextView precipitationTextView = (TextView) rootView.findViewById(R.id.meteoPrecipitationTextView);

        temperatureTextView.setText(String.valueOf(temperature) + " °C");
        humidityTextView.setText(String.valueOf(humidity) + " %");
        windSpeedTextView.setText(String.valueOf(windSpeed) + " m/s");
        windGustTextView.setText(String.valueOf(windgust) + " m/s");
        precipitationTextView.setText(String.valueOf(precipitation) + " mm");

        windDirectionTextView.setText(String.valueOf(windDirection) + " °");

        return rootView;
    }
}
