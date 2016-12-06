package utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Linxj on 2016/11/11.
 * 说明：对权限进行操作
 */

public class PermissionUtils {
    private static OnPermissionRequestCallback mOnPermissionRequestCallback;
    private static int mRequestCode;

    public static boolean isAllPermissionGranded(Context mContext) {
        if (isPreM()) {
            return true;
        }
        if (isTargetPreM(mContext)) {
            return true;
        }
        return checkAllPermissionGranded(mContext).isEmpty();
    }

    /**
     * 判断是否权限都己具备
     */
    public static List<String> checkAllPermissionGranded(Context mContext) {
        PackageInfo packageInfo = getPackageInfo(mContext);
        Set dangerousPermissions = getDangerousPermissions();
        List<String> reMainPermissions = new ArrayList<String>();
        if (packageInfo != null && packageInfo.requestedPermissions != null) {
            for (String permission : packageInfo.requestedPermissions
                    ) {
                if (dangerousPermissions.contains(permission)) {
                    reMainPermissions.add(permission);
                }
            }
        }
        reMainPermissions = filterPermission(mContext, reMainPermissions);
        return reMainPermissions;
    }

    public static List<String> filterPermission(Context mContext, List<String> permissions) {
        List<String> filters = new ArrayList<String>();
        for (String permission :
                permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                filters.add(permission);
            }
        }
        return filters;
    }

    /**
     * 请求权限
     */
    public static void requestDangerousPermission(Context mContext, OnPermissionRequestCallback
            permissionRequestCallback, int requstCode) {
        List<String> reMainPermissions = null;
        mOnPermissionRequestCallback = permissionRequestCallback;
        mRequestCode = requstCode;
        if (isTargetPreM(mContext)) {
            return;
        }
        if (isPreM()) {
            permissionRequestCallback.onAllPermissionGranted(false);
        } else {
            reMainPermissions = new ArrayList<String>();
            reMainPermissions = checkAllPermissionGranded(mContext);
            doRequestPermisson(mContext, reMainPermissions, requstCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void doRequestPermisson(Context mContext, List<String> reMainPermissions, int requestCode) {
        if (reMainPermissions.isEmpty()) {//权限都具备了
            mOnPermissionRequestCallback.onAllPermissionGranted(false);
        } else {//请求权限
            ((Activity) mContext).requestPermissions(reMainPermissions.toArray(
                    new String[reMainPermissions.size()]), requestCode);
        }
    }

    /**
     * 版本是否是6.0之前
     */

    public static boolean isPreM() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    /**
     * 当前设置的target是否在6.0之前
     */
    public static boolean isTargetPreM(Context mContext) {
        return mContext.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.M;
    }

    public static PackageInfo getPackageInfo(Context mContext) {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            return packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public static Set<String> getDangerousPermissions() {
        Set<String> dangerousPermissions = new HashSet<>();
        dangerousPermissions.add(Manifest.permission.READ_CALENDAR);
        dangerousPermissions.add(Manifest.permission.WRITE_CALENDAR);

        dangerousPermissions.add(Manifest.permission.CAMERA);

        dangerousPermissions.add(Manifest.permission.READ_CONTACTS);
        dangerousPermissions.add(Manifest.permission.WRITE_CONTACTS);
        dangerousPermissions.add(Manifest.permission.GET_ACCOUNTS);

        dangerousPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        dangerousPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        dangerousPermissions.add(Manifest.permission.RECORD_AUDIO);

        dangerousPermissions.add(Manifest.permission.READ_PHONE_STATE);
        dangerousPermissions.add(Manifest.permission.CALL_PHONE);
        dangerousPermissions.add(Manifest.permission.READ_CALL_LOG);
        dangerousPermissions.add(Manifest.permission.WRITE_CALL_LOG);
        dangerousPermissions.add(Manifest.permission.ADD_VOICEMAIL);
        dangerousPermissions.add(Manifest.permission.USE_SIP);
        dangerousPermissions.add(Manifest.permission.PROCESS_OUTGOING_CALLS);

        dangerousPermissions.add(Manifest.permission.BODY_SENSORS);

        dangerousPermissions.add(Manifest.permission.SEND_SMS);
        dangerousPermissions.add(Manifest.permission.RECEIVE_SMS);
        dangerousPermissions.add(Manifest.permission.READ_SMS);
        dangerousPermissions.add(Manifest.permission.RECEIVE_WAP_PUSH);
        dangerousPermissions.add(Manifest.permission.RECEIVE_MMS);

        dangerousPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        dangerousPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return dangerousPermissions;
    }

    public interface OnPermissionRequestCallback {
        /**
         * 申请的权限都通过了
         */
        void onAllPermissionGranted(boolean showRequestDialog);

        /**
         * 申请的权限没通过
         */
        void onPermissionDenied(List<String> deniedPermissions);
    }

    public static void handleRequestPermissionsResult(int requstCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults){
        if (requstCode != mRequestCode) {
            return;
        }
        ArrayList<String> retPermissions = new ArrayList<String>();
        for (int i = 0; i < grantResults.length; i++) {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                retPermissions.add(permissions[i]);
            }
        }
        if(retPermissions.isEmpty()){
            mOnPermissionRequestCallback.onAllPermissionGranted(false);
        }else{
            mOnPermissionRequestCallback.onPermissionDenied(retPermissions);
        }
    }
}
