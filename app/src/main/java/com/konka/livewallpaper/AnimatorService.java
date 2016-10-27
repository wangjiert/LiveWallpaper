package com.konka.livewallpaper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;

public class AnimatorService extends Service {
    public static final String TAG = "TEST";
    WindowManager windowManager ;
    CountDownTask countDownTask = new CountDownTask(10000,1000);
    HashSet<View> hashSet = new HashSet<>();
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
        if(hashSet.size() > 0){
            windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            for(View button : hashSet) {
                windowManager.removeViewImmediate(button);
            }
            windowManager = null;
            hashSet.clear();
            Log.i(TAG, "hashset size is " + hashSet.size());
        }
        if(intent.getExtras() == null) {
            countDownTask.start();
        }
        else {
            Log.i(TAG, "switch off the sticker");
            stopSelf();
        }
        return START_STICKY;
    }
    @Override
    public void finalize(){
        Log.i(TAG, "delete service");
    }

    private class CountDownTask extends CountDownTimer {

        public CountDownTask(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            layoutParams.format = PixelFormat.TRANSLUCENT;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            layoutParams.width = 500;
            layoutParams.height = 500;
            Button button = new Button(AnimatorService.this);
            hashSet.add(button);
            windowManager.addView(button,layoutParams);
            windowManager = null;
            Log.i(TAG, "fnish my task");
            //stopSelf();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, "the remain time is " + millisUntilFinished / 1000);
        }
    }
}
