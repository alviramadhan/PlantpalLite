package com.example.plantpallite.MyShow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Plant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyPlantScheduleViewModel extends AndroidViewModel {
    private final MyPlantpalRepository repository;

    public MyPlantScheduleViewModel(@NonNull Application application) {
        super(application);
        this.repository = new MyPlantpalRepository(application);
    }

    // Fetch plant details by ID
    public LiveData<Plant> getPlantById(int plantId) {
        return repository.getPlantById(plantId);
    }

    // Generate dynamic watering schedule
    public List<String> generateWateringSchedule(int frequency, Date lastUpdate) {
        return generateSchedule(frequency, lastUpdate);
    }

    // Generate dynamic fertilizing schedule
    public List<String> generateFertilizingSchedule(int frequency, Date lastUpdate) {
        return generateSchedule(frequency, lastUpdate);
    }

    // Here is method to calculate schedules
    private List<String> generateSchedule(int frequency, Date lastUpdate) {
        List<String> schedule = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastUpdate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Generate 5 occurrences
        for (int i = 0; i < 5; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, frequency); // Add frequency in days
            schedule.add(dateFormat.format(calendar.getTime())); // Format date as dd/MM/yyyy
        }

        return schedule;
    }
}