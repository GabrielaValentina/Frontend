package com.example.gabri.licentafrontend.Activities.User_Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.gabri.licentafrontend.Domain.Mappers.UserMapper;
import com.example.gabri.licentafrontend.Domain.User;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_USER;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 3/26/2018.
 */

public class RegistrationConfirmation_Activity extends AppCompatActivity {
    private String ip;
    private String url;

    private String email;

    private EditText editTextCode;
    private EditText editTextEmail;
    private Button confirmationButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_confirmation_activity);

        ip = getIntent().getExtras().getString("arg_ip");
        url = "http://"+ ip +":8080";
        url = "http://192.168.100.6:8080/";
        email = getIntent().getExtras().getString("email");

        this.editTextCode = (EditText) findViewById(R.id.editTextCodeCONFIRMATION);
        this.editTextEmail = (EditText) findViewById(R.id.editTextEmailREGISTRATIONACTIVITY);
        this.editTextEmail.setText(email);
        this.confirmationButton = (Button) findViewById(R.id.buttonCONFIRMATION);
        this.backButton = (Button) findViewById(R.id.buttonBackRegistrationConfirmation);
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Date incorecte!");

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

    public void buttonBackToLogin(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void buttonConfirmationAndRegistration(View view){
        if(editTextEmail.getText().toString().equals("") || editTextCode.getText().toString().equals(""))
            show_message("Vă rugăm să completați toate câmpurile!");
        else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitAPI_USER retrofitAPI_user = retrofit.create(RetrofitAPI_USER.class);

            Call<String> call = retrofitAPI_user.verificationCode(editTextEmail.getText().toString(), editTextCode.getText().toString(), "STRING");

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(retrofit.Response<String> response, Retrofit retrofit) {
                    try {
                      //  Log.d("mess ", response.body());
                        String a = response.body() + "";
                        if(a.equals("false")){
                            show_message("Codul nu este cel corespunzător");
                        }
                        else {
                            show_message("Înregistrarea a avut loc cu succes!");
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
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
    }

}
