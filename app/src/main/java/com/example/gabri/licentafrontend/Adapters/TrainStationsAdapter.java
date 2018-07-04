package com.example.gabri.licentafrontend.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;

/**
 * Created by gabri on 4/12/2018.
 */

public class TrainStationsAdapter extends ArrayAdapter<TrainStationAdapter> {

    private Context myContext;
    private int myRecouce;
    int color = Color.parseColor("#FFE4C4");
    int color1 = Color.parseColor("#B0C4DE");
    private int[] colors = new int[] {color, color1};

    public TrainStationsAdapter(Context context, int resouce, ArrayList<TrainStationAdapter> objects){
        super(context, resouce, objects);
        myContext = context;
        myRecouce = resouce;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = LayoutInflater.from(myContext);
            convertView = inflater.inflate(myRecouce, parent, false);

            String station_name = getItem(position).getStation_name();
            String arrival_time = getItem(position).getArrival_time();
            String departure_time = getItem(position).getDeparture_time();

            TextView textStationName = convertView.findViewById(R.id.textViewStationLISTVIEWADAPTERSTATIONS);
            textStationName.setText(station_name);

            TextView textArrivalTime = convertView.findViewById(R.id.textViewArrivalTimeLISTVIEWADAPTERSTATIONS);
            textArrivalTime.setText(arrival_time);

            TextView textDepartureTime = convertView.findViewById(R.id.textViewDepartureTimeLISTVIEWADAPTERSTATIONS);
            textDepartureTime.setText(departure_time);

            int colorPos = position % colors.length;
            convertView.setBackgroundColor(colors[colorPos]);

            return convertView;
    }
}
