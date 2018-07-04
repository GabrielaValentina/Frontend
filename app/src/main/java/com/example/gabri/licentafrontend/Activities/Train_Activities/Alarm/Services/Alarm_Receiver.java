package com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Services;

/**
 * Created by gabri on 5/5/2018.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Alarm_Receiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("state");

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("state", state);

        context.startService(serviceIntent);
    }
}
