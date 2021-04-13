package com.gabriel.taxifee;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.gabriel.taxifee.model.DriverInfoModel;

public class _common {
    public static final String DRIVER_INFO_REFERENCE = "DriverInfo";
    public static final String DRIVER_LOCATION_REFERENCES = "DriverLocation";
    public static final String TOKEN_REFERENCE = "Token";
    public static final String NOTI_TITLE = "title";
    public static final String NOTI_CONTENT = "body";

    public static DriverInfoModel currentUser;

    public static String buildWelcomeMessage() {
        if (_common.currentUser != null) {
            return new StringBuilder("Welcome ")
                    .append(_common.currentUser.getFirstName())
                    .append(" ")
                    .append(_common.currentUser.getLastName()).toString();
        } else {
            return "";
        }
    }

    public static void showNotification(Context context, int id, String title, String body, Intent intent) {
        PendingIntent pendingIntent =  null;
        if (intent != null) {
            pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            String NOTIFICATION_CHANNEL_ID = "taxi_fee";
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            
        }
    }
}
