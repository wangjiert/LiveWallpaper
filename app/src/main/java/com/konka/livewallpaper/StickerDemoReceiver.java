package com.konka.livewallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class StickerDemoReceiver extends BroadcastReceiver {
    public static final String TAG = "TEST";
    public StickerDemoReceiver() {
        Log.i(TAG, "create broadcastreceive");
    }
    @Override
    public void finalize(){
        Log.i(TAG, "delete receive");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(intent.getAction().equals("com.konka.STICKERDEMOBROADCAST")) {
            Bundle bundle = intent.getExtras();
            boolean isEnable = bundle.getBoolean("ENABLE");
            Intent intent1 = new Intent(context,AnimatorService.class);
            intent1.putExtra("ENABLE", isEnable);
            context.startService(intent1);
        }
        else if(getStickerDemoStatus(context)) {
            Intent intent1 = new Intent(context,AnimatorService.class);
            intent1.putExtra("ENABLE", true);
            context.startService(intent1);
        }
    }

    private boolean getStickerDemoStatus(Context context) {
        boolean flag = false;
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://mstar.tv.usersetting/systemsetting/"),
                null, null, null, null);
        if (cursor.moveToFirst()) {
            flag = cursor.getInt(cursor.getColumnIndex("PopStickerSwitch")) == 1 ? true
                    : false;
        }
        cursor.close();
        return flag;
    }

}
