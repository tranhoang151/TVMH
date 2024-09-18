package com.example.bai3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button setAlarmButton;
    private Button stopAlarmButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent(this, AlarmService.class);

        timePicker = findViewById(R.id.timePicker);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        stopAlarmButton = findViewById(R.id.stopAlarmButton);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });
    }

    private void setAlarm() {
        int hour, minute;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Báo thức đã được đặt cho " + hour + ":" + String.format("%02d", minute), Toast.LENGTH_SHORT).show();
    }

    private void stopAlarm() {
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        stopService(serviceIntent);
        Toast.makeText(this, "Báo thức đã được tắt", Toast.LENGTH_SHORT).show();
    }
}