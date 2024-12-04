package com.example.plantpallite.MyShow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Plant;

import java.util.ArrayList;
import java.util.List;

public class MyShowAllPlantViewModel extends AndroidViewModel {
//    private final MyPlantpalRepository repository;
//    private LiveData<List<Plant>> plants;
//
//    public MyShowAllPlantViewModel(@NonNull Application application) {
//        super(application);
//        repository = new MyPlantpalRepository(application);
//    }
//
//    public void setUserId(int userId) {
//        // Update LiveData to fetch plants for the given userId
//        plants = repository.getPlantsByUserId(userId);
//    }
//
//    public LiveData<List<Plant>> getPlants(int userId) {
//        return plants;
//    }

    private final MyPlantpalRepository repository;
    private MutableLiveData<List<Plant>> plantsLiveData = new MutableLiveData<>();

    public MyShowAllPlantViewModel(@NonNull Application application) {
        super(application);
        repository = new MyPlantpalRepository(application);
    }

    // Set userId and fetch plants
    public void fetchPlantsByUserId(int userId) {
        if (userId != -1) {
            repository.getPlantsByUserId(userId).observeForever(plantsLiveData::setValue);
        } else {
            plantsLiveData.setValue(new ArrayList<>()); // Empty list for invalid userId
        }
    }

    // Expose LiveData to the fragment
    public LiveData<List<Plant>> getPlantsLiveData() {
        return plantsLiveData;
    }
}
