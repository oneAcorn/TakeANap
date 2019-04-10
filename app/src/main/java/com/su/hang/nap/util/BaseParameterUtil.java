//package com.su.hang.nap;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.support.v4.app.ActivityCompat;
//import android.telephony.TelephonyManager;
//import android.text.TextUtils;
//
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.LineNumberReader;
//
///**
// * Created by Administrator on 2017/11/11.
// */
//
//public class BaseParameterUtil {
//    private Context context;
//    private PackageManager manager;
//    private PackageInfo info;
//    private TelephonyManager tm;
//    public String EMPTY = "empty";
//
//    private BaseParameterUtil(Context context) {
//        this.context = context;
//        init();
//    }
//
//    private static volatile BaseParameterUtil instance = null;
//
//    public static BaseParameterUtil getInstance(Context context) {
//        if (instance == null) {
//            //线程锁定
//            synchronized (BaseParameterUtil.class) {
//                //双重锁定
//                if (instance == null) {
//                    instance = new BaseParameterUtil(context);
//                }
//            }
//        }
//        return instance;
//    }
//
//    private void init() {
//        try {
//            manager = context.getPackageManager();
//            info = manager.getPackageInfo(context.getPackageName(), 0);
//            tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getAppVersionName() {
//        if (null == manager || null == info) {
//            init();
//        }
//        if (TextUtils.isEmpty(info.versionName)) {
//            return EMPTY;
//        }
//        return info.versionName;
//    }
//
//    public int getAppVersionCode() {
//        if (null == manager || null == info) {
//            init();
//        }
//        return info.versionCode;
//    }
//
//    public String getDevice() {
//        String device = SharedPreferencesUtils.getSharedPreferencesData(context,
//                ShareKeys.DEVICE_KEY);
//        if (TextUtils.isEmpty(device)) {
//            device = DeviceIDUtil.getDeviceID(context);
//            if (TextUtils.isEmpty(device)) {
//                return EMPTY;
//            }
//            SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.DEVICE_KEY,
//                    device);
//        }
//        return device;
//    }
//
//    public String getDeviceToken() {
//        String deviceToken = SharedPreferencesUtils.getSharedPreferencesData(context,
//                ShareKeys.DEVICE_TOKEN_KEY);
//        if (TextUtils.isEmpty(deviceToken)) {
//            deviceToken = PushAgent.getInstance(context).getRegistrationId();
//            if (TextUtils.isEmpty(deviceToken)) {
//                return EMPTY;
//            }
//            SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.DEVICE_TOKEN_KEY,
//                    deviceToken);
//        }
//        return deviceToken;
//    }
//
//    public void saveDeviceToken(String token) {
//        SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.DEVICE_TOKEN_KEY,
//                token);
//    }
//
//    public String getSystemVersion() {
//        if (TextUtils.isEmpty(android.os.Build.VERSION.RELEASE)) {
//            return EMPTY;
//        }
//        return android.os.Build.VERSION.RELEASE;
//    }
//
//    public String getPhoneModel() {
//        if (TextUtils.isEmpty(android.os.Build.MODEL)) {
//            return EMPTY;
//        }
//        return android.os.Build.MODEL;
//    }
//
//    public String getSource() {
//        return "ANDROID";
//    }
//
//    public String getIMEI() {
//        if (null == tm) {
//            init();
//        }
//        String imei = SharedPreferencesUtils.getSharedPreferencesData(context,
//                ShareKeys.IMEI_KEY);
//        if (TextUtils.isEmpty(imei)) {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return EMPTY;
//            }
//            imei = tm.getDeviceId();
//            if (TextUtils.isEmpty(imei)) {
//                return EMPTY;
//            }
//            SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.IMEI_KEY,
//                    imei);
//        }
//        return imei;
//    }
//
//    public String getMAC() {
//        if (null == tm) {
//            init();
//        }
//        String mac = SharedPreferencesUtils.getSharedPreferencesData(context,
//                ShareKeys.MAC_KEY);
//        if (TextUtils.isEmpty(mac)) {
//            mac = getMac();
//            if (TextUtils.isEmpty(mac)) {
//                return EMPTY;
//            }
//            SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.MAC_KEY,
//                    mac);
//        }
//        return mac;
//    }
//
//    private String getMac() {
//        String macSerial = null;
//        String str = "";
//
//        try {
//            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
//            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//
//            for (; null != str; ) {
//                str = input.readLine();
//                if (str != null) {
//                    macSerial = str.trim();// ȥ�ո�
//                    break;
//                }
//            }
//        } catch (IOException ex) {
//            // ����Ĭ��ֵ
//            ex.printStackTrace();
//        }
//        return macSerial;
//    }
//}
