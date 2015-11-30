package net.tensory.powercounter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import net.tensory.powercounter.logging.EventsPerDayLoggerFactory;
import net.tensory.powercounter.logging.Logger;
import net.tensory.powercounter.logging.EventNotifier;

import java.util.Date;

/**
* Register this broadcast listener in AndroidManifest.
*/
public class PowerEventReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger logger = EventsPerDayLoggerFactory.getInstance(context.getApplicationContext());

        logger.subscribe(LogViewer.getInstance());
        logger.subscribe(new EventNotifier(context));

        logger.logEvent(new Date());
    }
}
