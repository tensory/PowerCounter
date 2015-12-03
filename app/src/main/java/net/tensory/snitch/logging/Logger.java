package net.tensory.snitch.logging;

import net.tensory.snitch.logging.notification.LogSubscriber;

import java.util.Date;

/**
 * Interface for Date-based loggers
 */
public interface Logger<T> {
    public void subscribe(LogSubscriber subscriber);

    public void logEvent(Date eventTime);

    public T getLastLoggedValue();
}
