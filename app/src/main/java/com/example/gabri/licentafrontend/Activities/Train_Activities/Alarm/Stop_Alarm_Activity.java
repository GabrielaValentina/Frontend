package com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Services.Alarm_Receiver;
import com.example.gabri.licentafrontend.R;

/**
 * Created by gabri on 7/3/2018.
 */

public class Stop_Alarm_Activity  extends AppCompatActivity {

    PendingIntent pendingIntent;
    Intent intent;
    Context context;
    AlarmManager alarmManager;
    Button buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_alarm_activity);
        this.context = this;
        intent = new Intent(this.context, Alarm_Receiver.class);
        this.alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        this.buttonStop = (Button) findViewById(R.id.buttonSTOPALARM);
    }

    public void stop_alarm(View view){
        intent.putExtra("state", "unseted_alarm");
        pendingIntent = PendingIntent.getBroadcast(Stop_Alarm_Activity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.sendBroadcast(intent);
        alarmManager.cancel(pendingIntent);
    }

}
