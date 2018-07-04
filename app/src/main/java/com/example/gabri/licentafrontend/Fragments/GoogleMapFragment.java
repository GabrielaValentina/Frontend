package com.example.gabri.licentafrontend.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gabri.licentafrontend.Activities.Train_Activities.GPSTracker;
import com.example.gabri.licentafrontend.Activities.Train_Activities.MyTrainActivity;
import com.example.gabri.licentafrontend.Activities.User_Activities.RegistrationConfirmation_Activity;
import com.example.gabri.licentafrontend.Domain.Landmark;
import com.example.gabri.licentafrontend.Domain.Mappers.LandmarkDetailsMapper;
import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_LANDMARK;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_USER;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static android.R.attr.targetSdkVersion;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private MapView mapView;
    View view;

    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    private Button imageButtonAddNewPlace;
    private EditText editTextAddDescriptionNewPlace;

    private ArrayList<TrainStationAdapter> stationList;
    private ArrayList<Landmark> landmarksList;
    private String current_location;
    private Float current_latitude;
    private Float current_longitude;
    private Long current_route;
    private String url;
    private Boolean isAdded;
    private Marker marker;

    MyTrainActivity activity;

    public GoogleMapFragment() {
    }

    public static GoogleMapFragment newInstance(String param1, String param2) {
        GoogleMapFragment fragment = new GoogleMapFragment();
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
        view = inflater.inflate(R.layout.fragment_google_map, container, false);
        activity = (MyTrainActivity) getActivity();
        stationList = activity.getTrain_station_adapter();
        current_route = Long.parseLong(activity.getRoute());
        Log.d("curr r ", current_route + "");
        url = activity.getUrl();
        marker = null;
        isAdded = false;
        return view;
    }

    public boolean selfPermissionGranted(String permission) {
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {

                result = getContext().checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                result = PermissionChecker.checkSelfPermission(getContext(), permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    public void getPos() {

        locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //if network provider is enable
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if(marker != null)
                                marker.remove();

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Log.d("schimbat = ", latitude + "");
                            current_latitude = Float.parseFloat(latitude+"");
                            current_longitude = Float.parseFloat(longitude+"");
                            LatLng latLng = new LatLng(latitude, longitude);
                            int height = 100;
                            int width = 100;
                            if(isAdded()) {
                                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pin);

                                Bitmap b = bitmapdraw.getBitmap();
                                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                                //Geocorder
                                Geocoder geocoder = new Geocoder(getContext().getApplicationContext());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    String locality = addresses.get(0).getLocality();
                                    locality += ",Romania";
                                    current_location = locality;
                                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(locality).icon(
                                            BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                           // double latitude = provider..getLatitude();
                            //double longitude = location.getLongitude();
                            double latitude = 12;
                            double longitude = 24;
                            Log.d("lattttt = ", latitude + "");
                            LatLng latLng = new LatLng(latitude, longitude);
                            int height = 100;
                            int width = 100;
                            if(isAdded()){
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pin);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                            //Geocorder
                            Geocoder geocoder = new Geocoder(getContext().getApplicationContext());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                String locality = addresses.get(0).getLocality();
                                //   locality += ",Romania";
                                mMap.addMarker(new MarkerOptions().position(latLng).title(locality).icon(
                                        BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        }
                    });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            int height = 100;
                            int width = 100;
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.castle_menu);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                            //Geocorder
                            Geocoder geocoder = new Geocoder(getContext());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                String locality = addresses.get(0).getLocality();
                                // locality += ",Romania";
                                mMap.addMarker(new MarkerOptions().position(latLng).title(locality).icon(
                                        BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }
                        @Override
                        public void onProviderEnabled(String provider) {
                        }
                        @Override
                        public void onProviderDisabled(String provider) {}
                    });
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.googleMap);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        ActivityCompat.requestPermissions(this.getActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        this.editTextAddDescriptionNewPlace = (EditText) view.findViewById(R.id.editTextAddDescriptionGOOGLEMAP);
        this.imageButtonAddNewPlace = (Button) view.findViewById(R.id.imageButtonAddNewPlaceGOOGLEMAP);

        this.editTextAddDescriptionNewPlace.setOnClickListener(this);

        imageButtonAddNewPlace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("imageButtonAddNewPlace","imageButtonAddNewPlace");
                pushButtonAddNewPlace();
            }
        });

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getPos(); // --------------------------------------------------------------------------------------
        }

    }

    private  void getLocation2(){
        GPSTracker gps = new GPSTracker(this.getContext());
        if(gps.canGetLocation()){
            editTextAddDescriptionNewPlace.setText(gps.getLatitude() + " - "+gps.getLongitude());
        }
    }

    private void getLocation() {
        String lattitude;
        String longitude;
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            Log.d("if","if");

        } else {

            Log.d("locationManager", locationManager.toString());
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.d("Lattttitude = " + lattitude,
                        "\n" + "Longitude = " + longitude);

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.d("Lattttitude = " + lattitude,
                        "\n" + "Longitude = " + longitude);


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.d("Latttttitude = " + lattitude,
                        "\n" + "Longitude = " + longitude);

            } else {
                Log.d("Unble ", "Unble to Trace your location");
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
     //   if(isAdded()) {
            mMap = googleMap;
            ArrayList<LatLng> latLngArrayList = new ArrayList<>();
            stationList = activity.getTrain_station_adapter();
            landmarksList = activity.getLandmarks_list();
            ArrayList<String> stations = new ArrayList<>();
            for (TrainStationAdapter station : stationList) {
                stations.add(station.getStation_name());
                Log.d("station = ", station.getStation_name());
            }

            for (int i = 0; i < stations.size(); i++) {
                if (Geocoder.isPresent()) {
                    try {
                        String location = stations.get(i);
                        Geocoder gc = new Geocoder(this.getContext());
                        List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects
                        List<LatLng> ll = new ArrayList<LatLng>(addresses.size());
                        // A list to save the coordinates if they are available
                        for (Address a : addresses) {
                            if (a.hasLatitude() && a.hasLongitude()) {
                                ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                                Log.d(location + " " + "lat -> " + a.getLatitude(), "long -> " + a.getLongitude());
                                latLngArrayList.add(new LatLng(a.getLatitude(), a.getLongitude()));
                            }
                        }
                    } catch (IOException e) {
                    }
                }
                mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title(stations.get(i)));
            }

            PolylineOptions polygonOptions = new PolylineOptions();
            for (int i = 0; i < latLngArrayList.size() - 1; i++) {
                polygonOptions.add(latLngArrayList.get(i));
            }
            if(latLngArrayList.size()!=0) {
                polygonOptions = polygonOptions.add(latLngArrayList.get(latLngArrayList.size() - 1)).color(Color.RED).geodesic(true);
                mMap.addPolyline(polygonOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngArrayList.get(latLngArrayList.size() - 1), 10));
            }

            //add landmarks

            ArrayList<LatLng> latitudeLongitudeList = new ArrayList<>();
            int height = 150;
            int width = 150;
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.castle_menu);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            for (Landmark landmark : landmarksList) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(landmark.getLatitude(),
                        landmark.getLongitude())).title(landmark.getLocation()
                        + " - " + landmark.getDescription()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                latitudeLongitudeList.add(new LatLng(landmark.getLatitude(), landmark.getLongitude()));
            }
      //  }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void pushButtonAddNewPlace() {
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.castle_menu);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        String description = editTextAddDescriptionNewPlace.getText().toString();
        LatLng latLng = new LatLng(current_latitude, current_longitude);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_LANDMARK retrofitAPI = retrofit.create(RetrofitAPI_LANDMARK.class);
        Landmark landmark = new Landmark(current_latitude, current_longitude, current_location,
                editTextAddDescriptionNewPlace.getText().toString(), Long.parseLong("2"));
        Call<Landmark> call = retrofitAPI.addNewLandmark(landmark, "JSON");

        call.enqueue(new Callback<Landmark>() {
            @Override
            public void onResponse(retrofit.Response<Landmark> response, Retrofit retrofit) {
                try {
                    if (response.body() == null) {
                        show_message("Nu s-a realizat adăugarea obiectivului turistic!");
                        isAdded = false;
                    } else {
                        show_message("Adăugarea s-a realizat cu succes!");
                        mMap.addMarker(new MarkerOptions().position(latLng).title(current_location + ": " + description).icon(
                                BitmapDescriptorFactory.fromBitmap(smallMarker)));
                        isAdded = true;
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
    public void onClick(View v) {
        if(editTextAddDescriptionNewPlace.getText().toString().equals("Descrierea locului..."))
            editTextAddDescriptionNewPlace.setText("");
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage(message)
                .setTitle("");

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


    public void save_landmark() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_LANDMARK retrofitAPI = retrofit.create(RetrofitAPI_LANDMARK.class);
        Landmark landmark = new Landmark(current_latitude, current_longitude, current_location,
                editTextAddDescriptionNewPlace.getText().toString(), Long.parseLong("2"));
        Call<Landmark> call = retrofitAPI.addNewLandmark(landmark, "JSON");

        call.enqueue(new Callback<Landmark>() {
            @Override
            public void onResponse(retrofit.Response<Landmark> response, Retrofit retrofit) {
                try {
                    if (response.body() == null) {
                        show_message("Nu s-a realizat adăugarea obiectivului turistic!");
                        isAdded = false;
                    } else {
                        show_message("Adăugarea s-a realizat cu succes!");
                        isAdded = true;
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
    public void onDestroy() {
        locationManager = null;
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        locationManager = null;
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        locationManager = null;
        super.onPause();
    }
}
