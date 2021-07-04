package com.meliksah.huaweicodingmarathonproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.Geofence;
import com.huawei.hms.location.GeofenceRequest;
import com.huawei.hms.location.GeofenceService;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.SettingsClient;
import com.meliksah.huaweicodingmarathonproject.R;
import com.meliksah.huaweicodingmarathonproject.model.Fence;
import com.meliksah.huaweicodingmarathonproject.receiver.GeoFenceBroadcastReceiver;
import com.meliksah.huaweicodingmarathonproject.service.FenceItemService;
import com.meliksah.huaweicodingmarathonproject.viewadapters.MainRecyclerViewAdapter;
import com.meliksah.huaweicodingmarathonproject.viewmodel.FenceViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mainRecyclerView;
    FloatingActionButton floatingActionButton;
    FenceViewModel fenceViewModel;
    private GeofenceService geofenceService;
    private ArrayList<String> idList;
    private ArrayList<Geofence> geofenceList;
    private List<Fence> fenceList;
    private PendingIntent pendingIntent;
    private String TAG;
    Dialog newFenceDialog, nameAndNoteDialog;
    SettingsClient settingsClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i(TAG, "android sdk < 28 Q");
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION"};
                ActivityCompat.requestPermissions(this, strings, 2);
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult != null){
                    List<Location> locations = locationResult.getLocations();
                    if(!locations.isEmpty()){
                        location = locationResult.getLastLocation();
                    }
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);

                if (locationAvailability != null) {
                    boolean flag = locationAvailability.isLocationAvailable();
                    Log.d("TAG", "isLocationAvailable: " + flag);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("INSERT_COMPLETE");
        registerReceiver(serviceBroadcastReceiver,intentFilter);

        initVariables();
        setObservable();
        initRecyclerView();

    }
    private void setObservable(){
        fenceViewModel.getFences().observe(this, new Observer<List<Fence>>() {
            @Override
            public void onChanged(List<Fence> fences) {
                fenceList = fences;
                refreshList();
            }

        });
    }

    public void initVariables(){
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        geofenceService = LocationServices.getGeofenceService(this);
        pendingIntent = getPendingIntent();
        idList = new ArrayList<String>();
        geofenceList = new ArrayList<Geofence>();
        fenceViewModel = new FenceViewModel(getApplication());
        TAG = "geoFence";
        initGeoFences();
    }

    public void initGeoFences(){
        if(fenceList != null){
            fenceList.forEach(fence -> {
                geofenceList.add(new Geofence.Builder().setUniqueId(fence.getId())
                        .setValidContinueTime(0)
                        .setRoundArea(fence.getLatitude(),fence.getLongitude(),Float.parseFloat(fence.getRadius().toString()))
                        .setConversions(fence.getFenceType().equalsIgnoreCase("ENTER") ? Geofence.ENTER_GEOFENCE_CONVERSION : Geofence.EXIT_GEOFENCE_CONVERSION)
                        .build());
                idList.add(fence.getId());
            });
        }
    }
    private GeofenceRequest getAddGeofenceRequest() {
        GeofenceRequest.Builder builder = new GeofenceRequest.Builder();
        builder.setInitConversions(GeofenceRequest.ENTER_INIT_CONVERSION);
        builder.createGeofenceList(geofenceList);
        return builder.build();
    }

    public void initRecyclerView(){
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this,fenceList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(mainRecyclerViewAdapter);
    }


    public void displayNewFenceDialog(){
        newFenceDialog = new Dialog(this);
        newFenceDialog.setContentView(R.layout.new_fence_dialog);
        Button currentLocationBtn, selectFromMapBtn, closeBtn;

        currentLocationBtn = newFenceDialog.findViewById(R.id.currentLocationBtn);
        selectFromMapBtn = newFenceDialog.findViewById(R.id.selectFromMapBtn);
        closeBtn = newFenceDialog.findViewById(R.id.closeBtn);


        currentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNameAndNoteDialog();
                newFenceDialog.dismiss();

            }
        });

        selectFromMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFenceDialog.dismiss();
            }
        });

        newFenceDialog.show();
    }

    public PendingIntent getPendingIntent(){
        Intent intent = new Intent(this, GeoFenceBroadcastReceiver.class);
        intent.setAction(GeoFenceBroadcastReceiver.ACTION_PROCESS_LOCATION);
        return PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void requestGeoFenceWithNewIntent() {
        geofenceService.createGeofenceList(getAddGeofenceRequest(), pendingIntent)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "add geofence success!");
                        } else {
                            Log.w(TAG, "add geofence failed : " + task.getException().getMessage());
                        }
                    }
                });
    }

    public void removeWithID() {
        // Remove a geofence based on its ID, and process response to the geofence deletion request.
        geofenceService.deleteGeofenceList(idList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "delete geofence with ID success!");
                        } else {
                            Log.w(TAG, "delete geofence with ID failed ");
                        }
                    }
                });
    }

    void displayToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void displayNameAndNoteDialog(){
        nameAndNoteDialog = new Dialog(this);
        nameAndNoteDialog.setContentView(R.layout.name_and_note_dialog);
        EditText etFenceName, etFenceNote;
        Button btnSave, btnCancel;
        RadioGroup radioGroup;
        etFenceName = nameAndNoteDialog.findViewById(R.id.etFenceName);
        etFenceNote = nameAndNoteDialog.findViewById(R.id.etFenceNote);
        btnSave = nameAndNoteDialog.findViewById(R.id.btnSave);
        btnCancel = nameAndNoteDialog.findViewById(R.id.btnCancel);
        radioGroup = nameAndNoteDialog.findViewById(R.id.radioGroup2);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fenceName,fenceNote,fenceType;
                fenceName = etFenceName.getText().toString();
                fenceNote = etFenceNote.getText().toString();
                fenceType = radioGroup.getCheckedRadioButtonId() == R.id.radioButtonEnter ? "ENTER" : "EXIT";
                Intent serviceIntent = new Intent(MainActivity.this, FenceItemService.class);
                Bundle b = new Bundle();
                b.putString("fenceName",fenceName);
                b.putString("fenceNote",fenceNote);
                b.putString("fenceType",fenceType);
                b.putDouble("lat",location != null ? location.getLatitude() : 44.0);
                b.putDouble("lng",location != null ? location.getLongitude(): 44.0);
                serviceIntent.putExtras(b);
                startService(serviceIntent);
                nameAndNoteDialog.dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameAndNoteDialog.dismiss();
            }
        });

        nameAndNoteDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: apply LOCATION PERMISSION successful");
            } else {
                Log.i(TAG, "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed");
            }
        }

        if (requestCode == 2) {
            if (grantResults.length > 2 && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful");
            } else {
                Log.i(TAG, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION  failed");
            }
        }
    }

    public void fabOnClick(View view) {
        displayNewFenceDialog();
    }

    BroadcastReceiver serviceBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String msg = "";
            if(action.equalsIgnoreCase("INSERT_COMPLETE")) {
                msg = "Insert completed";
            }
            refreshList();
            displayToast(msg);
        }
    };


    public void refreshList(){
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this,fenceList);
        mainRecyclerView.setAdapter(mainRecyclerViewAdapter);
    }
}