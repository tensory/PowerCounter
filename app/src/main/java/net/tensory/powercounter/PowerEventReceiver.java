package net.tensory.powercounter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import net.tensory.powercounter.logging.EventsPerDayLogger;
import net.tensory.powercounter.logging.LogViewer;
import net.tensory.powercounter.logging.Logger;

import java.util.Date;

/**
* Register this broadcast listener in AndroidManifest.
*/
public class PowerEventReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger logger = new EventsPerDayLogger(context);

        logger.subscribe(LogViewer.getInstance());
        logger.logEvent(new Date());
    }
}
