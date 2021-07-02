package com.meliksah.huaweicodingmarathonproject.model;

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

    @NonNull
    private String name;

    @NonNull
    @ColumnInfo(name= "longitude")
    private Long longitude;

    @NonNull
    @ColumnInfo(name= "latitude")
    private Long latitude;

    @NonNull
    @ColumnInfo(name= "radius")
    private Long radius;

    @NonNull
    @ColumnInfo(name= "fence_type")
    private String fenceType;

    public Fence(@NonNull String id, @NonNull Long longitude, @NonNull Long latitude, @NonNull Long radius, @NonNull String fenceType) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.fenceType = fenceType;
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
    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull Long longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull Long latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public Long getRadius() {
        return radius;
    }

    public void setRadius(@NonNull Long radius) {
        this.radius = radius;
    }

    @NonNull
    public String getFenceType() {
        return fenceType;
    }

    public void setFenceType(@NonNull String fenceType) {
        this.fenceType = fenceType;
    }
}
