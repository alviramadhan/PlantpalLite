package com.example.plantpallite.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.plantpallite.Plant;

import java.util.Date;
import java.util.List;

@Dao
public interface PlantDAO {

    // Insert a new plant
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPlant(Plant plant);

    // Get all plants by user ID
    @Query("SELECT * FROM PLANT WHERE USER_ID = :userId")
    LiveData<List<Plant>> getPlantsByUserId(int userId);

    // Get a plant by its ID
    @Query("SELECT * FROM PLANT WHERE ID = :plantId")
    LiveData<Plant> getPlantById(int plantId);

    // Update a plant
    @Update
    int updatePlant(Plant plant);

    // Delete a plant
    @Delete
    int deletePlant(Plant plant);

    // Get plants with upcoming tasks (e.g., watering, fertilizing)
    @Query("SELECT * FROM PLANT WHERE LAST_UPDATE < :upcomingDate")
    LiveData<List<Plant>> getPlantsWithUpcomingTasks(long upcomingDate);

    @Query("UPDATE PLANT SET LAST_WATERING_DATE = :newDate WHERE ID = :plantId")
    void updateLastWateringDate(int plantId, Date newDate);

    @Query("UPDATE PLANT SET LAST_FERTILIZING_DATE = :newDate WHERE ID = :plantId")
    void updateLastFertilizingDate(int plantId, Date newDate);

    @Query("UPDATE PLANT SET LAST_UPDATE     = :newDate WHERE ID = :plantId")
    void  setLastUpdate(int plantId, Date newDate);
}

