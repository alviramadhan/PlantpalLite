package com.example.plantpallite.MyAdd;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.plantpallite.Plant;

import java.util.Date;

public class MyAddPlantViewModel extends AndroidViewModel {



    private Plant myPlant;

    public MyAddPlantViewModel(@NonNull Application application) {
        super(application);
        myPlant = new Plant();
        myPlant.setLastUpdate(new Date(System.currentTimeMillis()));
    }
    // TODO: Implement the ViewModel

    //keeping the data

    public Plant getMyPlant() {
        return myPlant;
    }

    public void setMyPlant(Plant myPlant) {
        this.myPlant = myPlant;
    }
}