package jp.co.devhogata.alertinfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 通知処理
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setTicker(intent.getStringExtra("MESSAGE"));
        builder.setContentTitle(intent.getStringExtra("MESSAGE"));
        builder.setContentText("簡単通知からの通知メッセージです");
        builder.setContentInfo("通知情報");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

        // Log
        Log.i("NotificationReceiver", "onReceive end");

    }

}