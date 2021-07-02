package com.meliksah.huaweicodingmarathonproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "fences")
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
}
