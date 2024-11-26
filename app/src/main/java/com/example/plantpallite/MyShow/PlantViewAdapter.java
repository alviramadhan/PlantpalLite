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
    private OnPlantClickListener listener;
    private  OnPlantEditListener listenerEdit;

    // Constructor
    public PlantViewAdapter(List<Plant> plants, OnPlantClickListener listener, OnPlantEditListener listenerEdit) {
        this.plants = plants;
        this.listener = listener;
        this.listenerEdit = listenerEdit;
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
        holder.nextWateringDateText.setText("Next Watering Date: " + plant.getWateringFrequency());
        holder.nextFertilizingDateText.setText("Next Fertilizing Date: " + plant.getFertilizingFrequency());
        holder.plantImage.setImageResource(R.drawable.plant_placeholder); // Placeholder image

        // Set click listener for the card
        holder.plantCard.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPlantClick(plant);
            }
        });

        // Handle "Edit Plant" button click
        holder.editPlantButton.setOnClickListener(v -> {
            if (listenerEdit != null) {
                listenerEdit.onPlantEdit(plant);
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

    public interface OnPlantEditListener {
        void onPlantEdit(Plant plant);
    }
}
