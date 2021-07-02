package com.meliksah.huaweicodingmarathonproject.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.meliksah.huaweicodingmarathonproject.dao.FenceDao;
import com.meliksah.huaweicodingmarathonproject.database.FenceRoomDatabase;
import com.meliksah.huaweicodingmarathonproject.model.Fence;

import java.util.Date;
import java.util.List;

public class FenceRepository {
    private FenceDao fenceDao;

    public FenceRepository(Application app){
        FenceRoomDatabase db = FenceRoomDatabase.getDatabase(app);
        fenceDao = db.fenceDao();
    }

    public void insert(Fence fence){
        fence.setCreatedAt(new Date());
        FenceRoomDatabase.databaseWriteExecutor.execute(() ->{
            fenceDao.insertFence(fence);
        });
        fenceDao.insertFence(fence);
    }

    public void delete(Fence fence){
        fenceDao.delete(fence);
    }

    public LiveData<List<Fence>> getFences(){
        return fenceDao.getAll();
    }

}
