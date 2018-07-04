package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.gabri.licentafrontend.Activities.Chat_Activities.Chat_Activity;
import com.example.gabri.licentafrontend.Activities.Chat_Activities.UsersList_Chat_Activity;
import com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Alarm_Activity_Bun;
import com.example.gabri.licentafrontend.Activities.User_Activities.RegisterActivity;
import com.example.gabri.licentafrontend.R;

/**
 * Created by gabri on 4/15/2018.
 */

public class Main_Train_Activity extends Activity {

    private String ip;
    private String url;
    private String last_name_USER;
    private String first_name_USER;
    private String email_address_USER;
    private String password_USER;
    private String phone_number_USER;
    private String id_USER;
    private String device_id;

    private ImageButton buttonSearchRoutes;
    private ImageButton buttonMyTrain;
    private ImageButton buttonReviews;
    private ImageButton buttonLandmarks;
    private ImageButton buttonChat;
    private ImageButton buttonRecommendedRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ip = getIntent().getExtras().getString("ip");
        url = getIntent().getExtras().getString("url");
        device_id = getIntent().getExtras().getString("android_id");
        id_USER = getIntent().getExtras().getString("id_user");
        last_name_USER = getIntent().getExtras().getString("last_name");
        first_name_USER = getIntent().getExtras().getString("first_name");
        email_address_USER = getIntent().getExtras().getString("email_address");
        password_USER = getIntent().getExtras().getString("password");
        phone_number_USER = getIntent().getExtras().getString("phone_number");

        this.buttonMyTrain =  findViewById(R.id.buttonMyTrainMAINACTIVITY);
        this.buttonSearchRoutes = findViewById(R.id.buttonSearchRouteMAINACTIVITY);
        this.buttonReviews =  findViewById(R.id.buttonReviewMAINACTIVITY);
        this.buttonLandmarks =  findViewById(R.id.buttonLAndmarkMAINACTIVITY);
        this.buttonChat = findViewById(R.id.buttonOpenChatActivityMAINTRAINACTIVITY);
        this.buttonRecommendedRoutes =  findViewById(R.id.buttonRecommendedRoutesMAINACTIVITY);

        buttonRecommendedRoutes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), RecommendedRoutes_Activity.class);
                intent.putExtra("url", url);
                intent.putExtra("id_user", id_USER);
                startActivity(intent);
            }
        });

        buttonMyTrain.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MyTrain_Search.class);
                intent.putExtra("user_id", id_USER);
                intent.putExtra("url", url);
                intent.putExtra("last_name", last_name_USER);
                intent.putExtra("first_name", first_name_USER);
                intent.putExtra("email_address", email_address_USER);
                intent.putExtra("password", password_USER);
                intent.putExtra("phone_number", phone_number_USER);
                startActivity(intent);
            }
        });

        buttonChat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), Chat_Activity.class);
                intent.putExtra("ip", ip);
                intent.putExtra("url", url);
                intent.putExtra("id_user", id_USER);
                intent.putExtra("name", last_name_USER + " " + first_name_USER);
                startActivity(intent);
            }
        });

        buttonSearchRoutes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),SearchRoute_Activity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        buttonReviews.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),ShowReviews_Activity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        buttonLandmarks.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Landmarks_Activity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }
}
