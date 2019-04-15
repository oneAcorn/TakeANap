package com.su.hang.nap.business;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.su.hang.nap.R;
import com.su.hang.nap.base.TTSActivity;
import com.su.hang.nap.bean.ParameterBean;
import com.su.hang.nap.configure.ShareKeys;
import com.su.hang.nap.receiver.AlarmReceiver;
import com.su.hang.nap.util.ShareObjUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends TTSActivity {
    private SeekBar mSeekBar;
    private EditText et;
    private TextView timeSpaceTv;
    private EditText speakTimesEt;
    private TextView timeSpanStartTv, timeSpanEndTv;
    private ParameterBean alarmBean;

    private String alarmTip;
    private TextView statusTv;
    private long endStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmBean = (ParameterBean) ShareObjUtil.getObject(this, ShareKeys.PARAMETER_BEAN);
        initUI(alarmBean);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String whatever) {
        if (null != alarmBean && System.currentTimeMillis() > endStamp) {
            cancelAlarm(null);
            addAlarm(alarmBean, 1);
            return;
        }
        text2Speech(TextUtils.isEmpty(alarmTip) ? et.getText().toString() : alarmTip);
        statusTv.setText(statusTv.getText().toString() + ",提醒 " + getCurrentTime());
    }

    @Override
    public void onInit(int status) {
        super.onInit(status);
    }

    public void setAlarm(View view) {
        ensureAlarmBean();
        addAlarm(alarmBean, 0);
    }

    private void ensureAlarmBean() {
        if (alarmBean == null)
            alarmBean = new ParameterBean();
        alarmBean.setTimeStart(timeSpanStartTv.getText().toString());
        alarmBean.setTimeEnd(timeSpanEndTv.getText().toString());
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
    }

    private long getTimeStamp(String timeHHmm, int delayDay) {
        if (TextUtils.isEmpty(timeHHmm) || !timeHHmm.contains(":"))
            return 0l;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        if (delayDay != 0)
            calendar.add(Calendar.DAY_OF_MONTH, delayDay);
        String dateStr = String.format("%s-%s-%s %s", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), timeHHmm);
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        long res = 0;
        try {
            date = sdr.parse(dateStr);
            res = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private String getCurrentTime() {
        return getTimeFromStamp(System.currentTimeMillis());
    }

    private String getTimeFromStamp(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    public void cancelAlarm(View view) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        statusTv.setText(statusTv.getText().toString() + ",取消 " + getCurrentTime());
    }

    private void addAlarm(ParameterBean bean, int delayDay) {
        if (null == bean)
            return;
        ShareObjUtil.saveObject(this, bean, ShareKeys.PARAMETER_BEAN);
        Intent intent = new Intent(this, AlarmReceiver.class);
//        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        Calendar calendar = Calendar.getInstance();
        long startMillis = getTimeStamp(bean.getTimeStart(), delayDay);
        if (startMillis < System.currentTimeMillis()) {
            startMillis = getTimeStamp(bean.getTimeStart(), delayDay + 1);
        }
        calendar.setTimeInMillis(startMillis);
        long endMillis = getTimeStamp(alarmBean.getTimeEnd(), delayDay);
        if (endMillis < startMillis) {
            endMillis = getTimeStamp(alarmBean.getTimeEnd(), delayDay + 1);
        }
        this.endStamp = endMillis;
//        calendar.add(Calendar.SECOND, sec);
        //注册新提醒
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //intervalMillis 毫秒 最小值1分钟?
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), bean.getIntervalMinutes() * 60 * 1000, pendingIntent);

        Toast.makeText(this, "设置成功", Toast.LENGTH_LONG).show();
        statusTv.setText(statusTv.getText().toString() + ",设置从 " + getTimeFromStamp(startMillis) + " 到 " + getTimeFromStamp(endMillis));
    }

    private void initUI(ParameterBean mParameterBean) {
        et = findViewById(R.id.speak_what_et);
        timeSpaceTv = findViewById(R.id.time_space_title_tv);
        mSeekBar = findViewById(R.id.seekBar);
        speakTimesEt = findViewById(R.id.speak_times_et);
        timeSpanStartTv = findViewById(R.id.time_span_start_tv);
        timeSpanEndTv = findViewById(R.id.time_span_end_tv);
        statusTv = findViewById(R.id.status_tv);

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
            timeSpanStartTv.setText(mParameterBean.getTimeStart());
            timeSpanEndTv.setText(mParameterBean.getTimeEnd());

            if (!TextUtils.isEmpty(mParameterBean.getTip())) {
                et.setText(mParameterBean.getTip());
            }
        }
    }

    public void pickStartTime(final View view) {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                TextView tv = (TextView) view;
                tv.setText((hourOfDay > 9 ? hourOfDay : "0" + hourOfDay) + ":" + (minute > 9 ? minute : "0" + minute));

            }
        }, 0, 0, true).show();
    }

    public void pickEndTime(final View view) {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                TextView tv = (TextView) view;
                tv.setText((hourOfDay > 9 ? hourOfDay : "0" + hourOfDay) + ":" + (minute > 9 ? minute : "0" + minute));
            }
        }, 0, 0, true).show();
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
