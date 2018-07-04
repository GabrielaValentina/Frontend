package com.example.gabri.licentafrontend.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Domain.DetailsAboutReview;
import com.example.gabri.licentafrontend.Domain.Mappers.RouteReviewMapper;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabri on 6/18/2018.
 */

public class RouteReview_Adapter extends ArrayAdapter<RouteReviewMapper> {

    Context context;
    int resouce;

    private int[] colors = new int[] {0x30FF0000, 0x300000FF};
    private List<RouteReviewMapper> objects;

    public RouteReview_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<RouteReviewMapper> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resouce = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(resouce, parent, false);

        String departure = getItem(position).getDeparture();
        String destination = getItem(position).getArrival();
        String comment = getItem(position).getComment();
        float numberOfStars = Float.parseFloat(getItem(position).getStars()+"");
        String time = getItem(position).getTime();
        String name = getItem(position).getName();


        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBarSHOWROUTES);
        ratingBar.setRating(numberOfStars);
        TextView textViewComment = (TextView) convertView.findViewById(R.id.textViewCommentSHOWROUTES);
        textViewComment.setText("\n" + comment);
        TextView textViewStations = (TextView) convertView.findViewById(R.id.textViewStationsSHOWROUTES);
        textViewStations.setText("\n" + departure + " - " + destination);
        TextView textViewDetails = (TextView) convertView.findViewById(R.id.textViewUserDetailsSHOWROUTES);
        textViewDetails.setText("Postat de " +  name + " în " + time.substring(0,10) +
                " (" + time.substring(11,16) + ")");

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        return convertView;
    }
}
