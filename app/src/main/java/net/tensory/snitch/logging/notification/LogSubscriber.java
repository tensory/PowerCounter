package net.tensory.snitch.logging.notification;

/**
 * Define an interface for subscribers to changes in the log.
 */
public interface LogSubscriber {
    public void update(int value);
}
