package com.example.plantpallite.MyShow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Plant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyPlantScheduleViewModel extends AndroidViewModel {
    private final MyPlantpalRepository repository;
    private MutableLiveData<Plant> plantLiveData = new MutableLiveData<>();

    public MyPlantScheduleViewModel(@NonNull Application application) {
        super(application);
        repository = new MyPlantpalRepository(application);
    }

    public LiveData<Plant> getPlantById(int plantId) {
        return repository.getPlantById(plantId);
    }
//


    public void updateLastWateredDate(Plant plant, Date newDate) {
        plant.setLastWateringDate(newDate);
        repository.updatePlant(plant);
    }

    public void updateLastFertilizingDate(Plant plant, Date newDate) {
        plant.setLastFertilizingDate(newDate);
        repository.updatePlant(plant);
    }

    public void setLastUpdate(Plant plant, Date newDate){
        plant.setLastUpdate(newDate);
        repository.updatePlant(plant);
    }



    //For mapping the string to int
    private int getFrequencyInDays(String frequency) {
        switch (frequency) {
            case "Daily":
                return 1;
            case "Every 2 Days":
                return 2;
            case "Weekly":
                return 7;
            case "Biweekly":
                return 14;
            case "Monthly":
                return 30;
            default:
                throw new IllegalArgumentException("Unknown frequency: " + frequency);
        }
    }



    //Watering Frequency datess
    public String[] calculateWateringDates(Plant plant) {
        String[] dates = new String[4];
        int frequency = getFrequencyInDays(plant.getWateringFrequency()); // Replace with fertilizing if needed
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(plant.getLastUpdate());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        for (int i = 0; i < 4; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, frequency);
            dates[i] = dateFormat.format(calendar.getTime());
        }

        return dates;
    }

    //Fertilizing Frequency dates
    public String[] calculateFertilizingDates(Plant plant) {
        String[] dates = new String[4];
        int frequency = getFrequencyInDays(plant.getFertilizingFrequency()); // Replace with fertilizing if needed
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(plant.getLastUpdate());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        for (int i = 0; i < 4; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, frequency);
            dates[i] = dateFormat.format(calendar.getTime());
        }

        return dates;
    }




}