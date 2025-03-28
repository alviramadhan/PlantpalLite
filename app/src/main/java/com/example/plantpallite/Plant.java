package com.example.plantpallite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "PLANT",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "USER_ID",
                childColumns = "USER_ID",
                onDelete = ForeignKey.CASCADE // Deletes plants if the user is deleted
        ),
        indices = {@Index(value = "USER_ID")} // Indexing for performance
)
    public class Plant {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "ID")
        private Integer id;   // Plant name

        @ColumnInfo(name = "NAME")
        private String name;

        @ColumnInfo(name = "TYPE")
        private String type;    // Plant type (Indoor, Outdoor, Succulent, etc.)

        @ColumnInfo(name = "WATERING_FREQUENCY")
        private String wateringFrequency;  // Watering frequency

        @ColumnInfo(name = "FERTILIZING_FREQUENCY")
        private String fertilizingFrequency;  // Fertilizing frequency

        @ColumnInfo(name = "PLANTING_DATE")
        private long plantingDate;    // Timestamp of planting date (in milliseconds)

        @ColumnInfo(name = "USER_ID")
        private Integer userID;  // ID of the user who owns the plant

        @ColumnInfo(name = "LAST_UPDATE")
        private Date lastUpdate;  // Timestamp of the last update

        @ColumnInfo(name = "IMAGE_URL")
        private String image;    // URL or file path of the plant image

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Constructor
    public Plant(Integer id, String name, String type, String wateringFrequency, String fertilizingFrequency,
                 long plantingDate, Integer userID, Date lastUpdate, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.wateringFrequency = wateringFrequency;
        this.fertilizingFrequency = fertilizingFrequency;
        this.plantingDate = plantingDate;
        this.userID = userID;
        this.lastUpdate = lastUpdate;
        this.image = image;
    }

    // Default constructor
    public Plant() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public String getFertilizingFrequency() {
        return fertilizingFrequency;
    }

    public void setFertilizingFrequency(String fertilizingFrequency) {
        this.fertilizingFrequency = fertilizingFrequency;
    }

    public long getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(long plantingDate) {
        this.plantingDate = plantingDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    // Method to calculate plant age in days
    public long getPlantAgeInDays() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - plantingDate) / (1000 * 60 * 60 * 24); // Convert milliseconds to days
    }

    // Method to calculate days since the last update
    public long getDaysSinceLastUpdate() {
        if (lastUpdate != null) {
            long currentTime = System.currentTimeMillis();
            return (currentTime - lastUpdate.getTime()) / (1000 * 60 * 60 * 24); // Convert milliseconds to days
        }
        return -1; // Return -1 if lastUpdate is null
    }


    @Override
    public String toString() {
        return "Plant{" +
                "id= '" + id + '\'' +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", wateringFrequency='" + wateringFrequency + '\'' +
                ", fertilizingFrequency='" + fertilizingFrequency + '\'' +
                ", plantingDate=" + plantingDate +
                ", userID='" + userID + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", image='" + image + '\'' +
                '}';
    }
}
