package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gabri.licentafrontend.Database.DatabaseHelper;
import com.example.gabri.licentafrontend.Domain.User;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_TRAIN;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_USER;

import java.util.ArrayList;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 4/18/2018.
 */

public class MyTrain_Search extends AppCompatActivity {

    private String id_user;
    private String url;
    private String last_name_USER;
    private String first_name_USER;
    private String email_address_USER;
    private String password_USER;
    private String phone_number_USER;

    private EditText editTextNumberOfTrain;
    private ImageButton buttonSpeak;
    private Button buttonSearch;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private String train_number;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_my_train_activity);

        this.databaseHelper = new DatabaseHelper(this);

        id_user = getIntent().getExtras().getString("user_id");
        url = getIntent().getExtras().getString("url");
        last_name_USER = getIntent().getExtras().getString("last_name");
        first_name_USER = getIntent().getExtras().getString("first_name");
        email_address_USER = getIntent().getExtras().getString("email_address");
        password_USER = getIntent().getExtras().getString("password");
        phone_number_USER = getIntent().getExtras().getString("phone_number");

        editTextNumberOfTrain = (EditText) findViewById(R.id.editTextNumberOfTrainSEARCHMYTRAINACTIVITY);
        buttonSpeak = (ImageButton) findViewById(R.id.imageButtonSearchTrainSEARCHMYTRAINACTIVITY);
        buttonSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        buttonSearch = (Button) findViewById(R.id.buttonSearchMyTrainMYTRAINACTIVITY);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                train_number = editTextNumberOfTrain.getText().toString();

                if(train_number.equals("")){
                    show_message("Date invalide!", "Introduceti numarul trenului!");
                }
                else {
                   trainNumber_verification(train_number);
                }
            }
        });
    }

    public void trainNumber_verification(final String train_number){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_TRAIN retrofitAPI_train = retrofit.create(RetrofitAPI_TRAIN.class);

        Call<String> call = retrofitAPI_train.findATrainById(train_number, "STRING");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit.Response<String> response, Retrofit retrofit) {
                try {
                    Log.d("route = ", response.body());
                    if(!response.body().equals("nu")){
                        Intent intent = new Intent(getApplicationContext(), MyTrainActivity.class);
                        intent.putExtra("user_id", id_user);
                        intent.putExtra("url", url);
                        intent.putExtra("train_number",train_number);
                        intent.putExtra("route_number", response.body());
                        intent.putExtra("last_name", last_name_USER);
                        intent.putExtra("first_name", first_name_USER);
                        intent.putExtra("email_address", email_address_USER);
                        intent.putExtra("password", password_USER);
                        intent.putExtra("phone_number", phone_number_USER);
                        startActivity(intent);
                    }
                    else{
                        show_message("Date incorecte!", "Numarul trenului introdus este invalid!");
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "error onResponse");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", "no internet");
                verifyLocalData(train_number);
            }
        });
    }

    private void verifyLocalData(String train) {
        String route = databaseHelper.findRouteByTrainId(train);
        if(!route.equals("no")){
            Intent intent = new Intent(getApplicationContext(), MyTrainActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("user_id", id_user);
            intent.putExtra("train_number",train_number);
            intent.putExtra("route_number", route);
            intent.putExtra("last_name", last_name_USER);
            intent.putExtra("first_name", first_name_USER);
            intent.putExtra("email_address", email_address_USER);
            intent.putExtra("password", password_USER);
            intent.putExtra("phone_number", phone_number_USER);
            startActivity(intent);
        }
        else{
            show_message("Date incorecte!", "Numarul trenului introdus este invalid!");
        }
    }

    private void promptSpeechInput() {

        /**
        *ACTION_RECOGNIZE_SPEECH = Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        * */
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Rostiti numarul trenului");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Nu suporta",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editTextNumberOfTrain.setText(result.get(0));
                    train_number = result.get(0);
                    Log.d("speech input text -> ", result.get(0));
                }
                break;
            }

        }
    }

    public void show_message(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);

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
