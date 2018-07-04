package com.example.gabri.licentafrontend.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Activities.Train_Activities.SearchRoute_Activity;
import com.example.gabri.licentafrontend.Domain.RouteSearched;
import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;

/**
 * Created by gabri on 4/27/2018.
 */

public class SearchedRoutesAdapter extends ArrayAdapter<RouteSearched> {

    private Context myContext;
    private int myRecouce;
    private int[] colors = new int[] {0x30FF0000, 0x300000FF};

    public SearchedRoutesAdapter(Context context, int resouce, ArrayList<RouteSearched> objects){
        super(context, resouce, objects);
        myContext = context;
        myRecouce = resouce;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //   View view = super.getView(position, convertView, parent);
        String idTrain = getItem(position).getId_train();
        String arrival_station = getItem(position).getArrival_station();
        String departure_station = getItem(position).getDeparture_station();
        String duration = getItem(position).getTotal_duration();
        String arrival_time = getItem(position).getArrival_hour();
        String departure_time = getItem(position).getDeparture_hour();
        int routeNumber = getItem(position).getRouteNumber();

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myRecouce, parent, false);

        TextView textTrain = (TextView) convertView.findViewById(R.id.textViewIdTrainSEARCHEDROUTESADAPTER);
        textTrain.setText("\n \n \n" + " " +idTrain);

        TextView textArrivalTime = (TextView) convertView.findViewById(R.id.textViewArrivalTimeSEARCHEDROUTESADAPTER);
        textArrivalTime.setText("Ora sosirii "+ "\n" + arrival_time.substring(11,16));
        textArrivalTime.setTextSize(18);

        TextView textDepartureTime = (TextView) convertView.findViewById(R.id.textViewDepatureTimeSEARCHEDROUTESADAPTER);
        textDepartureTime.setText("Ora plecarii " + "\n" + departure_time.substring(11,16));
        textDepartureTime.setTextSize(18);

        TextView textDepartureAndArrival = (TextView) convertView.findViewById(R.id.textViewDepartureAndestinationSEARCHEDROUTESADAPTAER);
        textDepartureAndArrival.setText("       " + departure_station + " - " + arrival_station);
        textDepartureAndArrival.setTextSize(22);

        int colorPos = routeNumber % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);
        return convertView;

    }
}
