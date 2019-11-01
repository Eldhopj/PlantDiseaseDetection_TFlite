package com.eldhopj.plantdiseasedetection_tflite.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.eldhopj.plantdiseasedetection_tflite.R;
import com.eldhopj.plantdiseasedetection_tflite.databinding.ActivityDetailBinding;
import com.eldhopj.plantdiseasedetection_tflite.room.DiseaseDao;
import com.eldhopj.plantdiseasedetection_tflite.room.DiseaseList;
import com.eldhopj.plantdiseasedetection_tflite.room.RoomClient;
import com.eldhopj.plantdiseasedetection_tflite.tfLite.Recognition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private List<Recognition> results;
    private Bitmap capturedImage;

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

    //Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Selection of items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) { // get the ID
            case R.id.action_history_btn:
                HistoryActivity.start(getApplicationContext());
                return true;
            default:
                return false;

        }
    }

    //For Back navigation Icon logic
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (!results.isEmpty()) {
            String result = "Chances for " + results.get(0).getTitle() + " is " + convertFloatIntoPercentage(results.get(0).getConfidence());
            binding.DiseaseOne.setText(result);
            setIntoLocalDb(result);
        }
        binding.capturedImage.setImageBitmap(capturedImage);
    }

    private void setIntoLocalDb(String disease) {
        RoomClient roomClient = RoomClient.getDatabase(getApplicationContext());
        new InsertAsyncTask(roomClient.diseaseDao()).execute(new DiseaseList(disease));
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

    @SuppressLint("DefaultLocale")
    private String convertFloatIntoPercentage(Float confidence) {
        return String.format("%.2f", confidence * 100) + "%";
    }


    private static class InsertAsyncTask extends AsyncTask<DiseaseList, Void, Void> {

        private final DiseaseDao mAsyncTaskDao;

        InsertAsyncTask(DiseaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(DiseaseList... diseaseLists) {
            mAsyncTaskDao.insert(diseaseLists[0]);
            return null;
        }
    }

}
