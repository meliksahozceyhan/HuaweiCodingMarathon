package com.meliksah.huaweicodingmarathonproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.meliksah.huaweicodingmarathonproject.model.Fence;

import java.util.List;

@Dao
public interface FenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFence(Fence fence);

    @Delete
    public void delete(Fence fence);

    @Update
    public void update(Fence fence);

    @Query("SELECT * FROM fences")
    public List<Fence>  getAll();
}
