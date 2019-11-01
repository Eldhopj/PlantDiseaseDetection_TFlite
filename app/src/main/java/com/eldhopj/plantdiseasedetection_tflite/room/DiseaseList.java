package com.eldhopj.plantdiseasedetection_tflite.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "disease_table") //name of the table
public class DiseaseList {
    @PrimaryKey(autoGenerate = true)//Room will auto generate the ID , we don't wanna worry about it
    private int id;
    private String disease;

    public DiseaseList(String disease) {
        this.disease = disease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

}
