package com.konka.livewallpaper;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class AnimatorService extends Service {
    public static final String TAG = "TEST";
    CountDownTask countDownTask = new CountDownTask(10000,1000);
    public AnimatorService() {
        Log.i(TAG, "create Service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        countDownTask.cancel();
        Bundle bundle = intent.getExtras();
        if(!bundle.getBoolean("ENABLE")) {
            Log.i(TAG, "switch off the sticker");
            stopSelf();
        }
        else {
            countDownTask.start();
            //stopSelf(startId);
        }
        return START_STICKY;
    }

    private class CountDownTask extends CountDownTimer {

        public CountDownTask(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Log.i(TAG, "fnish my task");
            stopSelf();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, "the remain time is " + millisUntilFinished / 1000);
        }
    }

    private class AnimatorTask implements Runnable {
        @Override
        public void run() {

        }
    }

}
