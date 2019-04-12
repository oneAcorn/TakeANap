package com.su.hang.nap.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;


/**
 * Created by Administrator on 2017/11/11.
 */

public class BaseParameterUtil {
    private PackageManager manager;
    private PackageInfo info;
    private TelephonyManager tm;
    public String EMPTY = "empty";

    private BaseParameterUtil() {

    }

    private static volatile BaseParameterUtil instance = null;

    public static BaseParameterUtil getInstance() {
        if (instance == null) {
            //线程锁定
            synchronized (BaseParameterUtil.class) {
                //双重锁定
                if (instance == null) {
                    instance = new BaseParameterUtil();
                }
            }
        }
        return instance;
    }

    private void init(Context context) {
        try {
            manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(), 0);
            tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getAppVersionName(Context context) {
        if (null == manager || null == info) {
            init(context);
        }
        if (TextUtils.isEmpty(info.versionName)) {
            return EMPTY;
        }
        return info.versionName;
    }

    public int getAppVersionCode(Context context) {
        if (null == manager || null == info) {
            init(context);
        }
        return info.versionCode;
    }

//    public String getChannel(Context context) {
//        if (TextUtils.isEmpty(SharedPreferencesUtils.getSharedPreferencesData(context, ShareKeys.CHANNEL_KEY))) {
//            SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.CHANNEL_KEY, AnalyticsConfig.getChannel(context));
//            return AnalyticsConfig.getChannel(context);
//        } else {
//            return SharedPreferencesUtils.getSharedPreferencesData(context, ShareKeys.CHANNEL_KEY);
//        }
//    }
//
//    //UUID
//    public String getDevice(Context context) {
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
//    public String getDeviceToken(Context context) {
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
//    public void saveDeviceToken(Context context, String token) {
//        SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.DEVICE_TOKEN_KEY,
//                token);
//    }

    //App版本
    public String getSystemVersion() {
        if (TextUtils.isEmpty(android.os.Build.VERSION.RELEASE)) {
            return EMPTY;
        }
        return android.os.Build.VERSION.RELEASE;
    }

    //系统版本号
    public String getPhoneModel() {
        if (TextUtils.isEmpty(android.os.Build.MODEL)) {
            return EMPTY;
        }
        return android.os.Build.MODEL;
    }

    public String getSource() {
        return "ANDROID";
    }

//    public String getIMEI(Context context) {
//        if (null == tm) {
//            init(context);
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
//    public String getLastStartTime(Context context) {
//        String time = EMPTY;
//        time = SharedPreferencesUtils.getSharedPreferencesData(context, ShareKeys.LAST_START_TIME_KEY);
//        if (TextUtils.isEmpty(time)) {
//            time = EMPTY;
//        }
//        String currentTime = DateTranslateUtil.
//                getFormatedDateTime
//                        ("yyyy-MM-dd HH:mm:ss", DateTranslateUtil.getCurrentDateSeconds());
//        SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.LAST_START_TIME_KEY, currentTime);
//        return time;
//    }
//
//    public String getMAC(Context context) {
//        if (null == tm) {
//            init(context);
//        }
//        String mac = SharedPreferencesUtils.getSharedPreferencesData(context,
//                ShareKeys.MAC_KEY);
//        if (TextUtils.isEmpty(mac)) {
//            mac = getMacByVersion(context);
//            if (TextUtils.isEmpty(mac)) {
//                return EMPTY;
//            }
//            SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.MAC_KEY,
//                    mac);
//        }
//        return mac;
//    }

    private String getMacByVersion(Context context) {
        String strMac = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.e("=====", "6.0以下");
            strMac = getLocalMacAddressFromWifiInfo(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("=====", "6.0以上7.0以下");
            strMac = getMacAddress(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("=====", "7.0以上");
            if (!TextUtils.isEmpty(getMacAddress())) {
                Log.e("=====", "7.0以上1");
                strMac = getMacAddress();
                return strMac;
            } else if (!TextUtils.isEmpty(getMachineHardwareAddress())) {
                Log.e("=====", "7.0以上2");
                strMac = getMachineHardwareAddress();
                return strMac;
            } else {
                Log.e("=====", "7.0以上3");
                strMac = getLocalMacAddressFromBusybox();
                return strMac;
            }
        }

        return "02:00:00:00:00:00";
    }

    /**
     * 根据wifi信息获取本地mac
     *
     * @param context
     * @return
     */
    private String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String mac = winfo.getMacAddress();
        return mac;
    }

    /**
     * android 6.0及以上、7.0以下 获取mac地址
     *
     * @param context
     * @return
     */
    private String getMacAddress(Context context) {

        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress0 = getMacAddress0(context);
            if (!TextUtils.isEmpty(macAddress0)) {
                return macAddress0;
            }
        }
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            Log.e("----->" + "NetInfoManager", "getMacAddress:" + ex.toString());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress:" + e.toString());
            }

        }
        return macSerial;
    }

    private String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress0:" + e.toString());
            }

        }
        return "";

    }

    /**
     * Check whether accessing wifi state is permitted
     *
     * @param context
     * @return
     */
    private boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:"
                    + "access wifi state is enabled");
            return true;
        } else
            return false;
    }

    private String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    private String getMacAddress() {
        String strMacAddr = null;
        try {
            // 获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
        }
        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface
                    .getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface
                        .nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取本地IP
     *
     * @return
     */
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
/**
 * android 7.0及以上 （2）扫描各个网络接口获取mac地址
 *
 */
    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    private String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress != null)
                    break;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }
