package com.meliksah.huaweicodingmarathonproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.meliksah.huaweicodingmarathonproject.R;
import com.meliksah.huaweicodingmarathonproject.service.FenceItemService;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private HuaweiMap huaweiMap;
    private MapView mapView;
    private LocationCallback mLocationCallback;
    private Location mLastKnownLocation;
    private int DEFAULT_ZOOM = 15;
    private Marker mMarker;

    private Button saveButton, cancelButton;
    private Dialog nameAndNoteDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapView);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        initListeners();

        Bundle mapViewBundle = null;

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);


    }

    public void initListeners(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNameAndNoteDialog();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        huaweiMap.setOnMapClickListener(new HuaweiMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                addMarker(latLng);
            }
        });

    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        huaweiMap.setMyLocationEnabled(true);
        huaweiMap.getUiSettings().setCompassEnabled(true);
        huaweiMap.getUiSettings().setZoomControlsEnabled(true);
        huaweiMap.getUiSettings().setRotateGesturesEnabled(true);
        huaweiMap.getUiSettings().setScrollGesturesEnabled(true);
        huaweiMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);
        huaweiMap.getUiSettings().setTiltGesturesEnabled(true);
        huaweiMap.getUiSettings().setZoomGesturesEnabled(false);
        huaweiMap.getUiSettings().setAllGesturesEnabled(true);
        huaweiMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        huaweiMap.getUiSettings().setMapToolbarEnabled(true);

        updateLocationUI();
        getDeviceLocation();

        huaweiMap = huaweiMap;
    }

    private void updateLocationUI() {
        if (huaweiMap == null) {
            return;
        }
        try {
            huaweiMap.setMyLocationEnabled(true);
            huaweiMap.getUiSettings().setMyLocationButtonEnabled(true);
            huaweiMap.setPadding(10, 30, 50, 70);
        } catch (SecurityException e) {
            Log.e("TAG", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult != null) {
                    List<Location> locations = locationResult.getLocations();

                    if (!locations.isEmpty()) {
                        mLastKnownLocation = locationResult.getLastLocation();
                        huaweiMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()),
                                (float) DEFAULT_ZOOM));
                        addMarker(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()));
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
    }



    private void addMarker(LatLng latLng) {
        if(mMarker != null){
            mMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng).draggable(true);
        mMarker = huaweiMap.addMarker(markerOptions);
        huaweiMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
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
                LatLng position = mMarker.getPosition();
                fenceName = etFenceName.getText().toString();
                fenceNote = etFenceNote.getText().toString();
                fenceType = radioGroup.getCheckedRadioButtonId() == R.id.radioButtonEnter ? "ENTER" : "EXIT";
                Intent serviceIntent = new Intent(MapActivity.this, FenceItemService.class);
                Bundle b = new Bundle();
                b.putString("fenceName",fenceName);
                b.putString("fenceNote",fenceNote);
                b.putString("fenceType",fenceType);
                b.putDouble("lat",position.latitude);
                b.putDouble("lng",position.longitude);
                b.putString("comingFrom","map");
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
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}