package com.example.gabri.licentafrontend.Activities.User_Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabri.licentafrontend.Activities.Train_Activities.Main_Train_Activity;
import com.example.gabri.licentafrontend.Database.DatabaseHelper;
import com.example.gabri.licentafrontend.Domain.Mappers.RouteMapper;
import com.example.gabri.licentafrontend.Domain.Mappers.RouteRequestForBD;
import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.User;
import com.example.gabri.licentafrontend.Domain.UserDevice;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Repository.LocalRepository;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_TRAIN;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_USER;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import android.provider.Settings.Secure;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener{

    private final String ip = "192.168.100.6";
    private final String url = "http://"+ ip +":8080/";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView editTextRegister;
    private TextView editTextConfirmation;
    private Button buttonLogin;
    private String android_id;

    private DatabaseHelper databaseHelper;

    private LocalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        databaseHelper = new DatabaseHelper(this);

        android_id = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);

        this.editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        this.editTextEmail.setOnClickListener(this);
        this.editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        this.editTextPassword.setOnClickListener(this);
        this.editTextRegister =  (TextView) findViewById(R.id.textViewRedirectRegisterLOGINACT);
        SpannableString content = new SpannableString("Înregistrare");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        editTextRegister.setText(content);
        this.editTextConfirmation = (TextView) findViewById(R.id.textViewRedirectConfirmationLOGIN);
        SpannableString contentConfirm = new SpannableString("Confirmare cont");
        contentConfirm.setSpan(new UnderlineSpan(), 0, contentConfirm.length(), 0);
        editTextConfirmation.setText(contentConfirm);
        this.buttonLogin = (Button) findViewById(R.id.buttonLOGIN);

        try {
            getLocalData();
            Thread.sleep(1000);
            getAllRoutes();
            getAllStations();
        }catch (InterruptedException ex){

        }

    }

    public void pushButtonRegister(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.putExtra("arg_ip", ip);
        startActivity(intent);
    }

    public void pushButtonConfirmation(View view){
        Intent intent = new Intent(getApplicationContext(), RegistrationConfirmation_Activity.class);
        intent.putExtra("arg_ip", ip);
        intent.putExtra("email", "");
        startActivity(intent);
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Date incorecte!");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editTextEmail.setText("");
                editTextPassword.setText("");
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

    public void getLocalData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_USER retrofitAPI_user = retrofit.create(RetrofitAPI_USER.class);

        Call<List<User>> call = retrofitAPI_user.getAllUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(retrofit.Response<List<User>> response, Retrofit retrofit) {
                try {
                    if(databaseHelper.getAllUsers().size() != 0)
                        databaseHelper.deleteUserTable();
                    for(User user : response.body()){
                             databaseHelper.insertNewUser(user.getId(), user.getLastName(), user.getFirstName(),
                                user.getEmailAddress(), user.getPassword(), 1);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "blaa error onResponse");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void proba_retrofit(String email_address, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_USER retrofitAPI_user = retrofit.create(RetrofitAPI_USER.class);

        Call<User> call = retrofitAPI_user.login(email_address, password, "JSON");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {
                try {
                    String id_user = response.body().getId().toString();
                    String last_name = response.body().getLastName();
                    String first_name = response.body().getFirstName();
                    String email_address = response.body().getEmailAddress();
                    String password = response.body().getPassword();
                    String phone_number = response.body().getPhoneNumber();
                    //serverul returneaza un nou User daca datele cautate nu sunt valide.
                    //date invalide
                    if(last_name.equals("") && first_name.equals("")){
                        show_message("Adresa de email sau parola sunt invalide!");
                    }
                    //datele sunt valide =>  se realizeaza logarea
                    else {
                        Intent intent = new Intent(getApplicationContext(), Main_Train_Activity.class);
                        intent.putExtra("ip",ip);
                        intent.putExtra("url", url);
                        intent.putExtra("android_id", android_id);
                        intent.putExtra("id_user", id_user);
                        intent.putExtra("last_name", last_name);
                        intent.putExtra("first_name", first_name);
                        intent.putExtra("email_address", email_address);
                        intent.putExtra("password", password);
                        intent.putExtra("phone_number", phone_number);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "blaa error onResponse");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", "no internet" );
                List<User> getALl = databaseHelper.getAllUsers();
                for(User user : getALl)
                     Log.d("onFail",user.getFirstName() + " " + user.isStatus());
                verifyLocalData(email_address, password);
            }
        });
    }

    public void verifyLocalData(String email_address, String password){
        User user = databaseHelper.findUser(email_address, password);

        if(user.getFirstName() != null) {
            Log.d("userrrrrr ", user.getFirstName() + user.getFirstName());
            Intent intent = new Intent(getApplicationContext(), Main_Train_Activity.class);
            intent.putExtra("url", url);
            intent.putExtra("android_id", android_id);
            intent.putExtra("id_user", user.getId() + "");
            intent.putExtra("last_name", user.getLastName());
            intent.putExtra("first_name", user.getFirstName());
            intent.putExtra("email_address", email_address);
            intent.putExtra("password", password);
            intent.putExtra("phone_number", user.getPhoneNumber());
            startActivity(intent);
        }else{
            show_message("Adresa de email sau parola sunt invalide!");
        }
    }

    public void getAllStations(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_TRAIN retrofitAPI = retrofit.create(RetrofitAPI_TRAIN.class);

        Call<List<StationMapper1>> call = retrofitAPI.getAllStations();

        call.enqueue(new Callback<List<StationMapper1>>() {
            @Override
            public void onResponse(retrofit.Response<List<StationMapper1>> response, Retrofit retrofit) {
                try {
                    if(databaseHelper.getAllStations().size() != 0)
                        databaseHelper.deleteStationTable();

                    for(StationMapper1 stationMapper1 : response.body()){
                        databaseHelper.insertNewStation(stationMapper1.getId(),
                                stationMapper1.getName(), stationMapper1.getStation_number_in_route(),
                                stationMapper1.getArrival_time(), stationMapper1.getDeparture_time(),
                                stationMapper1.getId_route());
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "on error");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", "no internet" );
            }
        });

        Log.d("sizeeeee stations", databaseHelper.getAllStations().size() + "");
    }

    public void getAllRoutes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_TRAIN retrofitAPI = retrofit.create(RetrofitAPI_TRAIN.class);

        Call<List<RouteRequestForBD>> call = retrofitAPI.getAllRoutes();

        call.enqueue(new Callback<List<RouteRequestForBD>>() {
            @Override
            public void onResponse(retrofit.Response<List<RouteRequestForBD>> response, Retrofit retrofit) {
                try {
                    if(databaseHelper.getAllRoutes().size() != 0)
                        databaseHelper.deleteRouteTable();

                    for(RouteRequestForBD route : response.body()){
                        databaseHelper.insertNewRoute(route.getId(), route.getTime(), route.getTrain());
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "blaa error onResponse");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", "no internet" );
            }
        });

        Log.d("sizeeeee routes", databaseHelper.getAllRoutes().size() + "");
    }

    public void pushButtonLogin(View view) {
        String email_address = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if(email_address.equals("") || password.equals(""))
            show_message("Va rugam sa completati campurile corespunzatoare adresei de email si parolei!");
        else
            proba_retrofit(email_address, password);
    }

    @Override
    public void onClick(View v) {
        if(editTextEmail.getText().toString().equals("Email"))
            editTextEmail.setText("");

        if(editTextPassword.getText().toString().equals("Parolă"))
            editTextPassword.setText("");
    }
}
