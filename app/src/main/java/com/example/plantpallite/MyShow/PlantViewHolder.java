package com.example.plantpallite.MyShow;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantpallite.R;
import com.google.android.material.card.MaterialCardView;

public class PlantViewHolder extends RecyclerView.ViewHolder {

    public TextView plantNameText, plantTypeText, nextWateringDateText, nextFertilizingDateText;
    public ImageView plantImage;
    public Button editPlantButton;
    public MaterialCardView plantCard;

    public PlantViewHolder(@NonNull View itemView) {
        super(itemView);

        // Match these IDs to layout
        plantNameText = itemView.findViewById(R.id.plantNameText);
        plantTypeText = itemView.findViewById(R.id.plantTypeText);
        nextWateringDateText = itemView.findViewById(R.id.textView8);
        nextFertilizingDateText = itemView.findViewById(R.id.textView10);
        plantImage = itemView.findViewById(R.id.plantImage);
        editPlantButton = itemView.findViewById(R.id.button);
        plantCard = itemView.findViewById(R.id.plantCardView);
    }


}
