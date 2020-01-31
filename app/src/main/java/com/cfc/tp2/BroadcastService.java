package com.cfc.tp2;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class BroadcastService extends Service {

    private GlobalData data;

    public static final String COUNTDOWN_BR = "com.cfc.tp2.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    CountDownTimer cdt = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // Globaldata class access
        data = GlobalData.getInstance();

        // Set timer who decrease every 1000ms (1s) for 1,3,5 or 10 minutes as defined in data class
        cdt = new CountDownTimer(data.getTimeValue()*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // send curent timer value
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
                // if the timer is finished
                data.setTimerFinished(true);
                bi.putExtra("countdown", 0);
                sendBroadcast(bi);

            }
        };

        // timer start
        cdt.start();
    }

    @Override
    public void onDestroy() {
        // Cancel the timer
        cdt.cancel();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}