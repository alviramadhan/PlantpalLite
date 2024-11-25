package com.example.plantpallite.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.plantpallite.DAO.PlantDAO;
import com.example.plantpallite.DAO.UserDAO;
import com.example.plantpallite.Plant;
import com.example.plantpallite.User;

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
        allPlants = plantDAO.getPlantsByUserId(1); // Replace 1 with dynamic user ID if needed
    }

    // ------------------- USER OPERATIONS -------------------

    /**
     * Insert a new user into the database.
     * This operation is executed asynchronously to avoid blocking the UI thread.
     */

//    public void login(String email, String password, OnLoginCallback callback) {
//        Callable<User> callable = () -> userDAO.getUserByEmail(email);
//        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
//            try {
//                User user = callable.call();
//                if (user != null && user.getPassword().equals(password)) {
//                    callback.onSuccess(user);
//                } else {
//                    callback.onFailure("Invalid email or password");
//                }
//            } catch (Exception e) {
//                callback.onFailure("Error during login");
//            }
//        });
//    }
//
//    public interface OnLoginCallback {
//        void onSuccess(User user);
//        void onFailure(String errorMessage);
//    }


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
     * Get a user by their email address for login purposes.
     * This operation is executed asynchronously using a Future.
     */
    public User getUserByEmail(String email) {
        Callable<User> callable = () -> userDAO.getUserByEmail(email);
        Future<User> future = MyRoomDatabase.databaseWriteExecutor.submit(callable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error retrieving user by email", e);
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
    public void deleteUser(User user) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userDAO.deleteUser(user));
    }

    // ------------------- PLANT OPERATIONS -------------------

    /**
     * Insert a new plant into the database.
     * This operation is executed asynchronously.
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
     * This allows real-time updates to the UI.
     */
    public LiveData<List<Plant>> getPlantsByUserId(int userId) {
        return plantDAO.getPlantsByUserId(userId);
    }

    /**
     * Get details of a specific plant by its ID.
     * This operation is executed asynchronously using a Future.
     */
    public Plant getPlantById(int plantId) {
        Callable<Plant> callable = () -> plantDAO.getPlantById(plantId);
        Future<Plant> future = MyRoomDatabase.databaseWriteExecutor.submit(callable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error retrieving plant by ID", e);
        }
    }

    /**
     * Get a list of plants with upcoming tasks (e.g., watering or fertilizing).
     */
    public LiveData<List<Plant>> getPlantsWithUpcomingTasks(long upcomingDate) {
        return plantDAO.getPlantsWithUpcomingTasks(upcomingDate);
    }
}

