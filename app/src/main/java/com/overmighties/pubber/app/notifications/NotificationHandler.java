package com.overmighties.pubber.app.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.MainActivity;

public class NotificationHandler {

    public static final String TAG = "NotificationHandler";
    public static final String CHANNEL_GENERAL_GROUP_ID = "Pubber_general_group";
    public static final String CHANNEL_ID_REMIND = "Pubber_remind";
    public static final String CHANNEL_ID_RATE_GROUP = "Pubber_rate_group";

    public static final String CHANNEL_ID_RATE = "Pubber_rate";


    public static void createNotificationRemind(Context context){

        Integer id = 0;

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID_REMIND)
                .setSmallIcon(R.drawable.pub_icon_256)
                .setContentTitle(context.getString(R.string.notification_from_app_remind_title))
                .setContentText(context.getString(R.string.notification_from_app_remind_description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(id, notification);

    }
    public static void createNotificationChannels(Context context){
        NotificationManager notificationManagerGroup =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannelGroup generalGroup = new NotificationChannelGroup(CHANNEL_GENERAL_GROUP_ID, context.getString(R.string.general_group));
        notificationManagerGroup.createNotificationChannelGroup(generalGroup);

        NotificationChannel channelRate = new NotificationChannel(CHANNEL_GENERAL_GROUP_ID, context.getString(R.string.notifications_rating), NotificationManager.IMPORTANCE_DEFAULT);
        channelRate.setGroup(CHANNEL_GENERAL_GROUP_ID);

        NotificationChannel channelRemind = new NotificationChannel(CHANNEL_ID_REMIND, context.getString(R.string.notifications_from_app), NotificationManager.IMPORTANCE_DEFAULT);
        channelRemind.setGroup(CHANNEL_GENERAL_GROUP_ID);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channelRemind);
        notificationManager.createNotificationChannel(channelRate);
            
        Log.i(TAG, "chanels created");

    }

}
