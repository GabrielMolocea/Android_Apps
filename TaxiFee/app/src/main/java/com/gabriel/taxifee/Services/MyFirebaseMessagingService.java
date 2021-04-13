package com.gabriel.taxifee.Services;

import androidx.annotation.NonNull;

import com.gabriel.taxifee.Utils.UserUtils;
import com.gabriel.taxifee._common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UserUtils.updateToken(this, s);
        }

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> dataRecv =  remoteMessage.getData();
        if (dataRecv != null) {
            _common.showNotification(this,
                    new Random().nextInt(),
                    dataRecv.get(_common.NOTI_TITLE),
                    dataRecv.get(_common.NOTI_CONTENT),
                    null);
        }
    }

}