/**
 * android 7.0及以上 （3）通过busybox获取本地存储的mac地址
 *
 */

    /**
     * 根据busybox获取本地Mac
     *
     * @return
     */
    private String getLocalMacAddressFromBusybox() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");
        // 如果返回的result == null，则说明网络不可取
        if (result == null) {
            return "网络异常";
        }
        // 对该行数据进行解析
        // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            Mac = result.substring(result.indexOf("HWaddr") + 6,
                    result.length() - 1);
            result = Mac;
        }
        return result;
    }

    private String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            while ((line = br.readLine()) != null
                    && line.contains(filter) == false) {
                result += line;
            }

            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return EMPTY;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 分辨率
     */
    public String getSize(Activity context) {
        Point point = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(point);
        return point.toString();
    }

    /**
     * 屏幕尺寸
     *
     * @param context
     * @return
     */
    public String getScreenSizeOfDevice2(Activity context) {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.getWindowManager().getDefaultDisplay().getRealSize(point);

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            double x = Math.pow(point.x / dm.xdpi, 2);
            double y = Math.pow(point.y / dm.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            return screenInches + "";
        } else {
            return EMPTY;
        }
    }

    // 获得可用的内存
    public long getmem_UNUSED(Context mContext) {
        long MEM_UNUSED;
        // 得到ActivityManager
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        // 创建ActivityManager.MemoryInfo对象

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        // 取得剩余的内存空间

        MEM_UNUSED = mi.availMem / 1024 / 1024 / 1024;
        return MEM_UNUSED;
    }

    // 获得总内存
    public long getmem_TOLAL() {
        long mTotal;
        // /proc/meminfo读出的内核信息进行解释
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');
        // 截取字符串信息

        content = content.substring(begin + 1, end).trim();
        mTotal = Integer.parseInt(content);
        return mTotal / 1024 / 1024;
    }

    /**
     * 判断sd卡是否可用
     */
    public boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public String getInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSizeLong = statFs.getBlockSizeLong();
        }
        long blockCountLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockCountLong = statFs.getBlockCountLong();
        }
        long size = blockCountLong * blockSizeLong;
        return Formatter.formatFileSize(context, size);
    }

    /**
     * 获取手机内部可用存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public String getAvailableInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long availableBlocksLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocksLong = statFs.getAvailableBlocksLong();
        }
        long blockSizeLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSizeLong = statFs.getBlockSizeLong();
        }
        return Formatter.formatFileSize(context, availableBlocksLong
                * blockSizeLong);
    }

    /**
     * 获取手机外部存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public String getExternalMemorySize(Context context) {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSizeLong = statFs.getBlockSizeLong();
        }
        long blockCountLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockCountLong = statFs.getBlockCountLong();
        }
        return Formatter
                .formatFileSize(context, blockCountLong * blockSizeLong);
    }

    /**
     * 获取手机外部可用存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public String getAvailableExternalMemorySize(Context context) {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long availableBlocksLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocksLong = statFs.getAvailableBlocksLong();
        }
        long blockSizeLong = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSizeLong = statFs.getBlockSizeLong();
        }
        return Formatter.formatFileSize(context, availableBlocksLong
                * blockSizeLong);
    }

    /**
     * cpu
     *
     * @return
     */
    public String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        return result;
    }

    public String getCpuType() {
        Object[] objects = getCpuArchitecture();
        String res = "";
        for (int i = 0; i < objects.length; i++) {
            if (null != objects[i]) {
                res += objects[i].toString() + ";";
            }
        }
        return res;
    }

    public Object[] getCpuArchitecture() {
        Object[] mArmArchitecture = new Object[5];
        try {
            InputStream is = new FileInputStream("/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            try {
                String nameProcessor = "Processor";
                String nameFeatures = "Features";
                String nameModel = "model name";
                String nameCpuFamily = "cpu family";
                while (true) {
                    String line = br.readLine();
                    String[] pair = null;
                    if (line == null) {
                        break;
                    }
                    pair = line.split(":");
                    if (pair.length != 2)
                        continue;
                    String key = pair[0].trim();
                    String val = pair[1].trim();
                    if (key.compareTo(nameProcessor) == 0) {
                        String n = "";
                        if (val.indexOf("AArch64") >= 0) {
                            mArmArchitecture[0] = "aarch64";
                            mArmArchitecture[1] = 64;
                            continue;
                        } else {

                            for (int i = val.indexOf("ARMv") + 4; i < val.length(); i++) {
                                String temp = val.charAt(i) + "";
                                if (temp.matches("\\d")) {
                                    n += temp;
                                } else {
                                    break;
                                }
                            }
                        }
                        mArmArchitecture[0] = "ARM";
                        mArmArchitecture[1] = Integer.parseInt(n);
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameFeatures) == 0) {
                        if (val.contains("neon")) {
                            mArmArchitecture[2] = "neon";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameModel) == 0) {
                        if (val.contains("Intel")) {
                            mArmArchitecture[0] = "INTEL";
                            mArmArchitecture[2] = "atom";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameCpuFamily) == 0) {
                        mArmArchitecture[1] = Integer.parseInt(val);
                        continue;
                    }
                }
            } finally {
                br.close();
                ir.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mArmArchitecture;
    }

    public int getBattery(Activity context, int id) {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(context.BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return batteryManager.getIntProperty(id);
        } else {
            return 0;
        }
    }

    /**
     * 判断蓝牙是否有效来判断是否为模拟器
     *
     * @return true 为模拟器
     */
    public boolean notHasBlueTooth() {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            if (TextUtils.isEmpty(name)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 判断蓝牙是否有效来判断是否为模拟器
     *
     * @return true 为模拟器
     */
    @SuppressLint("MissingPermission")
    public boolean getBlueToothState() {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return false;
        } else {
            return ba.isEnabled();
        }
    }

    /**
     * 获取系统语言设置
     *
     * @return
     */
    public String getLanguage(Activity context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    /**
     * 有无光感
     *
     * @param context
     * @return
     */
    public boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (null == sensor8) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 返回手机运营商名称，在调用支付前调用作判断
//     *
//     * @return
//     */
//    public String getProvidersName(Context context) {
//        String ProvidersName = "";
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return "permission reject";
//        }
//        String IMSI = telephonyManager.getSubscriberId();
//        if (IMSI == null) {
//            return "unknow";
//        }
//
//        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
//            ProvidersName = "China_mobile";
//        } else if (IMSI.startsWith("46001")) {
//            ProvidersName = "China_telecom";
//        } else if (IMSI.startsWith("46003")) {
//            ProvidersName = "China_unicom";
//        }
//
////        try {
////            ProvidersName = URLEncoder.encode("" + ProvidersName, "UTF-8");
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        }
////        Log.e("TAG_IMSI", "==== 当前卡为：" + ProvidersName);
//        return ProvidersName;
//    }

//    /**
//     * 返回手机运营商编号，在调用支付前调用作判断
//     *
//     * @return
//     */
//    public String getProvidersCode(Context context) {
//        String ProvidersName = "";
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return "permission reject";
//        }
//        String IMSI = telephonyManager.getSubscriberId();
//        if (IMSI == null) {
//            return "unknow";
//        }
//
//        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
//            ProvidersName = "0";
//        } else if (IMSI.startsWith("46001")) {
//            ProvidersName = "2";
//        } else if (IMSI.startsWith("46003")) {
//            ProvidersName = "1";
//        }
//
////        try {
////            ProvidersName = URLEncoder.encode("" + ProvidersName, "UTF-8");
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        }
////        Log.e("TAG_IMSI", "==== 当前卡为：" + ProvidersName);
//        return ProvidersName;
//    }

    @SuppressLint("NewApi")
    public boolean isNotificationEnabled(Context context) {
        final String CHECK_OP_NO_THROW = "checkOpNoThrow";
        final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String handleActivityName(String str) {
        try {
            String res = str;
            int dotPosition = str.lastIndexOf(".");
            res = res.substring(dotPosition + 1, res.length());
            res = res.replaceAll("Activity", "");
            return res;
        } catch (Exception e) {
            return str;
        }
    }

    // 获取双卡双待 SIM 卡序列号
    @SuppressLint("MissingPermission")
    public String getSubscriberId(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyClass;
        Object result = null;
        Object result0 = null;
//        Object result1 = null;
        try {
            telephonyClass = Class.forName(telephony.getClass().getName());
            Method m1 = telephonyClass.getMethod("getSubscriberId");
            result = m1.invoke(telephony);
            Method m2 = telephonyClass.getMethod("getSubscriberId", new Class[]{int.class});
            result0 = m2.invoke(telephony, 0);
//            result1 = m2.invoke(telephony, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String sim1 = (String) result;
            String sim2 = (String) result0;
            if (!TextUtils.isEmpty(sim1) && !TextUtils.isEmpty(sim2)) {
                if (sim2.indexOf(sim1) != -1) {
                    sim2 = sim2.replace(sim1, "");
                }
                if (sim2.length() >= 30) {
                    String str = sim2;
                    sim2 = sim2.substring(0, 15);
                    str = str.substring(15, 30);
                    if (sim2.equals(str)) {
                        return sim1 + "|" + sim2;
                    } else {
                        return sim1 + "|" + sim2;
                    }
                } else {
                    return sim1 + "|" + sim2;
                }
            } else if (!TextUtils.isEmpty(sim1)) {
                return sim1;
            } else if (!TextUtils.isEmpty(sim2)) {
                if (sim2.length() >= 30) {
                    String str = sim2;
                    sim2 = sim2.substring(0, 15);
                    str = str.substring(15, 30);
                    if (sim2.equals(str)) {
                        return sim2;
                    } else {
                        return sim2 + "|" + str;
                    }
                } else {
                    return sim2;
                }
            } else {
                return EMPTY;
            }
        } catch (IndexOutOfBoundsException e) {
            return EMPTY;
        } catch (NullPointerException e) {
            return EMPTY;
        }
    }

    public String getVoiceStatus(Context context) {//获取手机设置声音模式
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int mode = audioManager.getRingerMode();
        switch (mode) {
            case AudioManager.RINGER_MODE_NORMAL:
                //普通模式2
                return AudioManager.RINGER_MODE_NORMAL + "";
            case AudioManager.RINGER_MODE_VIBRATE:
                // 振动模式1
                return AudioManager.RINGER_MODE_VIBRATE + "";
            case AudioManager.RINGER_MODE_SILENT:
                //静音模式0
                return AudioManager.RINGER_MODE_SILENT + "";
        }
        return "000";
    }

    public int getCallColume(Context context) {//获取通话音量
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);//最大
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);//当前
        Log.d("aaaaaaaa", "max : " + max + " current : " + current);
        return current;
    }
    public float getCallColumeRate(Context context) {//获取通话音量
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);//最大
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);//当前
        Log.d("aaaaaaaa", "max : " + max + " current : " + current);
        return Float.valueOf(current)/Float.valueOf(max);
    }
    public String getHeadPhoneStatus(Context context) {//获取耳机状态
        AudioManager localAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        localAudioManager.isWiredHeadsetOn();
        if (localAudioManager.isWiredHeadsetOn()) {
            return "1";
        } else {
            return "2";
        }
    }

