package com.example.plantpallite.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.plantpallite.Converters;
import com.example.plantpallite.DAO.PlantDAO;
import com.example.plantpallite.DAO.UserDAO;
import com.example.plantpallite.Plant;
import com.example.plantpallite.User;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {Plant.class, User.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MyRoomDatabase extends RoomDatabase {

    //For the DAOs
    public abstract PlantDAO plantDAO();
    public abstract UserDAO userDAO();

    private static volatile MyRoomDatabase INSTANCE;


    // Thread pool for database operations
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {//it prevents the threads to create multiple database simultaneously
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "my_database")
                            .addCallback(roomCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                    //This statement is to force the database creation
                    INSTANCE.query("SELECT 1",null);
                }
            }
        }
        return INSTANCE;
    }


    public static Callback roomCallback = new Callback(){
        //onCreate will be call only the first time the database is created.
        //This is a great place to set up initial data in the database.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateInitialData(INSTANCE);
            Log.i("XYZ", "onCreate Called");
        }

        //onOpen will be called every time the database is opened
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.i("XYZ", "onOpen Called");
        }
    };

    private static void populateInitialData(MyRoomDatabase instance) {
        databaseWriteExecutor.execute(() -> {
            // Insert default users
            UserDAO userDAO = instance.userDAO();
            userDAO.insertUser(new User(1, "admin@plantpal.com", "admin123", "Admin", "User"));
            userDAO.insertUser(new User(2, "tester@plantpal.com", "test123", "Test", "User"));

            // Insert default plants
            PlantDAO plantDAO = instance.plantDAO();
            plantDAO.insertPlant(new Plant(1, "Rose", "Outdoor", "Daily", "Weekly",
                    System.currentTimeMillis(), 1, new Date(System.currentTimeMillis()), null));
            plantDAO.insertPlant(new Plant(2, "Cactus", "Indoor","Weekly", "Monthly",
                    System.currentTimeMillis(), 2, new Date(System.currentTimeMillis()), null));

        });
    }

}
