package com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.gabri.licentafrontend.Activities.Train_Activities.Alarm.Services.Alarm_Receiver;
import com.example.gabri.licentafrontend.Activities.Train_Activities.MyTrainActivity;
import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Repository.LocalRepository;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_TRAIN;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 5/9/2018.
 */

public class Alarm_Activity_Bun extends AppCompatActivity {

    Spinner spinner_stations;
    RadioButton radioButton_alarm_parsonalized;
    RadioButton radioButton_alarm_implicit;
    TimePicker timePicker;
    Button button_start_alarm;
    Button button_stop_alarm;

    AlarmManager alarmManager;
    Calendar calendar;

    Boolean isImplicitAlarm;
    Boolean isPersonalizedAlarm;

    ArrayList<TrainStationAdapter> stations;
    String url;

    PendingIntent pendingIntent;
    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_bun);

        this.context = this;

        this.isImplicitAlarm = false;
        this.isPersonalizedAlarm = false;

        this.spinner_stations = (Spinner) findViewById(R.id.spinnerStations);
        this.radioButton_alarm_implicit = (RadioButton) findViewById(R.id.radioButtonIMPLICITALARM);
        this.radioButton_alarm_parsonalized = (RadioButton) findViewById(R.id.radioButtonPERSONALIZEDALARM);
        this.timePicker = (TimePicker) findViewById(R.id.timePickerAlarm);
        this.button_start_alarm = (Button) findViewById(R.id.buttonSaveALARACTIVITY);
        this.button_stop_alarm = (Button) findViewById(R.id.buttonStopALARACTIVITY);

       // url = getIntent().getExtras().getString("url");
        stations = (ArrayList<TrainStationAdapter>) getIntent().getExtras().getSerializable("stations");

        intent = new Intent(this.context, Alarm_Receiver.class);
        //initialize alarm manager
        this.alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize the instance of Calendar
        this.calendar = Calendar.getInstance();

        this.button_start_alarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isImplicitAlarm == true){
                    Calendar ten_minutes_calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(00));
                    calendar.set(Calendar.MINUTE, Integer.valueOf(10));
                    Log.d("SCADERE calendar min " + calendar.get(Calendar.MINUTE),
                            "ora "+ calendar.get(Calendar.HOUR_OF_DAY));
                    Log.d("isImplicitAlarm","isImplicitAlarm");
                    String station_selected = spinner_stations.getSelectedItem().toString();
                    //take the hour and minutes from current element from spinner
                    final String hour = station_selected.substring(station_selected.indexOf("(") + 1, station_selected.indexOf(":"));
                    final String minute = station_selected.substring(station_selected.indexOf(":") + 1, station_selected.indexOf(")"));

                    //set the calendar time
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
                    calendar.set(Calendar.MINUTE, Integer.valueOf(minute));
                    Log.d("INAINTE calendar min " + calendar.get(Calendar.MINUTE),
                            "ora "+ calendar.get(Calendar.HOUR_OF_DAY));
                    calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) -10);
                      //calendar.add(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 10);
                   // calendar = calendar - ten_minutes_calendar;
                    Log.d("DUPA calendar min " + calendar.get(Calendar.MINUTE),
                            "ora "+ calendar.get(Calendar.HOUR_OF_DAY));

                    if (Integer.valueOf(minute) > 9)
                        // textView_set_alarm_clock.setText("Alarma e setata la " + hour + ":" + minute);
                        show_message("Alarma e setata la " + hour + ":" + minute);
                    else
                        show_message("Alarma e setata la " + hour + ":" + "0" + minute);

                    Log.d("call_alarm",calendar.getTime().getHours()+ ":" + calendar.getTime().getMinutes() + " -> "+ calendar.getTimeInMillis());
                    intent.putExtra("state", "seted_alarm");
                    pendingIntent = PendingIntent.getBroadcast(Alarm_Activity_Bun.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                else {
                    if (isPersonalizedAlarm == true) {
                        Log.d("isImplicitAlarm","perso");
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();

                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                        if (minute > 9)
                           // textView_set_alarm_clock.setText("Alarma e setata la " + hour + ":" + minute);
                            show_message("Alarma e setata la " + hour + ":" + minute);
                        else
                            show_message("Alarma e setata la " + hour + ":" + "0" + minute);
                            //textView_set_alarm_clock.setText("Alarma e setata la " + hour + ":" + "0" + minute);

                        Log.d("call_alarm",calendar.getTime().getHours()+ ":" + calendar.getTime().getMinutes());
                        intent.putExtra("state", "seted_alarm");
                        pendingIntent = PendingIntent.getBroadcast(Alarm_Activity_Bun.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });

        this.button_stop_alarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                isImplicitAlarm = false;
                isPersonalizedAlarm = false;
                stop_alarm();
            }
        });

        populate_spinner();
        try {
             Thread.sleep(1000);
          }catch (InterruptedException ex){
        }
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Alarma");

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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonIMPLICITALARM:
                if (checked)
                    this.isImplicitAlarm = true;
                break;
            case R.id.radioButtonPERSONALIZEDALARM:
                if (checked)
                    this.isPersonalizedAlarm = true;
                break;
        }


    }

    public void stop_alarm(){
        intent.putExtra("state", "unseted_alarm");
        pendingIntent = PendingIntent.getBroadcast(Alarm_Activity_Bun.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.sendBroadcast(intent);
        alarmManager.cancel(pendingIntent);
    }

    public void populate_spinner(){
        List<String> spinnerArray =  new ArrayList<String>();
        String text;
        for(TrainStationAdapter station : stations){
            if(!station.getArrival_time().equals("-")){
              text = station.getStation_name() + " (" + station.getArrival_time()+")";
                spinnerArray.add(text);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        spinner_stations.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public ArrayList<TrainStationAdapter> getStations() {
         return stations;
    }
}
