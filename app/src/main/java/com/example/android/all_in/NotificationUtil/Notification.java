package com.example.android.all_in.NotificationUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.android.all_in.R;
import com.example.android.all_in.Screen2;

public class Notification {

    public static final String ACTION_CANCEL_NOTIFICATION =
            "com.example.android.all_in.NotificationUtil.ACTION_CLEAR_NOTIFICATION";
    public static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.all_in.NotificationUtil.ACTION_UPDATE_NOTIFICATION";
    private static final int PENDING_INTENT_REQUEST_CODE = 1001001;
    private static final CharSequence CHANNEL_NAME = "weather_update_channel";
    private static final int NOTIFICATION_ID = 1223334444;
    private static String notifyUser_notificationChannel_Id = "weather_update";

    private static PendingIntent contentIntent(Context context) {
        Intent intent = new Intent(context, Screen2.class);
        return PendingIntent.getActivity(context, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static void notifyUser(Context context) {

        // intent to pass for action put in Notification
        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        cancelIntent.putExtra("notificationID", NOTIFICATION_ID);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (context, 12121212, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        // intent to pass for action put in Notification
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (context, 12121212, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notifyUser_notificationChannel_Id, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = buildeNotificationBuilder(context);
        // add action in Notification
        builder.addAction(R.drawable.ic_directions, "cancel", cancelPendingIntent);
        builder.addAction(R.drawable.ic_directions, "update", updatePendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    public static void updatedNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notifyUser_notificationChannel_Id, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = buildeNotificationBuilder(context);
        builder.setStyle(new NotificationCompat.InboxStyle().addLine("This is added line")
                .addLine("This is also added line")
                .addLine("This is last added line"));
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private static NotificationCompat.Builder buildeNotificationBuilder(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notifyUser_notificationChannel_Id)
                .setAutoCancel(true)
                .setContentIntent(contentIntent(context))
                .setContentTitle("Weather Update")
                .setWhen(0)
                .setContentText("Toronto is now shivering in snow")
                .setSmallIcon(R.drawable.ic_directions);
        return builder;
    }

}
