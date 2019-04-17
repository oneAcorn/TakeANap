package com.su.hang.nap.base;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.su.hang.nap.util.BaseParameterUtil;
import com.su.hang.nap.util.Logger;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Administrator on 2018/4/12.
 */

public class TTSActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTTS();
    }

    private void initTTS() {
        tts = new TextToSpeech(this, this); // 参数Context,TextToSpeech.OnInitListener
        tts.setPitch(0.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
    }

    protected void text2Speech(String text) {
        text2Speech(text, true);
    }

    protected void text2Speech(String text, boolean breakSpeaking) {
        if (tts == null) {
            return;
        }
        if (tts.isSpeaking()) {
            if (breakSpeaking) {
                tts.stop();
            } else {
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = new Bundle();
            bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_ALARM);
            bundle.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, BaseParameterUtil.getInstance().getMusicVolumeRate(this));
            tts.speak(text,
                    TextToSpeech.QUEUE_FLUSH, bundle, "");
        } else {
            HashMap<String, String> myHashAlarm = new HashMap();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_ALARM));
//        Logger.d(BaseParameterUtil.getInstance().getCallColumeRate(this)+"");
            //音量
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_VOLUME,
                    BaseParameterUtil.getInstance().getMusicVolumeRate(this) + "");
            //语调
//        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_PAN,"0");
            tts.speak(text,
                    TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.stop(); // 不管是否正在朗读TTS都被打断
        tts.shutdown(); // 关闭，释放资源
    }

    /**
     * 用来初始化TextToSpeech引擎
     * status:SUCCESS或ERROR这2个值
     * setLanguage设置语言，帮助文档里面写了有22种
     * TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失。
     * TextToSpeech.LANG_NOT_SUPPORTED:不支持
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                baseToast.showToast("数据丢失或不支持");
            }
        }
    }
}
