package com.example.plantpallite.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;

import com.example.plantpallite.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE) //the conflicting data will just be ignored.
    long insertUser(User user);

    // Update user
    @Update
    int updateUser(User user);

    // Delete a user
    @Delete
    int deleteUser(User user);

    // Check if email exists (for registration)
    @Query("SELECT COUNT(*) FROM USER WHERE EMAIL_ADDRESS = :email")
    int emailExists(String email);

    // Get user by email (for login)
    //USE upper to make it easier to compare regardless of user input.
    @Query("SELECT * FROM USER WHERE UPPER(EMAIL_ADDRESS) = UPPER(:email)")
    User getUserByEmail(String email);

    // Get user by ID
    @Query("SELECT * FROM USER WHERE USER_ID = :userId")
    User getUserById(int userId);

}
