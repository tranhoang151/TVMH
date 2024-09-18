package com.example.bai2;

import android.os.Handler;
import android.os.Message;

import java.util.Random;

public class CustomThread extends Thread {
    private final Handler handler;
    private final int threadId;
    private final int delay;
    private final boolean isRandom;
    private boolean isRunning = true;
    private boolean isPaused = false;
    private int currentValue = 0;

    public CustomThread(Handler handler, int threadId, int delay, boolean isRandom) {
        this.handler = handler;
        this.threadId = threadId;
        this.delay = delay;
        this.isRandom = isRandom;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!isPaused) {
                int value;
                if (isRandom) {
                    Random random = new Random();
                    value = random.nextInt(51) + 50;
                } else if (threadId == 2) {
                    value = currentValue * 2 + 1;
                } else {
                    value = currentValue;
                }
                currentValue++;

                Message msg = handler.obtainMessage(threadId, value, 0);
                handler.sendMessage(msg);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pauseThread() {
        isPaused = true;
    }

    public void resumeThread() {
        isPaused = false;
    }

    public void stopThread() {
        isRunning = false;
    }

    public boolean isRunning() {
        return !isPaused;
    }
}