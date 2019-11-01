package com.eldhopj.plantdiseasedetection_tflite.tfLite;

import android.graphics.Bitmap;

import java.util.List;

public interface Classifier {

    List<Recognition> recognizeImage(Bitmap bitmap);

    void close();
}
