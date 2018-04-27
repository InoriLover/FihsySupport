package fishy.support.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.JsonToken;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 手机设备的常用信息的获取工具类
 * <div>
 * <p>使用需要权限</p>
 * {@link android.Manifest.permission#READ_PHONE_STATE}
 * {@link android.Manifest.permission#READ_SMS}
 * {@link android.Manifest.permission#READ_PHONE_NUMBERS}
 * </div>
 * <h3>
 * 编辑记录
 * </h3>
 * <p>
 * 第一次完成<br/>
 * edited by shallowFish on 2018/4/27
 * </p>
 *
 * @author ShallowFish
 * @version 1.0.0
 */

public class DeviceInfoUtil {
    /**
     * 获得手机号码
     * <p>使用需要权限</p>
     * {@link android.Manifest.permission#READ_PHONE_STATE}
     * {@link android.Manifest.permission#READ_SMS}
     * {@link android.Manifest.permission#READ_PHONE_NUMBERS}
     *
     * @param context 上下文环境
     * @return 手机号码
     */
    public static String getMobileNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String number = telephonyManager.getLine1Number();
        return number;
    }

    /**
     * 获得手机IMEI
     * <p>使用需要权限</p>
     * {@link android.Manifest.permission#READ_PHONE_STATE}
     *
     * @param context 上下文环境
     * @return 手机IMEI
     */
    public static String getMobileImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 获得手机型号
     *
     * @return 手机型号
     */
    public static String getMobileType() {
        return Build.MODEL;
    }

    /**
     * 获得手机系统版本
     * @return 版本号int+float
     */
    public static String getMobileOSVersion() {
        return Build.VERSION.SDK_INT + "(" + Build.VERSION.RELEASE + ")";
    }

    /**
     * 得到手机的尺寸Metrics
     *
     * @param context
     * @return
     */
    private static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 得到设备的Dpi
     *
     * @param context
     * @return 设备的dpi
     */
    public static int getDeviceDpi(Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return metrics.densityDpi;
    }

    /**
     * 得到设备的宽，像素为单位
     *
     * @param context
     * @return 宽(像素)
     */
    public static int getDeviceWidth(Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return metrics.widthPixels;
    }

    /**
     * 得到设备的高，像素为单位
     *
     * @param context
     * @return 高(像素)
     */
    public static int getDeviceHeight(Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return metrics.heightPixels;
    }

    /**
     * 得到设备的Dpi缩放比
     *
     * @param context
     * @return 缩放比
     */
    public static float getDeviceDpiScale(Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return metrics.scaledDensity;
    }

    /**
     * 得到所有的设备信息
     * 将所有的共有信息包裹成json对象输出
     *
     * @return json格式的字符串，反馈所有的信息
     */
    public static String getAllDeviceInfo(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobileNumber", getMobileNumber(context));
            jsonObject.put("imei", getMobileImei(context));
            jsonObject.put("mobileType", getMobileType());
            jsonObject.put("osVersion", getMobileOSVersion());
            jsonObject.put("deviceDpi", getDeviceDpi(context));
            jsonObject.put("deviceWidth", getDeviceWidth(context));
            jsonObject.put("deviceHeight", getDeviceHeight(context));
            jsonObject.put("dpiScale", getDeviceDpiScale(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
