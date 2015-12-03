package net.tensory.snitch.logging;

import android.content.Context;

/**
 * Factory wrapper for EventsPerDayLogger.
 */
public class EventsPerDayLoggerFactory {
    private static EventsPerDayLogger instance;

    private EventsPerDayLoggerFactory() {}

    public static EventsPerDayLogger getInstance(Context context) {
        if (instance == null) {
            instance = new EventsPerDayLogger(context);
        }

        return instance;
    }
}
