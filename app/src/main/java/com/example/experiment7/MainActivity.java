package com.example.experiment7;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {
    //感应器管理器
    private SensorManager sensorManager;
    //光线亮度
    private TextView mtextView1;
    private TextView mtextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtextView1 = (TextView) findViewById(R.id.textView1);
        mtextView2 = (TextView) findViewById(R.id.textView2);


        //获得感应器服务
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listener, sensor, SensorManager. SENSOR_DELAY_NORMAL);
        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(listener, sensor1, SensorManager. SENSOR_DELAY_NORMAL);
    }
    //Activity被销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销监听器
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
    //感应器事件监听器
    private SensorEventListener listener = new SensorEventListener() {
        //当感应器精度发生变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        //当传感器监测到的数值发生变化时
        @Override
        public void onSensorChanged(SensorEvent event) {
            // values数组中第一个值就是当前的光照强度
            if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
                float value = event.values[0];
                mtextView1.setText("\n当前亮度 " + value + " lx(勒克斯)\n");
            }
            else if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
                float X_lateral = event.values[0];
                float Y_longitudinal = event.values[1];
                float Z_vertical = event.values[2];
                mtextView2.setText("绕z轴转过的角度"+ X_lateral+"\n绕x轴转过的角度"+ Y_longitudinal +"\n绕y轴转过的角度"+ Z_vertical+"\n");
            }

        }
    };
}