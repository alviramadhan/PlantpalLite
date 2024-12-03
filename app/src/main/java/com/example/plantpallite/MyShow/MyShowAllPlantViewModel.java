package com.example.plantpallite.MyShow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Plant;

import java.util.List;
public class MyShowAllPlantViewModel extends AndroidViewModel {  //need the Application context to initialize the repository
    private  MyPlantpalRepository repository;
    private  LiveData<List<Plant>>  plants;

    public MyShowAllPlantViewModel(@NonNull Application application) {
        super(application);
        // Initialize the repository with the application context
        repository = new MyPlantpalRepository(application);
        plants = repository.getPlantsByUserId(1); // Replace 1 with the actual user ID
    }

    // Expose LiveData to the fragment
    public LiveData<List<Plant>> getPlantsByUserId(int userId) {
        return repository.getPlantsByUserId(1);
    }
}

