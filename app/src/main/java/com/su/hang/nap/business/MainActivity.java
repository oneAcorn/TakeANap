package com.su.hang.nap.business;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.su.hang.nap.R;
import com.su.hang.nap.base.TTSActivity;
import com.su.hang.nap.bean.ParameterBean;
import com.su.hang.nap.configure.ShareKeys;
import com.su.hang.nap.receiver.AlarmReceiver;
import com.su.hang.nap.util.ShareObjUtil;

import java.util.Calendar;

public class MainActivity extends TTSActivity {
    private SeekBar mSeekBar;//
    private EditText et;
    private TextView timeSpaceTv;
    private EditText speakTimesEt;
    private ParameterBean alarmBean;

    private String alarmTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmBean = (ParameterBean) ShareObjUtil.getObject(this, ShareKeys.PARAMETER_BEAN);
        initUI(alarmBean);
    }

    @Override
    public void onInit(int status) {
        super.onInit(status);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        text2Speech(TextUtils.isEmpty(alarmTip) ? et.getText().toString() : alarmTip);
//        Toast.makeText(this,".onNewIntent.........",Toast.LENGTH_LONG).show();
    }


    public void setAlarm(View view) {
        if (alarmBean == null)
            alarmBean = new ParameterBean();
        alarmBean.setIntervalMinutes(mSeekBar.getProgress());
        int times = Integer.parseInt(speakTimesEt.getText().toString());
        alarmBean.setSpeakTimes(times);
        String tip = et.getText().toString();
        alarmBean.setTip(tip);
        if (times > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < times; i++) {
                sb.append(tip);
            }
            alarmTip = sb.toString();
        } else {
            alarmTip = tip;
        }
        addAlarm(10, alarmBean);
        Toast.makeText(this, "fdsjalfas", Toast.LENGTH_LONG).show();
    }

    public void cancelAlarm(View view) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "cancelAlarm", Toast.LENGTH_LONG).show();
    }

    private void addAlarm(int sec, ParameterBean bean) {
        if (null == bean)
            return;
        ShareObjUtil.saveObject(this, bean, ShareKeys.PARAMETER_BEAN);
        Intent intent = new Intent(this, AlarmReceiver.class);
//        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, sec);
        //注册新提醒
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //intervalMillis 毫秒 最小值1分钟?
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), bean.getIntervalMinutes() * 60 * 1000, pendingIntent);
    }

    private void initUI(ParameterBean mParameterBean) {
        et = findViewById(R.id.speak_what_et);
        timeSpaceTv = findViewById(R.id.time_space_title_tv);
        mSeekBar = findViewById(R.id.seekBar);
        speakTimesEt = findViewById(R.id.speak_times_et);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeSpaceTv.setText(String.format("间隔(%s分钟)", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (null != mParameterBean) {
            mSeekBar.setProgress(mParameterBean.getIntervalMinutes());
            speakTimesEt.setText(String.valueOf(mParameterBean.getSpeakTimes()));

            if (!TextUtils.isEmpty(mParameterBean.getTip())) {
                et.setText(mParameterBean.getTip());
            }
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
}
