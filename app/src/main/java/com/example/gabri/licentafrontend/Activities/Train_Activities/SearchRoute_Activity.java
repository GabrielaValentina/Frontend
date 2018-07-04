package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gabri.licentafrontend.R;



/**
 * Created by gabri on 4/15/2018.
 */

public class SearchRoute_Activity extends AppCompatActivity {

    private String url;

    private EditText textDeparture;
    private EditText textArrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_route_activity);

        this.textDeparture = (EditText) findViewById(R.id.editTextRootSEARCHROUTE);
        this.textArrival = (EditText) findViewById(R.id.editTextDestinationSEARCHROUTE);

        this.url = getIntent().getExtras().getString("url");
    }

    public void buttonSearch(View view){
        if(textArrival.getText().toString().equals("") || textDeparture.getText().toString().equals(""))
            show_message("Vă rugăm să completați câmpurile");
        else{
            if(textArrival.getText().toString().equals(textDeparture.getText().toString())){
                show_message("Punctul de plecare și cel de sosire trebuie sa fie diferite");
            }
            else {
                Intent intent = new Intent(getApplicationContext(), Show_ListView_SearchedRoutes_Activity.class);
                intent.putExtra("url", url);
                intent.putExtra("departure", textDeparture.getText().toString());
                intent.putExtra("arrival", textArrival.getText().toString());
                startActivity(intent);
            }
        }
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
