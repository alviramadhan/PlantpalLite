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


    private final MyPlantpalRepository repository;
    private final MutableLiveData<List<Plant>> plants = new MutableLiveData<>();

    public MyShowAllPlantViewModel(@NonNull Application application) {
        super(application);
        repository = new MyPlantpalRepository(application);
    }

    // Fetch plants for a specific userId
    public void fetchPlantsByUserId(int userId) {
        if (userId != -1) { // Validate userId
            repository.getPlantsByUserId(userId).observeForever(plants::setValue);
        } else {
            plants.setValue(null); // Clear the list if userId is invalid
        }
    }

    // Expose plants LiveData to the fragment
    public LiveData<List<Plant>> getPlants() {
        return plants;
    }

    // Delete a plant
    public void deletePlant(Plant plant) {
        repository.deletePlant(plant);
    }
}
