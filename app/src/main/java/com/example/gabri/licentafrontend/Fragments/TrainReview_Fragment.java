package com.example.gabri.licentafrontend.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Activities.Train_Activities.ShowReviews_Activity;
import com.example.gabri.licentafrontend.Adapters.TrainReview_Adapter;
import com.example.gabri.licentafrontend.Adapters.TrainStationsAdapter;
import com.example.gabri.licentafrontend.Domain.DetailsAboutReview;
import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.Review;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_REVIEW;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class TrainReview_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private ArrayList<DetailsAboutReview> listDetailsAboutReview;

    private ListView listView;
    private TrainReview_Adapter adapter;
    private TextView textViewFilter;
    private String url;

    public TrainReview_Fragment() {
    }

    public static TrainReview_Fragment newInstance(String param1, String param2) {
        TrainReview_Fragment fragment = new TrainReview_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_train_review_, container, false);
        ShowReviews_Activity activity = (ShowReviews_Activity) getActivity();
        url = activity.getUrl();

        this.listDetailsAboutReview = new ArrayList<>();

        try{
            populateListFromServer();
            Thread.sleep(1000);
        }catch (InterruptedException ex) {
        }

        this.listView = (ListView) view.findViewById(R.id.listViewTRAINFRAGMENT);

        this.adapter = new TrainReview_Adapter(this.getContext(), R.layout.list_view_adapter_show_reviews_train, listDetailsAboutReview);

        listView.setAdapter(adapter);

        this.textViewFilter = (TextView) view.findViewById(R.id.editTextFilterTRAINFRAGMENT);
        this.textViewFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               (TrainReview_Fragment.this).adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void populateListFromServer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_REVIEW retrofitAPI_review = retrofit.create(RetrofitAPI_REVIEW.class);

        Call<List<Review>> call = retrofitAPI_review.getAllTrainsReviews();

        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(retrofit.Response<List<Review>> response, Retrofit retrofit) {
                    for(Review review : response.body()) {
                        listDetailsAboutReview.add(new DetailsAboutReview(review.getReview_param(),
                                review.getNumberOfStars(), review.getComment(),
                                review.getName(), review.getTime()));
                    }
            }
            @Override
            public void onFailure(Throwable t) {
                show_message("Este necesară conexiunea la internet pentru vizualizarea recenziilor!");
            }
        });
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage(message)
                .setTitle("Recenzii");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alert =builder.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ffa500"));
            }
        });
        alert.setIcon( R.drawable.smile1);
        alert.show();
    }
}
