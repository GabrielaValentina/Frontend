package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.gabri.licentafrontend.Database.DatabaseHelper;
import com.example.gabri.licentafrontend.Domain.Landmark;
import com.example.gabri.licentafrontend.Domain.Mappers.LandmarkDetailsMapper;
import com.example.gabri.licentafrontend.Domain.RecommendedRouteRequest;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_LANDMARK;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_RECOMMENDED_ROUTES;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 5/2/2018.
 */

public class Landmarks_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private MapView mapView;

    private ArrayList<LandmarkDetailsMapper> landmarksList;

    private String url;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmarks_activity);
        url = getIntent().getExtras().getString("url");
        databaseHelper = new DatabaseHelper(this);

        try{
            landmarksList = getLandmarks();
            Thread.sleep(2000);
        }catch (InterruptedException ex) {
        }

        mapView = (MapView) findViewById(R.id.googleMapLANDMARKS);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
            populateMap();


    }

    public ArrayList<LandmarkDetailsMapper> getLandmarks(){
        ArrayList<LandmarkDetailsMapper> list = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_LANDMARK retrofitAPI_landmarks = retrofit.create(RetrofitAPI_LANDMARK.class);

        Call<List<LandmarkDetailsMapper>> call = retrofitAPI_landmarks.getAllLandmarks();

        call.enqueue(new Callback<List<LandmarkDetailsMapper>>() {
            @Override
            public void onResponse(retrofit.Response<List<LandmarkDetailsMapper>> response, Retrofit retrofit) {
                if(databaseHelper.getAllLandmarks().size() != 0)
                    databaseHelper.deleteLandmarkTable();

                for(LandmarkDetailsMapper landmark : response.body()){
                    list.add(landmark);
                    databaseHelper.insertNewLandmark(Long.parseLong(landmark.getId()+""),
                            landmark.getLatitude(), landmark.getLongitude(),
                            landmark.getLocation(), landmark.getDescription(),
                            landmark.getRoute_id(), landmark.getDeparture(),
                            landmark.getDestination());
                }
            }
            @Override
            public void onFailure(Throwable t) {
                List<LandmarkDetailsMapper> list_from_memory = getLocalLandmarks();
                for(LandmarkDetailsMapper landmark : list_from_memory) {
                    list.add(landmark);
                    Log.d("landmarks = ", landmark.getLocation());
                }
            }
        });
        return list;
    }

    public List<LandmarkDetailsMapper> getLocalLandmarks(){
        return databaseHelper.getAllLandmarks();
    }

    public void populateMap(){
        ArrayList<LatLng> latitudeLongitudeList = new ArrayList<>();
        int height = 150;
        int width = 150;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.castle_menu);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        if(landmarksList.size() == 0 ) {
            for (LandmarkDetailsMapper landmark : databaseHelper.getAllLandmarks()) {
                myMap.addMarker(new MarkerOptions().position(new LatLng(landmark.getLatitude(),
                        landmark.getLongitude())).title(landmark.getLocation()
                        + " - " + landmark.getDescription()).snippet("Ruta: " + landmark.getDeparture() + " - " +
                        landmark.getDestination()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                latitudeLongitudeList.add(new LatLng(landmark.getLatitude(), landmark.getLongitude()));
            }
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latitudeLongitudeList.
                    get(0), 8));
        }
        else{
            for (LandmarkDetailsMapper landmark : landmarksList) {
                myMap.addMarker(new MarkerOptions().position(new LatLng(landmark.getLatitude(),
                        landmark.getLongitude())).title(landmark.getLocation()
                        + " - " + landmark.getDescription()).snippet("Ruta: " + landmark.getDeparture() + " - " +
                        landmark.getDestination()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                latitudeLongitudeList.add(new LatLng(landmark.getLatitude(), landmark.getLongitude()));
            }
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latitudeLongitudeList.
                    get(0), 8));
            }
        }
}


