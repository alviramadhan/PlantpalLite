package com.example.plantpallite.MyShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantpallite.Plant;
import com.example.plantpallite.R;

import java.util.List;

// PlantViewAdapter is for managing the list of items
// and delegating the view binding to PlantViewHolder
public class PlantViewAdapter extends RecyclerView.Adapter<PlantViewHolder> {


    private List<Plant> plants;
    private final OnPlantClickListener clickListener;
    private final OnPlantDeleteListener deleteListener;



    // Constructor
    public PlantViewAdapter(List<Plant> plants, OnPlantClickListener clickListener, OnPlantDeleteListener deleteListener) {
        this.plants = plants;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_recycler_view_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plants.get(position);

        // Bind plant data to views
        holder.plantNameText.setText(plant.getName());
        holder.plantTypeText.setText(plant.getType());
        holder.nextWateringDateText.setText("Type of Watering Dates: " + plant.getWateringFrequency());
        holder.nextFertilizingDateText.setText("Type of Fertilizing Dates: " + plant.getFertilizingFrequency());
        holder.plantImage.setImageResource(R.drawable.plant_placeholder); // Placeholder image

        // Handle clicks
        holder.plantCard.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onPlantClick(plant);
            }
        });

        holder.editPlantButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onPlantEdit(plant);
            }
        });
    }
    @Override
    public int getItemCount() {
        return plants != null ? plants.size() : 0;
    }

    // Update the list of plants
    public void updateData(List<Plant> newPlants) {
        this.plants = newPlants;
        notifyDataSetChanged();
    }

    // Interface for click listener
    public interface OnPlantClickListener {
        void onPlantClick(Plant plant);
    }

    public interface OnPlantDeleteListener {
        void onPlantEdit(Plant plant);
    }
}
