package com.example.plantpallite.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.plantpallite.DAO.PlantDAO;
import com.example.plantpallite.DAO.UserDAO;
import com.example.plantpallite.Plant;
import com.example.plantpallite.User;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyPlantpalRepository {
    // DAOs for interacting with Plant and User tables
    private PlantDAO plantDAO;
    private UserDAO userDAO;

    // LiveData for observing all plants in the database
    private LiveData<List<Plant>> allPlants;

    /**
     * Constructor for MyPlantpalRepository
     * Initializes DAOs and LiveData using the Room database instance.
     */
    public MyPlantpalRepository(Application application) {
        // Get the database instance
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);

        // Initialize DAOs
        plantDAO = db.plantDAO();
        userDAO = db.userDAO();

        // Initialize LiveData for all plants
        //allPlants = plantDAO.getPlantsByUserId(1); // Replace 1 with dynamic user ID
    }

    // ------------------- USER OPERATIONS -------------------

    /**
     * Insert a new user into the database.
     * This operation is executed asynchronously to avoid blocking the UI thread.
     */



    public User login(String email, String password) {
        User user = userDAO.getUserByEmail(email); // Query the user by email
        if (user != null && user.getPassword().equals(password)) {
            return user; // Valid user found
        }
        return null; // Login failed
    }

    public void insertUser(User user) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userDAO.insertUser(user));
    }

    /**
     * Check if an email exists in the database for registration.
     * Returns true if the email is already in use.
     */
    public boolean isEmailExists(String email) {
        Callable<Boolean> callable = () -> userDAO.emailExists(email) > 0;
        Future<Boolean> future = MyRoomDatabase.databaseWriteExecutor.submit(callable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error checking email existence", e);
        }
    }

    /**
     * Update a user's information in the database.
     */
    public void updateUser(User user) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userDAO.updateUser(user));
    }

    /**
     * Delete a user from the database.
     */
    public void deleteUser(int userId) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDAO.getUserById(userId); // Fetch user by ID
            if (user != null) {
                userDAO.deleteUser(user); // Delete the fetched user
            }
        });
        }

    // ------------------- PLANT OPERATIONS -------------------

    /**
     * Insert a new plant into the database.
     * xecuted asynchronously.
     */
    public void insertPlant(Plant plant) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> plantDAO.insertPlant(plant));
    }

    /**
     * Update an existing plant's data in the database.
     */
    public void updatePlant(Plant plant) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> plantDAO.updatePlant(plant));
    }

    /**
     * Delete a plant from the database.
     */
    public void deletePlant(Plant plant) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> plantDAO.deletePlant(plant));
    }

    /**
     * Get all plants for a specific user ID as a LiveData object.
     * real-time updates to the UI.
     */
    public LiveData<List<Plant>> getPlantsByUserId(int userId) {
        return plantDAO.getPlantsByUserId(userId);
    }

    /**
     * Get details of a specific plant by its ID.
     * executed asynchronously using a Future.
     */
    public LiveData<Plant> getPlantById(int plantId) {
        try {
            LiveData<Plant> plant = plantDAO.getPlantById(plantId);
            if (plant == null) {
                throw new RuntimeException("No plant found with ID: " + plantId);
            }
            return plant;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving plant by ID: " + plantId, e);
        }
    }

    public void updateLastWateringDate(int plantId, Date newDate) {
        plantDAO.updateLastWateringDate(plantId, newDate);
    }

    public void updateLastFertilizingDate(int plantId, Date newDate) {
        plantDAO.updateLastFertilizingDate(plantId, newDate);
    }

    public  void  setLastUpdate(int plantId, Date newDate)
    {
        plantDAO.setLastUpdate(plantId, newDate);
    }

    /**
     * Get a list of plants with upcoming tasks
     */
    public LiveData<List<Plant>> getPlantsWithUpcomingTasks(long upcomingDate) {
        return plantDAO.getPlantsWithUpcomingTasks(upcomingDate);
    }
}

