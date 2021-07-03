package com.meliksah.huaweicodingmarathonproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.meliksah.huaweicodingmarathonproject.converter.DateConverter;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "fences")
@TypeConverters(DateConverter.class)
public class Fence {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name= "id")
    private String id;

    @NonNull
    @ColumnInfo(name = "created_at")
    private Date createdAt;


    private String name;

    @NonNull
    @ColumnInfo(name= "longitude")
    private Double longitude;

    @NonNull
    @ColumnInfo(name= "latitude")
    private Double latitude;

    @NonNull
    @ColumnInfo(name= "radius")
    private Double radius;

    @ColumnInfo(name= "fence_type")
    private String fenceType;


    private String note;



    public Fence(String name,@NonNull Double longitude, @NonNull Double latitude, @NonNull Double radius, String fenceType, String note) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.fenceType = fenceType;
        this.note = note;
    }
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull Date createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull Double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull Double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public Double getRadius() {
        return radius;
    }

    public void setRadius(@NonNull Double radius) {
        this.radius = radius;
    }

    @NonNull
    public String getFenceType() {
        return fenceType;
    }

    public void setFenceType(@NonNull String fenceType) {
        this.fenceType = fenceType;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }

}
