package net.tensory.powercounter.logging;

import net.tensory.powercounter.logging.notification.LogSubscriber;

import java.util.Date;

/**
 * // TODO: Add class doc comment
 */
public interface Logger {
    public void subscribe(LogSubscriber subscriber);

    public void logEvent(Date eventTime);
}
