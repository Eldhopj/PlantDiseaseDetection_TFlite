package com.eldhopj.plantdiseasedetection_tflite.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.eldhopj.plantdiseasedetection_tflite.R;
import com.eldhopj.plantdiseasedetection_tflite.databinding.ActivityDetailBinding;
import com.eldhopj.plantdiseasedetection_tflite.tfLite.Recognition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private List<Recognition> results;
    private Bitmap capturedImage;
    private Toolbar toolbar;

    public static void start(Context context, ArrayList<Recognition> results, byte[] bitmap) {
        Intent starter = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putByteArray("bitmap",bitmap);
        bundle.putParcelableArrayList("result", results);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        getExtras();
        setData();
        setToolBar();
    }

    private void setToolBar() {
        setSupportActionBar((Toolbar) binding.toolbarMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Disease Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // For back arrow on toolbar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //For Back navigation Icon logic
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setData() {
        binding.DiseaseOne.setText(results.get(0).getTitle() + " chances are "+ (results.get(0).getConfidence()*100));
        binding.capturedImage.setImageBitmap(capturedImage);
    }

    private void getExtras () {
        Intent intent = getIntent();
        if (intent != null) {
            results = intent.getParcelableArrayListExtra("result");

            // Decoding image
            capturedImage = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra("bitmap"), 0,
                    Objects.requireNonNull(intent.getByteArrayExtra("bitmap")).length);
        }
    }

}
