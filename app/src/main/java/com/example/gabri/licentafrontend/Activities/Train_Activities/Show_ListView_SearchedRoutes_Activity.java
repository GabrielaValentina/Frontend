package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.gabri.licentafrontend.Adapters.SearchedRoutesAdapter;
import com.example.gabri.licentafrontend.Domain.RouteSearched;
import com.example.gabri.licentafrontend.Domain.SearchedRoute;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_TRAIN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 4/27/2018.
 */

public class Show_ListView_SearchedRoutes_Activity extends AppCompatActivity {

    private ListView listViewRoutes;
    private String url;
    private String departure;
    private String arrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_searched_routes_activity);

        this.listViewRoutes = (ListView) findViewById(R.id.listViewSEARCHEDROUTESACTIVITY);

        this.url = getIntent().getExtras().getString("url");
        this.arrival = getIntent().getExtras().getString("arrival");
        this.departure = getIntent().getExtras().getString("departure");

        try {
            getData();
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            Log.d("error", ex.getMessage());
        }

    }

    private void getData(){

        ArrayList<RouteSearched> list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_TRAIN retrofitAPI_train = retrofit.create(RetrofitAPI_TRAIN.class);

        Call<List<SearchedRoute>> call = retrofitAPI_train.getSearchedRoutes(departure, arrival);

        call.enqueue(new Callback<List<SearchedRoute>>() {
            @Override
            public void onResponse(retrofit.Response<List<SearchedRoute>> response, Retrofit retrofit) {
                if(response.body().size()!=0){
                    for(SearchedRoute route : response.body()){
                        Log.d("rasp", route.getArrival());
                        list.add(new RouteSearched(route.getTrain(), route.getDeparture(),
                                route.getArrival(), route.getDeparture_time(), route.getArrival_time(),
                                "", Integer.parseInt(route.getId_route()+"")));
                    }
                }
                else{
                    show_message("Nu s-au găsit rute pentru datele introduse de dumneavoastră");
                }
                SearchedRoutesAdapter adapter = new SearchedRoutesAdapter(getApplicationContext(), R.layout.list_view_adapter_show_searched_routes, list);
                listViewRoutes.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
              show_message("Nu s-au găsit rute pentru datele introduse de dumneavoastră");
            }
        });
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Căutare rute");

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
