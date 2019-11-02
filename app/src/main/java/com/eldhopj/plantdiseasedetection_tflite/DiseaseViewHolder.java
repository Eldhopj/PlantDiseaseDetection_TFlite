package com.eldhopj.plantdiseasedetection_tflite;

import androidx.recyclerview.widget.RecyclerView;

import com.eldhopj.plantdiseasedetection_tflite.databinding.ItemDiseaseHistoryBinding;

class DiseaseViewHolder extends RecyclerView.ViewHolder {
    private final ItemDiseaseHistoryBinding binding;

    public DiseaseViewHolder(ItemDiseaseHistoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemDiseaseHistoryBinding getBinding() {
        return binding;
    }
}
