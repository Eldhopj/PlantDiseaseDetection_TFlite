package com.eldhopj.plantdiseasedetection_tflite.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.eldhopj.plantdiseasedetection_tflite.DiseaseAdapter;
import com.eldhopj.plantdiseasedetection_tflite.R;
import com.eldhopj.plantdiseasedetection_tflite.Utility;
import com.eldhopj.plantdiseasedetection_tflite.databinding.ActivityHistoryBinding;
import com.eldhopj.plantdiseasedetection_tflite.room.RoomClient;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private DiseaseAdapter diseaseAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, HistoryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        initRecyclerView();
        fetchData();
    }

    private void initRecyclerView() {
        diseaseAdapter = new DiseaseAdapter(getApplicationContext());
        Utility.setVerticalRecyclerView(binding.recyclerView, diseaseAdapter,
                getApplicationContext(), false, true);
    }

    private void fetchData() {
        RoomClient.getDatabase(getApplicationContext()).diseaseDao()
                .getAllData().observe(this, diseaseLists -> {
            diseaseAdapter.addItemRange(diseaseLists);
        });
    }
}
