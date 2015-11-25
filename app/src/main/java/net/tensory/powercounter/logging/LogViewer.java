package net.tensory.powercounter.logging;

import net.tensory.powercounter.logging.notification.LogSubscriber;

/**
 * Listener to changes in the log, offering access to the current logged value.
 */
public class LogViewer implements LogSubscriber {
    private int count;
    private static LogViewer instance;

    private LogViewer() {}

    public static LogViewer getInstance() {
        if (instance == null) {
            instance = new LogViewer();
        }

        return instance;
    }

    public int getCount() {
        return instance.count;
    }

    private void setEventCount(int newCount) {
        instance.count = newCount;
    }

    @Override
    public void update(int value) {
        instance.setEventCount(value);
    }
}
