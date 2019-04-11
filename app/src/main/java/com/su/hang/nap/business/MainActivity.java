package com.su.hang.nap.business;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.su.hang.nap.R;
import com.su.hang.nap.base.TTSActivity;
import com.su.hang.nap.bean.ParameterBean;
import com.su.hang.nap.configure.ShareKeys;
import com.su.hang.nap.util.RealPhoneUtil;
import com.su.hang.nap.util.ShareObjUtil;
import com.su.hang.nap.util.VibratorUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends TTSActivity
        implements SensorEventListener, View.OnClickListener {
    private EditText testEt;
    private EditText testEt1;
    private EditText testEt2;
    private Button testBtn;
    private ParameterBean mParameterBean;
    private SimpleDateFormat format;
    private Date mDate;
    private boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        format = new SimpleDateFormat("HH:mm");
        mDate = new Date();
        mParameterBean = (ParameterBean) ShareObjUtil.getObject(this, ShareKeys.PARAMETER_BEAN);
        initUI();
        refreshUI();
//        test();
//        test1();
//        test2();
//        test3();
    }

    private void initUI() {
        testEt = (EditText) findViewById(R.id.test_et);
        testEt1 = (EditText) findViewById(R.id.test_et1);
        testEt2 = (EditText) findViewById(R.id.test_et2);
        testBtn = (Button) findViewById(R.id.test_btn);

        testBtn.setOnClickListener(this);
    }

    private void refreshUI() {
        testEt.setText(mParameterBean.getTime());
        if (!TextUtils.isEmpty(mParameterBean.getTip())) {
            testEt1.setText(mParameterBean.getTip());
        }
        testEt2.setText(mParameterBean.getVibratorTime() + "");
    }

    private void test() {
        String result = "";
        if (!RealPhoneUtil.checkIsNotRealPhone()) {
            result += "cpu检测成功";
        } else {
            result += "cpu检测失败";
        }
        result += "\n";
        if (!RealPhoneUtil.isFeatures()) {
            result += "设备信息检测成功";
        } else {
            result += "设备信息检测失败";
        }
        result += "\n";
        if (!RealPhoneUtil.notHasLightSensorManager(this)) {
            result += "光感检测成功";
        } else {
            result += "光感检测失败";
        }
        result += "\n";
        if (!RealPhoneUtil.notHasBlueTooth()) {
            result += "蓝牙检测成功";
        } else {
            result += "蓝牙检测失败";
        }
        testEt.setText(result);
    }

    private void test1() {
        String result = "";
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result += "wifiInfo:" + wifiInfo.toString();
        result += "\n";
        result += "SSID:" + wifiInfo.getSSID();
        testEt.setText(result);
    }

    private void test2() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> listb = wifiManager.getScanResults();
        List<WifiConfiguration> lista = wifiManager.getConfiguredNetworks();
        testEt.setText(listb.toString() + "\n" + lista.toString());
    }

    private void test3() {
        SensorManager sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mSensorAccelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        boolean is_gyroscope_exist;
        float gyroscope_x = 0f;
        float gyroscope_y = 0f;
        float gyroscope_z = 0f;
        try {
            gyroscope_x = event.values[0];
            gyroscope_y = event.values[1];
            gyroscope_z = event.values[2];

            is_gyroscope_exist = true;
        } catch (Exception e) {
            is_gyroscope_exist = false;
        }
        testEt.setText("xyz:" + gyroscope_x + "," + gyroscope_y + "," + gyroscope_z + ",\n" + is_gyroscope_exist);
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(testEt.getText())) {
            return false;
        }
        if (TextUtils.isEmpty(testEt1.getText()) && TextUtils.isEmpty(testEt2.getText())) {
            return false;
        }
        return true;
    }

    private void startCountDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!stopThread) {
                    try {
                        Thread.sleep(1000);
                        mDate.setTime(System.currentTimeMillis());
                        String time = format.format(mDate);
                        if (time.equals(mParameterBean.getTime())) {
                            if (!TextUtils.isEmpty(mParameterBean.getTip())) {
                                text2Speech(mParameterBean.getTip());
                            }
                            if (mParameterBean.getVibratorTime() != 0) {
                                VibratorUtil.Vibrate(MainActivity.this, mParameterBean.getVibratorTime());
                            }
                            stopThread = true;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2019-04-10 16:27:05 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == testBtn) {
            // Handle clicks for testBtn
            if (checkData()) {
                mParameterBean = new ParameterBean();

                mParameterBean.setTime(testEt.getText().toString());
                if (!TextUtils.isEmpty(testEt1.getText().toString())) {
                    mParameterBean.setTip(testEt1.getText().toString());
                }
                if (!TextUtils.isEmpty(testEt2.getText().toString())) {
                    mParameterBean.setVibratorTime(Integer.valueOf(testEt2.getText().toString()));
                }
                ShareObjUtil.saveObject(MainActivity.this, mParameterBean, ShareKeys.PARAMETER_BEAN);
                stopThread = false;
                startCountDown();
            }
        }
    }
}
