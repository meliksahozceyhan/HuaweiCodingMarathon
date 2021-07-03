package com.meliksah.huaweicodingmarathonproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.meliksah.huaweicodingmarathonproject.model.Fence;
import com.meliksah.huaweicodingmarathonproject.repository.FenceRepository;

import java.util.List;

public class FenceViewModel extends AndroidViewModel {
    private final FenceRepository fenceRepository;
    private LiveData<List<Fence>> fenceList;

    public FenceViewModel(@NonNull Application application) {
        super(application);
        fenceRepository = new FenceRepository(application);
        fenceList = fenceRepository.getFences();
    }

    public LiveData<List<Fence>> getFences() {
        return fenceList;
    }

    public void insertFence(Fence fence){
        fenceRepository.insert(fence);
    }
}
