package com.example.plantpallite.MyAdd;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Plant;

import java.util.Date;
public class MyAddPlantViewModel extends AndroidViewModel {

    private Plant myPlant;
    private final MyPlantpalRepository repository;

    public MyAddPlantViewModel(@NonNull Application application) {
        super(application);
        repository = new MyPlantpalRepository(application);
        myPlant = new Plant();
        myPlant.setLastUpdate(new Date(System.currentTimeMillis()));
    }

    public Plant getMyPlant() {
        return myPlant;
    }

    public void setMyPlant(Plant myPlant) {
        this.myPlant = myPlant;
    }

    public void saveNewPlant() {
        repository.insertPlant(myPlant);
    }

    public void setUserId(int userId) {
        if (myPlant != null) {
            myPlant.setUserID(userId);
        }
    }



}

