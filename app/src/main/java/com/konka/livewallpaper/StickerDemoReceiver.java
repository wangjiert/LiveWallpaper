package com.konka.livewallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StickerDemoReceiver extends BroadcastReceiver {
    public static final String TAG = "TEST";
    public StickerDemoReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.i(TAG, "boot complete");
        }
        else {
            Bundle bundle = intent.getExtras();
            Log.i(TAG, bundle.getBoolean("ENABLE") + "");
        }
    }
}
