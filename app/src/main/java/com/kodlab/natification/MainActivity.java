package com.kodlab.natification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button Set,Cancel;
EditText Box;
TimePicker timePicker;

 private int notificationId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Box = findViewById(R.id.editBox);
        timePicker = findViewById(R.id.timePicker);

        //Set On Click Listener
        Set = findViewById(R.id.setButton);
            Set.setOnClickListener(this);
        Cancel = findViewById(R.id.cancelButton);
            Cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    //Set Notification & Message
        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.putExtra("notificationId",notificationId);
        intent.putExtra("message",Box.getText().toString());

        //PandingIntent
        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        //AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch (v.getId()) {
            case R.id.setButton:
                //Set Alarm
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                //Create Time
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE,minute);
                startTime.set(Calendar.SECOND,0);
                long alarmStartTime = startTime.getTimeInMillis();

                //Set Alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime,alarmIntent);
                Toast.makeText(getApplicationContext(),"NOTIFICATION DONE!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cancelButton:
                //Cancel Alarm
                alarmManager.cancel(alarmIntent);
                Toast.makeText(getApplicationContext(),"NOTIFICATION CANCELED", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}