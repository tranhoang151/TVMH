package com.example.bai3;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class AlarmService extends Service {

    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startVibration();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startVibration() {
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(new long[]{0, 1000, 1000}, 0));
            } else {
                vibrator.vibrate(new long[]{0, 1000, 1000}, 0);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (vibrator != null) {
            vibrator.cancel();
        }
        super.onDestroy();
    }
}