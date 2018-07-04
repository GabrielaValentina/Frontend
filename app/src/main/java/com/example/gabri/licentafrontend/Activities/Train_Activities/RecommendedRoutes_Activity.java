package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gabri.licentafrontend.Adapters.RecommendedRoutesAdapter;
import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.RecommendedRouteRequest;
import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Repository.LocalRepository;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_RECOMMENDED_ROUTES;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_TRAIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 6/10/2018.
 */

public class RecommendedRoutes_Activity extends AppCompatActivity {

    ListView list;

    String url;
    String id_USER;
    ArrayList<RecommendedRouteRequest> recommendedRoutesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommeded_routes);

        url = getIntent().getExtras().getString("url");
        id_USER = getIntent().getExtras().getString("id_user");
        list = (ListView) findViewById(R.id.listViewRecommenededRoutesACTIVITY) ;

        getRecommendeRoutes();

        try {
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }

        RecommendedRoutesAdapter adapter = new RecommendedRoutesAdapter(this, R.layout.list_view_adapter_recommended_routes, recommendedRoutesList);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.list_view_recommended_routes_header, list, false);
        list.addHeaderView(header);
        list.setAdapter(adapter);
    }

    public void getRecommendeRoutes(){
        this.recommendedRoutesList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_RECOMMENDED_ROUTES retrofitAPI_routes = retrofit.create(RetrofitAPI_RECOMMENDED_ROUTES.class);

        Call<List<RecommendedRouteRequest>> call = retrofitAPI_routes.getAllRecommendedRoutes(id_USER);

        call.enqueue(new Callback<List<RecommendedRouteRequest>>() {
            @Override
            public void onResponse(retrofit.Response<List<RecommendedRouteRequest>> response, Retrofit retrofit) {
                if(response.body().size() == 0)
                    show_message("Pentru a putea vizualiza rutele recomandat, " +
                            "este necesară atribuirea unor recenzii, deoarece recomandarea" +
                            "se realizează pe baza recenziilor dumneavoastră.");
                    for(RecommendedRouteRequest route : response.body()){
                        recommendedRoutesList.add(route);
                    }
            }
            @Override
            public void onFailure(Throwable t) {
                show_message("Este necesară conexiunea la internet pentru a vedea recomandările!");
            }
        });
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Rute recomandate");

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
