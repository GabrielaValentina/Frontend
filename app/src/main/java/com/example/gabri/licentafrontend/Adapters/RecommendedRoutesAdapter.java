package com.example.gabri.licentafrontend.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Domain.RecommendedRouteRequest;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;

/**
 * Created by gabri on 6/11/2018.
 */

public class RecommendedRoutesAdapter extends ArrayAdapter<RecommendedRouteRequest> {
    private Context myContext;
    private int myRecouce;
    int color = Color.parseColor("#FFE4C4");

    public RecommendedRoutesAdapter(Context context, int resouce, ArrayList<RecommendedRouteRequest> objects){
        super(context, resouce, objects);
        myContext = context;
        myRecouce = resouce;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myRecouce, parent, false);

        String departure = getItem(position).getDeparture();
        String destination = getItem(position).getDestination();
        String train = getItem(position).getTrain();

        TextView textDeparture = convertView.findViewById(R.id.textViewDepartureRECOMMENEDEROUTES);
        textDeparture.setText("\n \n" + departure);

        TextView textDestination = convertView.findViewById(R.id.textViewDestinationRECOMMENEDEROUTES);
        textDestination.setText("\n \n" + destination);

        TextView textTrain = convertView.findViewById(R.id.textViewTrainRECOMMENEDEROUTES);
        textTrain.setText("\n \n" + train);

        convertView.setBackgroundColor(color);

        return convertView;
    }
}
