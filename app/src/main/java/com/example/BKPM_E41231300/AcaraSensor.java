package com.example.BKPM_E41231300;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AcaraSensor extends AppCompatActivity implements SensorEventListener {

    private TextView accelerometerDataTextView;
    private TextView gyroscopeDataTextView;
    private TextView lightDataTextView;
    private TextView magneticFieldDataTextView;
    private TextView proximityDataTextView;

    private SensorManager sensorManager;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara_sensor);

        // Inisialisasi TextView
        accelerometerDataTextView = findViewById(R.id.accelerometer_data);
        gyroscopeDataTextView = findViewById(R.id.gyroscope_data);
        lightDataTextView = findViewById(R.id.light_data);
        magneticFieldDataTextView = findViewById(R.id.magnetic_field_data);
        proximityDataTextView = findViewById(R.id.proximity_data);

        // Inisialisasi sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Inisialisasi media player untuk suara
        mediaPlayer = MediaPlayer.create(this, R.raw.lagu); // Ganti dengan nama file audio Anda
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Daftarkan listener untuk sensor yang ingin digunakan
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (light != null) {
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximity != null) {
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hentikan sensor ketika aplikasi dijeda
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                String accelerometerData = String.format("Accelerometer: X=%.2f, Y=%.2f, Z=%.2f", values[0], values[1], values[2]);
                accelerometerDataTextView.setText(accelerometerData);
                break;
            case Sensor.TYPE_GYROSCOPE:
                String gyroscopeData = String.format("Gyroscope: X=%.2f, Y=%.2f, Z=%.2f", values[0], values[1], values[2]);
                gyroscopeDataTextView.setText(gyroscopeData);
                break;
            case Sensor.TYPE_LIGHT:
                String lightData = String.format("Light: %.2f", values[0]);
                lightDataTextView.setText(lightData);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                String magneticFieldData = String.format("Magnetic Field: X=%.2f, Y=%.2f, Z=%.2f", values[0], values[1], values[2]);
                magneticFieldDataTextView.setText(magneticFieldData);
                break;
            case Sensor.TYPE_PROXIMITY:
                String proximityData = String.format("Proximity: %.2f", values[0]);
                proximityDataTextView.setText(proximityData);
                break;
        }

        // Mainkan suara ketika terjadi perubahan pada sensor
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Tidak digunakan untuk sekarang
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Lepaskan media player ketika tidak digunakan lagi
            mediaPlayer = null;
        }
    }
}
