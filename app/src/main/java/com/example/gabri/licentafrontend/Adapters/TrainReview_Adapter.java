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
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Domain.DetailsAboutReview;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabri on 5/2/2018.
 */

public class TrainReview_Adapter extends ArrayAdapter<DetailsAboutReview> {

    Context context;
    int resouce;
    private int[] colors = new int[] {0x30FF0000, 0x300000FF};

    private Filter filter;
    private List<DetailsAboutReview> objects;


    public TrainReview_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<DetailsAboutReview> objects) {
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

        String train = getItem(position).getType();
        float numberOfStars = Float.valueOf(getItem(position).getStarsNumber()+"");
        String comment = getItem(position).getComment();
        String user = getItem(position).getUser();
        String date = getItem(position).getDate();

        TextView textViewTrain = (TextView) convertView.findViewById(R.id.textViewTrainLISTVIEWADAPTERREVIEWSTRAIN);
        textViewTrain.setText("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + train);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBarLISTVIEWADAPTERREVIEWSTRAIN);
        ratingBar.setRating(numberOfStars);
        TextView textViewComment = (TextView) convertView.findViewById(R.id.textViewCommentLISTVIEWADAPTERREVIEWSTRAIN);
        textViewComment.setText("\n" + comment);
        TextView textViewDetails = (TextView) convertView.findViewById(R.id.textViewUserLISTVIEWADAPTERREVIEWSTRAIN);
        textViewDetails.setText("Postat de " + user + " în data de " + date.substring(0,10) +
                " (" + date.substring(11,16) + ")");

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AppFilter<DetailsAboutReview>(objects);
        return filter;
    }

    private class AppFilter<T> extends Filter {

        private ArrayList<DetailsAboutReview> sourceObjects;

        public AppFilter(List<DetailsAboutReview> objects) {
            sourceObjects = new ArrayList<DetailsAboutReview>();
            synchronized (this) {
                sourceObjects.addAll(objects);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            String filterSeq = chars.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<DetailsAboutReview> filter = new ArrayList<>();
                for (DetailsAboutReview object : sourceObjects) {
                    if (object.getType().toLowerCase().contains(filterSeq))
                        filter.add(object);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            ArrayList<T> filtered = (ArrayList<T>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((DetailsAboutReview) filtered.get(i));
            notifyDataSetInvalidated();
        }
    }

}
