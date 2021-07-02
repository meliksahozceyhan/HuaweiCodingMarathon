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

    public FenceViewModel(@NonNull Application application) {
        super(application);
        fenceRepository = new FenceRepository(application);
    }

    public LiveData<List<Fence>> gelAllFences() {
        return fenceRepository.getFences();
    }

    public void insertFence(Fence fence){
        fenceRepository.insert(fence);
    }
}
