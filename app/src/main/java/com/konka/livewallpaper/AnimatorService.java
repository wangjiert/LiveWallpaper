package com.konka.livewallpaper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AnimatorService extends Service {
    public AnimatorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
