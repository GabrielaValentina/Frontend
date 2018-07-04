package com.example.gabri.licentafrontend.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gabri.licentafrontend.Activities.Train_Activities.ShowReviews_Activity;
import com.example.gabri.licentafrontend.Adapters.RouteReview_Adapter;
import com.example.gabri.licentafrontend.Adapters.TrainReview_Adapter;
import com.example.gabri.licentafrontend.Domain.Mappers.RouteReviewMapper;
import com.example.gabri.licentafrontend.Domain.Review;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_REVIEW;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RouteReview_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private String url;

    private ArrayList<RouteReviewMapper> listDetailsAboutReview;
    private RouteReview_Adapter adapter;
    private ListView listView;

    private OnFragmentInteractionListener mListener;

    public RouteReview_Fragment() {
    }

    public static RouteReview_Fragment newInstance(String param1, String param2) {
        RouteReview_Fragment fragment = new RouteReview_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_route_review_, container, false);
        ShowReviews_Activity activity = (ShowReviews_Activity) getActivity();
        url = activity.getUrl();
        this.listDetailsAboutReview = new ArrayList<>();

        try{
            populateListFromServer();
            Thread.sleep(1000);
        }catch (InterruptedException ex) {
        }

        this.listView = (ListView) view.findViewById(R.id.listViewShowReviewsFRAGMENT);

        this.adapter = new RouteReview_Adapter(this.getContext(), R.layout.list_view_adapter_show_reviews_routes, listDetailsAboutReview);

        listView.setAdapter(adapter);

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

        Call<List<RouteReviewMapper>> call = retrofitAPI_review.getAllRoutesReviews();

        call.enqueue(new Callback<List<RouteReviewMapper>>() {
            @Override
            public void onResponse(retrofit.Response<List<RouteReviewMapper>> response, Retrofit retrofit) {
                    listDetailsAboutReview.addAll(response.body());
                Log.d("hei",listDetailsAboutReview.size()+"");
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
