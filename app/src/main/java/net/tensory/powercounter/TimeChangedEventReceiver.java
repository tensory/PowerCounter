package net.tensory.powercounter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import net.tensory.powercounter.logging.EventNotifier;

/**
 * Clear all notifications fired by this app
 */
public class TimeChangedEventReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventNotifier.clearNotifications(context.getApplicationContext());
    }
}