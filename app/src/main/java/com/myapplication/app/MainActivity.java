package com.myapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
private ScrollView x,y;
private TextView z,k;
private SensorManager sensorManager;
private Sensor accelerometer;
private boolean SensorAvailable,NotFirstTime;
private float X,Y,Z;
private float CurrentX,CurrentY,CurrentZ;
private float xdiff,ydiff,zdiff;
private float minShake=5f;
private Vibrator vibrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x=findViewById(R.id.scrollView3);
        y = findViewById(R.id.scrollView2);
        z=findViewById(R.id.textView);
        k=findViewById(R.id.textView2);
        vibrate= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
     sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
     if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
         accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
  SensorAvailable=true;
     }
     else{
         SensorAvailable=false;
     }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SensorAvailable){
            sensorManager.registerListener( this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(SensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
 CurrentX=sensorEvent.values[0];
        CurrentY=sensorEvent.values[1];
        CurrentZ=sensorEvent.values[2];
       if(NotFirstTime){
           xdiff=Math.abs(X-CurrentX);
           zdiff=Math.abs(Z-CurrentZ);
           ydiff=Math.abs(Y-CurrentY);
           if((xdiff>minShake && ydiff>minShake)||(ydiff>minShake && zdiff>minShake)||(xdiff>minShake && zdiff>minShake)){
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   vibrate.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
               x.setBackgroundColor(Color.RED);
               y.setBackgroundColor(Color.BLUE);
               z.setText("Colours Swapped");
               k.setText("Task Completed");

               }

           }
       }
        X=CurrentX;
        Y=CurrentY;
        Z=CurrentZ;
        NotFirstTime=true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}