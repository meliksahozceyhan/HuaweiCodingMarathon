package com.meliksah.huaweicodingmarathonproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huawei.hms.location.Geofence;
import com.huawei.hms.location.GeofenceService;
import com.huawei.hms.location.LocationServices;
import com.meliksah.huaweicodingmarathonproject.model.Fence;
import com.meliksah.huaweicodingmarathonproject.viewadapters.MainRecyclerViewAdapter;
import com.meliksah.huaweicodingmarathonproject.viewmodel.FenceViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mainRecyclerView;
    FloatingActionButton floatingActionButton;
    FenceViewModel fenceViewModel ;
    private GeofenceService geofenceService;
    private ArrayList<String> idList;
    private ArrayList<Geofence> geofenceList;
    private List<Fence> fenceList;
    private PendingIntent pendingIntent;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WindowInsetsController insetsController = getWindow().getInsetsController();
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.navigationBars());
            insetsController.hide(WindowInsets.Type.captionBar());
        }

        initVariables();

    }

    public void initVariables(){
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        geofenceService = LocationServices.getGeofenceService(this);
        pendingIntent = getPendingIntent();
        idList = new ArrayList<String>();
        geofenceList = new ArrayList<Geofence>();
        fenceViewModel = new FenceViewModel(getApplication());
        initRecyclerView();
        TAG = "geoFence";
        initGeofences();
    }

    public void initGeofences(){
        geofenceList.add(new Geofence.Builder().setUniqueId("mGeoFence").setValidContinueTime(0).setRoundArea(42,42,42).
                setConversions(Geofence.ENTER_GEOFENCE_CONVERSION | Geofence.EXIT_GEOFENCE_CONVERSION).build());
    }

    public void initRecyclerView(){
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this,fenceViewModel.gelAllFences());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(mainRecyclerViewAdapter);
    }

    public PendingIntent getPendingIntent(){
        return null;
    }
}