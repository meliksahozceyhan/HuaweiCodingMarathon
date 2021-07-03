package com.meliksah.huaweicodingmarathonproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.huawei.hms.location.Geofence;
import com.huawei.hms.location.GeofenceData;
import com.meliksah.huaweicodingmarathonproject.R;

import java.util.ArrayList;

public class GeoFenceBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_PROCESS_LOCATION = "com.huawei.hmssample.geofence.GeoFenceBroadcastReceiver.ACTION_PROCESS_LOCATION";
    private static final String TAG = "GeoFenceReceiver";
    private static final String CHANNEL_ID = "GEOFENCE";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_LOCATION.equals(action)) {
                GeofenceData geofenceData = GeofenceData.getDataFromIntent(intent);
                if (geofenceData != null) {
                    boolean status = geofenceData.isSuccess();
                    if(status){
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                                .setContentTitle("You have a Notification")
                                .setContentText("You Have a Notification In this area")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(10,builder.build());

                    }
                }
            }
        }
    }
}
