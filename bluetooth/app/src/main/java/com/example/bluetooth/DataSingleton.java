package com.example.bluetooth;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.HashMap;
import java.util.Map;

public class DataSingleton {
    private static DataSingleton instance = null;

    // Weather data
    private double temperature, pressure, humidity, gas;

    // Medical data
    private double heartRate, bloodOxygen, bodyTemperature;
    private FirebaseFirestore db;

    // Private constructor to prevent instantiation
    private DataSingleton() {
        db = FirebaseFirestore.getInstance();
    }

    // Static method to get the instance
    public static DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

    // Methods to set data
    // Methods to set data
    public void setBodyTemp(double data) {
        this.bodyTemperature = data;
        sendDataToFirestore("bodyTemperature", data);
    }

    public void setBloodOx(double data) {
        this.bloodOxygen = data;
        sendDataToFirestore("bloodOxygen", data);
    }

    public void setHeartRate(double data) {
        this.heartRate = data;
        sendDataToFirestore("heartRate", data);
    }

    public void setTempData(double data) {
        this.temperature = data;
        sendDataToFirestore("temperature", data);
    }

    public void setPressData(double data) {
        this.pressure = data;
        sendDataToFirestore("pressure", data);
    }

    public void setHumidity(double data) {
        this.humidity = data;
        sendDataToFirestore("humidity", data);
    }

    public void setGas(double data) {
        this.gas = data;
        sendDataToFirestore("gas", data);
    }


    // Methods to get data
    public double getHeartRate() {
        return heartRate;
    }
    public double getBloodOx() { return bloodOxygen; }
    public double getBodyTemp() { return bodyTemperature; }
    public double getTempData() { return temperature; }
    public double getPressData() { return pressure; }
    public double getHumidity() { return humidity; }
    public double getGas() { return gas; }


    // Method to send data to Firestore
    private void sendDataToFirestore(String key, double value) {
        Map<String, Object> data = new HashMap<>();
        data.put(key, value);

        db.collection("sensorData")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error adding document", e);
                    }
                });
    }

}
