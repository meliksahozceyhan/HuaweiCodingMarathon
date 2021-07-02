package com.meliksah.huaweicodingmarathonproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.meliksah.huaweicodingmarathonproject.dao.FenceDao;
import com.meliksah.huaweicodingmarathonproject.model.Fence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Fence.class},version = 1,exportSchema = false)
public abstract class FenceRoomDatabase extends RoomDatabase {
    public abstract FenceDao fenceDao();
    private static volatile FenceRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FenceRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (FenceRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),FenceRoomDatabase.class,"fence_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
