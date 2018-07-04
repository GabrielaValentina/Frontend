package com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Alarm_Activity_Bun;
import com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Stop_Alarm_Activity;
import com.example.gabri.licentafrontend.R;

public class RingtonePlayingService extends Service {
    MediaPlayer mMediaPlayer;
    private int startId;
    private boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), Stop_Alarm_Activity.class);  //Alarm_Activity_Bun
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("TainApp")
                .setContentText("Oprește alarma!")
                .setSmallIcon(R.drawable.train)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        String state = intent.getExtras().getString("state");

        assert state != null;
        switch (state) {
            case "unseted_alarm":
                startId = 0;
                break;
            case "seted_alarm":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.alarm);
            mMediaPlayer.start();
            mNM.notify(0, mNotify);
            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){
            this.isRunning = false;
            this.startId = 0;
        }

        else if (this.isRunning && startId == 1){
            mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            mMediaPlayer.start();
            this.isRunning = true;
            this.startId = 0;
        }
        else {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            this.isRunning = false;
            this.startId = 0;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}
