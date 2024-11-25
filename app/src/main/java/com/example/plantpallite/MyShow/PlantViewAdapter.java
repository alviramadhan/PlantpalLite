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
    private OnPlantEditListener listener;

    public PlantViewAdapter(List<Plant> plants, OnPlantEditListener listener) {
        this.plants = plants;
        this.listener = listener;
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


        // Handle "Edit Plant" button click
        holder.editPlantButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(plant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plants != null ? plants.size() : 0;
    }

    public interface OnPlantEditListener {
        void onEdit(Plant plant);
    }
}
