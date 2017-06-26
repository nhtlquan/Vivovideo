package com.smile.studio.libsmilestudio.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;

/**
 * Created by admin on 05/06/2016.
 */
public class ApplicationInfo {

    private static PackageManager manager;
    private static PackageInfo packageinfo;

    public static String getHashKey(Context context) {
        String str = null;
        try {
            for (Signature sig : context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures) {
                MessageDigest localMessageDigest;
                (localMessageDigest = MessageDigest.getInstance("SHA1")).update(sig.toByteArray());
                str = new String(Base64.encode(localMessageDigest.digest(), 0));
            }
        } catch (Exception e) {
            Debug.e(e.toString());
        }
        return str;
    }

    /**
     * Trả về tên ứng dụng chứa trong file AndroidManifest.xml
     *
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        String str = null;
        Object localObject = context.getPackageManager();
        android.content.pm.ApplicationInfo localApplicationInfo = null;
        try {
            if ((localApplicationInfo = ((PackageManager) localObject).getApplicationInfo(context.getPackageName(),
                    0)) != null) {
                str = (localObject = ((PackageManager) localObject).getApplicationLabel(localApplicationInfo)) != null
                        ? (String) localObject : null;
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            if ((localApplicationInfo = context.getApplicationInfo()) != null) {
                str = context.getString(localApplicationInfo.labelRes);
            }
        }
        if (str != null) {
            return str;
        }
        return "unknow";
    }

    public static String getPackageName(Context context) {
        String packageName = "unkown";
        try {
            if (manager == null) {
                manager = context.getPackageManager();
                packageinfo = manager.getPackageInfo(context.getPackageName(), PackageInfo.INSTALL_LOCATION_AUTO);
            }
            return packageinfo.packageName;
        } catch (Exception e) {
            return packageName;
        }
    }

    public static String getVersionName(Context context) {
        String version = "unkown";
        try {
            if (manager == null) {
                manager = context.getPackageManager();
                packageinfo = manager.getPackageInfo(context.getPackageName(), PackageInfo.INSTALL_LOCATION_AUTO);
            }
            return packageinfo.versionName;
        } catch (Exception e) {
            return version;
        }
    }

    public static int getVersionCode(Context context) {
        int version = 1;
        try {
            if (manager == null) {
                manager = context.getPackageManager();
                packageinfo = manager.getPackageInfo(context.getPackageName(), PackageInfo.INSTALL_LOCATION_AUTO);
            }
            return packageinfo.versionCode;
        } catch (Exception e) {
            return version;
        }
    }
}