//    //获取几张SIM卡在手机里
//    public String getSIMCount(Context context) {
//        String[] perms = {Manifest.permission.READ_PHONE_STATE};
//        if (EasyPermissions.hasPermissions(context, perms)) {
//            String operator1 = SimUtils.getSimOperatorName(context, 0);
//            String operator2 = SimUtils.getSimOperatorName(context, 1);
//            if (!TextUtils.isEmpty(operator1) && !TextUtils.isEmpty(operator2)) {
//                return "2";
//            } else if (!TextUtils.isEmpty(operator1) || !TextUtils.isEmpty(operator2)) {
//                return "1";
//            } else {
//                return "0";
//            }
//        } else {
//            return EMPTY;
//        }
//    }

    /**
     * 获取电池容量 mAh
     * <p>
     * 源头文件:frameworks/base/core/res\res/xml/power_profile.xml
     * <p>
     * Java 反射文件：frameworks\base\core\java\com\android\internal\os\PowerProfile.java
     */
    public String getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(batteryCapacity + " mAh");
    }

    //单位:kb
    public int getNetSpeed() {//获取当前网速
        long total_data = TrafficStats.getTotalRxPackets();
        total_data = total_data / 1024L;
        return (int) total_data;
    }

    public String getAppInfo(Context context) {//获取app安装应用
        JSONArray jsonArray = new JSONArray();
        JSONObject tmpObj = null;
//        获取全部应用：
        PackageManager packageManager = context.getPackageManager();
        Intent mIntent = new Intent(Intent.ACTION_MAIN, null);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> listAllApps = packageManager.queryIntentActivities(mIntent, 0);

        ResolveInfo appInfo;
        String pkgName, appName;
        PackageInfo mPackageInfo;
        for (int i = 0; i < listAllApps.size(); i++) {
            appInfo = listAllApps.get(i);
            pkgName = appInfo.activityInfo.packageName;//获取包名
            appName = appInfo.loadLabel(packageManager).toString();
            //根据包名获取（需要处理异常）
            try {

                mPackageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
                if ((mPackageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    //第三方应用
                    tmpObj = new JSONObject();
                    try {
                        tmpObj.put("app_name", appName);
                        tmpObj.put("app_package", pkgName);
                        jsonArray.put(tmpObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tmpObj = null;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

//    public String getSmsFromPhone(Context context) {//获取短信内容
//        JSONArray jsonArray = new JSONArray();
//        JSONObject tmpObj = null;
//        Uri SMS_INBOX = Uri.parse("content://sms/");
//        ContentResolver cr = context.getContentResolver();
//        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type", "type"};
//        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
//        if (null == cur) {
//            return EMPTY;
//        }
//        while (cur.moveToNext()) {
//            String number = cur.getString(cur.getColumnIndex("address"));//手机号
//            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
//            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
//            String type = cur.getString(cur.getColumnIndex("type"));//短信类型1是接收到的，2是已发出
//            //这个必须用long int不够长
//            long date = cur.getLong(cur.getColumnIndex("date"));//时间
////            String str = DateUtil.getCurDate("yyyy-MM-dd");
////            long todayDate = DateUtil.getStringToDate(str, "yyyy-MM-dd");
//            try {
////                if(date > todayDate){
//                tmpObj = new JSONObject();
//                tmpObj.put("mobile", number);
//                tmpObj.put("content", body);
//                tmpObj.put("send_time", DateTranslateUtil.formatDate(date));
//                tmpObj.put("status", type);
//                jsonArray.put(tmpObj);
////                }else{
////                    return;
////                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            tmpObj = null;
//        }
//        return jsonArray.toString();
//    }

//    /**
//     * 获取 基站 信息
//     *
//     * @return
//     */
//    public StationInfoBean getBaseStationInformation(Context context) {
//        StationInfoBean result = new StationInfoBean();
//        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,};
//        if (EasyPermissions.hasPermissions(context, perms)) {
//            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            String operator = tManager.getNetworkOperator();
//            if (null != operator) {
//                result.setNetworkOperator(operator);
//            } else {
//                result.setNetworkOperator(EMPTY);
//            }
//
//            if (tManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
//                @SuppressLint("MissingPermission") CdmaCellLocation cdmaCellLocation = (CdmaCellLocation)
//                        tManager.getCellLocation();
//                if (null != cdmaCellLocation) {
//                    result.setBaseStationId(cdmaCellLocation.getBaseStationId() + "");
//                    result.setNetworkId(cdmaCellLocation.getNetworkId() + "");
//                    result.setSystemId(cdmaCellLocation.getSystemId() + "");
//                }
//
//            } else {
//                @SuppressLint("MissingPermission") GsmCellLocation gsmCellLocation = (GsmCellLocation) tManager.getCellLocation();
//                if (null != gsmCellLocation) {
//                    result.setCid(gsmCellLocation.getCid() + "");
//                    result.setLac(gsmCellLocation.getLac() + "");
//                }
//            }
//        }
//        return result;
//    }
//
//    //每次都记录 最后一次记录的一定会是程序关闭的时间
//    public void saveCloseTime(Context context) {
//        SharedPreferencesUtils.setSharedPreferencesData(context, ShareKeys.LAST_CLOSE_APP_TIME,
//                DateTranslateUtil.
//                        getFormatedDateTime
//                                ("yyyy-MM-dd HH:mm:ss", DateTranslateUtil.getCurrentDateSeconds()));
//    }
}
