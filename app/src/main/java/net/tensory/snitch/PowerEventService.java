package net.tensory.snitch;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.HandlerThread;
import android.os.IBinder;

/**
 * Long-running service tracking power events.
 */
public class PowerEventService extends Service {
    HandlerThread mThread;
    BroadcastReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        mReceiver = new PowerEventReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        mThread.quit();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
