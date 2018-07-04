package com.example.gabri.licentafrontend.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Alarm_Activity_Bun;
import com.example.gabri.licentafrontend.Activities.Train_Activities.MyTrainActivity;
import com.example.gabri.licentafrontend.Activities.Train_Activities.Review_Activity;
import com.example.gabri.licentafrontend.Adapters.TrainStationsAdapter;
import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;

public class DetailsAboutTrainFragment extends Fragment {
    // the fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    private ImageButton imageButton;
    private ImageButton imageButtonReview;

    private String mParam1;
    private String mParam2;

    private Long user_id;
    private String userName;
    private String currentTrain;
    private String currentRoute;
    private String url;
    private String email;
    private ArrayList<TrainStationAdapter> list_train_stations;

    MyTrainActivity activity;

    private OnFragmentInteractionListener mListener;

    public DetailsAboutTrainFragment() {
    }

    public static DetailsAboutTrainFragment newInstance(String param1, String param2) {
        DetailsAboutTrainFragment fragment = new DetailsAboutTrainFragment();
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

    public void populateListView(){
        list_train_stations = activity.getTrain_station_adapter();
        TrainStationsAdapter adapter = new TrainStationsAdapter(this.getContext(), R.layout.list_view_adapter_show_stations, list_train_stations);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_details_about_train, container, false);
        listView = (ListView) view.findViewById(R.id.listViewSTATIONS);
        this.imageButton = (ImageButton) view.findViewById(R.id.imageButtonMakeReviewTrainDetailsFRAGMENT);
        this.imageButtonReview = (ImageButton) view.findViewById(R.id.imageButtonReviewDETAILSFRAGMENT);


        //take informations from mytrainactivity
        activity = (MyTrainActivity) getActivity();
        this.user_id = activity.getUserId();
        this.userName = activity.getUserName();
        this.currentTrain = activity.getTrainNumber();
        this.currentRoute = activity.getRoute();
        this.url = activity.getUrl();
        this.email = activity.getEmail();

        try{
            populateListView();
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            Log.d("ex", ex.getMessage());
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Alarm_Activity_Bun.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userName", userName);
                bundle.putSerializable("currentTrain", currentTrain);
                bundle.putSerializable("currentRoute", currentRoute);
                bundle.putSerializable("email", email);
                bundle.putSerializable("url", url);
                bundle.putSerializable("stations",list_train_stations);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        imageButtonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Review_Activity.class);
                intent.putExtra("id_user", user_id+"");
                intent.putExtra("userName", userName);
                intent.putExtra("currentTrain", currentTrain);
                intent.putExtra("currentRoute", currentRoute);
                intent.putExtra("email", email);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        return view;
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
}
