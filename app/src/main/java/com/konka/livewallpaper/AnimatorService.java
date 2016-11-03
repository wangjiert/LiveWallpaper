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
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashSet;

public class AnimatorService extends Service {
    public static final String TAG = "TEST";
    CountDownTask countDownTask = new CountDownTask(3000,1000);
    FrameLayout frameLayout = null;
    AnimatorManager animatorManager;
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
        if(frameLayout != null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeViewImmediate(frameLayout);
            frameLayout = null;
            animatorManager = null;
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

    private class CountDownTask extends CountDownTimer {

        public CountDownTask(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            layoutParams.format = PixelFormat.TRANSLUCENT;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_FULLSCREEN;
            frameLayout = new FrameLayout(AnimatorService.this);
            animatorManager = new AnimatorManager(AnimatorService.this,frameLayout);
            windowManager.addView(frameLayout,layoutParams);
            Log.i(TAG, "fnish my task");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, "the animator will start after " + millisUntilFinished / 1000 + "s");
        }
    }
}
