package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Adapters.PagerAdapter;
import com.example.gabri.licentafrontend.Domain.Landmark;
import com.example.gabri.licentafrontend.Domain.Mappers.LandmarkDetailsMapper;
import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.Fragments.DetailsAboutTrainFragment;
import com.example.gabri.licentafrontend.Fragments.GoogleMapFragment;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Repository.LocalRepository;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_LANDMARK;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_TRAIN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 4/2/2018.
 */

public class MyTrainActivity extends AppCompatActivity implements GoogleMapFragment.OnFragmentInteractionListener,
                                                                  DetailsAboutTrainFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TextView editTextTrainNumber;

    private Long user_id;
    private String url;
    private String train_number;
    private String route_number;
    private String last_name_USER;
    private String first_name_USER;
    private String email_address_USER;
    private ArrayList<TrainStationAdapter> train_station_adapter;
    private ArrayList<Landmark> landmarks_list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_train_activity);

        this.train_station_adapter = new ArrayList<>();
        this.landmarks_list = new ArrayList<>();

        url = getIntent().getExtras().getString("url");
        user_id = Long.parseLong(getIntent().getExtras().getString("user_id"));
        train_number = getIntent().getExtras().getString("train_number");
        last_name_USER = getIntent().getExtras().getString("last_name");
        first_name_USER = getIntent().getExtras().getString("first_name");
        email_address_USER = getIntent().getExtras().getString("email_address");
        route_number = getIntent().getExtras().getString("route_number");

        this.editTextTrainNumber = (TextView) findViewById(R.id.textViewMyTrainNumberMYTRAIN);
        this.editTextTrainNumber.setText("Trenul " + train_number);

        try {
            get_informations();
            get_informations_about_landmarks();
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            Log.d("ex", ex.getMessage());
        }

        //set tabs
        this.tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Harta"));
        tabLayout.addTab(tabLayout.newTab().setText("Detalii"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());}
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    public void get_informations(){
        this.train_station_adapter = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_TRAIN retrofitAPI_train = retrofit.create(RetrofitAPI_TRAIN.class);

        Long id_route = Long.parseLong(route_number);
        Call<List<StationMapper1>> call = retrofitAPI_train.getStationsByRouteId(id_route);

        call.enqueue(new Callback<List<StationMapper1>>() {
            @Override
            public void onResponse(retrofit.Response<List<StationMapper1>> response, Retrofit retrofit) {

                try {
                    for(StationMapper1 station : response.body()){
                        if(station.getStation_number_in_route() ==1)
                            train_station_adapter.add(new TrainStationAdapter(station.getName(),  "-", station.getDeparture_time().substring(11,16)));
                        else
                            if( station.getStation_number_in_route() == response.body().size())
                                train_station_adapter.add(new TrainStationAdapter(station.getName(),  station.getArrival_time().substring(11,16), "-"));
                            else
                                train_station_adapter.add(new TrainStationAdapter(station.getName(),  station.getArrival_time().substring(11,16), station.getDeparture_time().substring(11,16)));
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "error onResponse");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void get_informations_about_landmarks(){
        this.landmarks_list = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_LANDMARK retrofitAPI= retrofit.create(RetrofitAPI_LANDMARK.class);

        Long id_route = Long.parseLong(route_number);
        Call<List<Landmark>> call = retrofitAPI.getAllLandmarksByRoute(id_route);

        call.enqueue(new Callback<List<Landmark>>() {
            @Override
            public void onResponse(retrofit.Response<List<Landmark>> response, Retrofit retrofit) {

                try {
                    for(Landmark landmark : response.body()){
                      landmarks_list.add(new Landmark(landmark.getLatitude(), landmark.getLongitude(),
                              landmark.getLocation(), landmark.getDescription(), landmark.getId_route()));
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "error onResponse");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public String getUserName(){
        return first_name_USER + " " + last_name_USER;
    }

    public String getTrainNumber(){
        return train_number;
    }

    public String getRoute(){
        return route_number;
    }

    public String getUrl(){
        return url;
    }

    public String getEmail(){
        return email_address_USER;
    }

    public ArrayList<TrainStationAdapter> getTrain_station_adapter(){
        return train_station_adapter;
    }

    public ArrayList<Landmark> getLandmarks_list(){
        return landmarks_list;
    }

    public Long getUserId() { return user_id;}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
