package com.meliksah.huaweicodingmarathonproject.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationResult;
import com.meliksah.huaweicodingmarathonproject.model.Fence;
import com.meliksah.huaweicodingmarathonproject.viewmodel.FenceViewModel;

import java.util.List;

public class FenceItemService extends IntentService {
    FenceViewModel fenceViewModel;
    private Location location;


    public FenceItemService() {
        super("FenceItemService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
            fenceViewModel = new FenceViewModel(getApplication());
            Intent broadCastIntent = new Intent();
            Bundle b = intent.getExtras();
            String fenceName, fenceNote, fenceType;
            Double lat,lng;
            fenceName = b.getString("fenceName","NoName");
            fenceNote = b.getString("fenceNote","NoNote");
            fenceType = b.getString("fenceType","ENTER");
            lat = b.getDouble("lat",44.0);
            lng = b.getDouble("lng",44.0);

            Fence fence = new Fence(fenceName,lng,lat,50.0,fenceType,fenceNote);
            fenceViewModel.insertFence(fence);
            broadCastIntent.setAction("INSERT_COMPLETE");

            sendBroadcast(broadCastIntent);

    }


}
