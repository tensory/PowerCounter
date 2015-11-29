package net.tensory.powercounter.logging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import net.tensory.powercounter.MainActivity;
import net.tensory.powercounter.R;
import net.tensory.powercounter.logging.notification.LogSubscriber;

/**
 * Send a device notification when a log event occurs.
 */
public class EventNotifier implements LogSubscriber {
    private Context context;
    private static final int NOTIFY_ID = 1;


    public EventNotifier(Context context) {
        this.context = context;
    }

    @Override
    public void update(int value) {
        sendNotification(value);
    }

    private void sendNotification(int numberToDisplay) {

        Notification notification = getNotification(context, numberToDisplay);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }

    private Notification getNotification(Context context, int value) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent mainActivityAction = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String contentText = String.format(context.getString(R.string.txt_you_have_checked), value) + context.getResources().getQuantityString(R.plurals.times_today, value);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.app_name))
                .setContentText(contentText)
                .setContentIntent(mainActivityAction)
                .setPriority(Notification.PRIORITY_LOW)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(android.app.Notification.CATEGORY_MESSAGE);
        }

        return builder.build();
    }
//
//    protected static NotificationCompat.Builder getBaseNotification(Context context, Notification notification) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setContentTitle(notification.getTitle())
//                .setContentText(notification.getText())
//                .setContentIntent(notification.getPendingIntent())
//                .setSmallIcon(R.drawable.ic_notif_small_icon)
//                .setColor(context.getResources().getColor(R.color.bg_orange))
//                .setAutoCancel(true);
//        return builder;
//    }
}
