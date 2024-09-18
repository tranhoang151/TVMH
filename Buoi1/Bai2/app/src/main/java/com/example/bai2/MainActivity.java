package com.example.bai2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2, textView3;
    private Button button1, button2, button3;
    private CustomThread thread1, thread2, thread3;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupHandler();
        startThreads();
    }

    private void initializeViews() {
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button1.setOnClickListener(v -> toggleThread(thread1));
        button2.setOnClickListener(v -> toggleThread(thread2));
        button3.setOnClickListener(v -> toggleThread(thread3));
    }

    private void setupHandler() {
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        textView1.setText(String.valueOf(msg.arg1));
                        break;
                    case 2:
                        textView2.setText(String.valueOf(msg.arg1));
                        break;
                    case 3:
                        textView3.setText(String.valueOf(msg.arg1));
                        break;
                }
            }
        };
    }

    private void startThreads() {
        thread1 = new CustomThread(handler, 1, 1000, true);
        thread2 = new CustomThread(handler, 2, 2500, false);
        thread3 = new CustomThread(handler, 3, 2000, false);

        new Handler().postDelayed(() -> {
            thread1.start();
            thread2.start();
            thread3.start();
        }, 2000);
    }

    private void toggleThread(CustomThread thread) {
        if (thread.isRunning()) {
            thread.pauseThread();
        } else {
            thread.resumeThread();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread1.stopThread();
        thread2.stopThread();
        thread3.stopThread();
    }
}