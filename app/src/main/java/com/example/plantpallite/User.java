package com.example.plantpallite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

    @Entity(tableName = "USER",
            indices = {@Index(value = "EMAIL_ADDRESS", unique = true)}
    )
    public class User {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "USER_ID")
        private Integer userId;    // Unique identifier for the user

        @ColumnInfo(name = "EMAIL_ADDRESS")
        private String email;    // User's email address

        @ColumnInfo(name = "PASSWORD")
        private String password;  // User's password

        @ColumnInfo(name = "FIRST_NAME")
        private String firstName; // User's first name

        @ColumnInfo(name = "LAST_NAME")
        private String lastName;   // User's last name

    // Constructor
    public User(Integer userId, String email, String password, String firstName, String lastName) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Ignore// Default constructor
    public User() {
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Method to get the full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='[HIDDEN]'" + // Avoid printing passwords in logs
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

