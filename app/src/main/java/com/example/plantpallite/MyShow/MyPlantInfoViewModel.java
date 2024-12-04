package com.example.plantpallite.MyShow;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Plant;

public class MyPlantInfoViewModel extends AndroidViewModel {

    private final MyPlantpalRepository repository;
    private MutableLiveData<Plant> plantLiveData = new MutableLiveData<>();

    public MyPlantInfoViewModel(Application application) {
        super(application);
        this.repository = new MyPlantpalRepository(application);
    }

    public LiveData<Plant> getPlantById(int plantId) {
        return repository.getPlantById(plantId);
    }

    public LiveData<Plant> getPlantLiveData() {
        return plantLiveData;
    }

}