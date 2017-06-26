package com.smile.studio.libsmilestudio.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Base64;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
@SuppressLint("DefaultLocale")
public class AndroidUtils {

    public static Date getDateInstall(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            return new Date(info.firstInstallTime);
        } catch (NameNotFoundException e) {
            Debug.e("Lỗi: " + e.toString());
            return null;
        }
    }

    /**
     * Gỡ bỏ cài đặt ứng dụng
     *
     * @param context
     * @param appPackageName
     */
    public static void uninstallApp(Context context, String appPackageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + appPackageName));
        context.startActivity(intent);
    }

    /**
     * Chạy ứng dụng
     *
     * @param context
     * @param appPackageName
     */
    public static void runApp(Context context, String appPackageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
        context.startActivity(intent);
    }

    public static String gotoMaketStore(String packagename) {
        return "market://details?id=" + packagename;
    }

    public static String getDetailsMaket(Context context) {
        return "market://details?id=" + context.getPackageName();
    }

    public static String gotoWebStore(String packagename) {
        return "https://play.google.com/store/apps/details?id=" + packagename;
    }

    public static String getDetails(Context context) {
        return "https://play.google.com/store/apps/details?id=" + context.getPackageName();
    }

    public static void onActionRateApp(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getDetailsMaket(context))));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getDetails(context))));
        }
    }

    public static String getProductionApp(Context context, String publisher_name) {
        return "market://search?q=pub:" + publisher_name;
    }

    public static String getProductionWeb(Context context, String publisher_name) {
        return "http://play.google.com/store/search?q=pub:" + publisher_name;
    }

    public static void onActionMoreApp(Context context, String publisher_name) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getProductionApp(context, publisher_name))));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getProductionWeb(context, publisher_name))));
        }
    }

    /**
     * Trả về mã Hashkey tương ứng với keystore cho ứng dụng
     *
     * @param context
     * @return
     */
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
     * Trả về chuỗi SHA1 tương ứng với keystore cho ứng dụng
     *
     * @param context
     * @return
     */
    public static String getSHA1(Context context) {
        String str = null;
        try {
            for (Signature sig : context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures) {
                MessageDigest localMessageDigest;
                (localMessageDigest = MessageDigest.getInstance("SHA1")).update(sig.toByteArray());
                str = byte2HexFormatted(localMessageDigest.digest());
            }
        } catch (Exception e) {
            Debug.e(e.toString());
        }
        return str;
    }

    /**
     * Trả về địa chỉ IP Address non-localhost từ giao diện mạng
     * NetworkInterface. Yêu cầu cấp quyền sử dụng
     * Manifest.permission#ACCESS_NETWORK_STATE cho ứng dụng.
     *
     * @param useIPv4
     * @return
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
//						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = Inet4Address.getByName(sAddr) !=
                                null ? true : false;
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%');
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return "";
    }

    /**
     * Trả về địa chỉ MAC của thiết bị CHÚ Ý: Yêu cầu API LEVEL 9 trở lên
     *
     * @param interfaceName - eth0, wlan0 or NULL=use first interface
     * @return Trả về một chuỗi chứa địa chỉ mac nếu tìm thấy. Ngược lại trả về
     * chuỗi trống nếu không tìm thấy
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null)
                    return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0)
                    buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception e) {
            Debug.e(e.toString());
        }
        return "";
    }

    /**
     * Kiểm tra xem điện thoại có thể truy cập Internet theo đường nào, ví như
     * thiết bị có kết nối internet thông qua đường truyền wifi hay mobile hay
     * bluetooth...
     *
     * @param context
     * @return
     */
    public static int getTypeConnectedToIntenet(Context context) {
        ConnectivityManager cm;
        NetworkInfo networkInfo[];
        if (((cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)) != null)
                && ((networkInfo = cm.getAllNetworkInfo()) != null)) {
            for (int i = 0; i < networkInfo.length; i++) {
                if ((networkInfo[i] != null) && (networkInfo[i].isAvailable())
                        && (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)) {
                    return networkInfo[i].getType();
                }
            }
        }
        return -1;
    }

    /**
     * Hàm lấy địa chỉ MAC của wifi. Yêu cầu cấp quyền sử dụng
     * Manifest.permission#ACCESS_NETWORK_STATE cho ứng dụng.
     *
     * @param context
     * @return
     */
    public static String getWifiMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress();
    }

    /**
     * Kiểm tra điện thoại đã kết
     * <p/>
     * nối mạng (Internet) hay chưa?
     *
     * @param context
     * @return
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * Kiểm tra xem điện thoại có thể truy cập Internet qua sóng của nhà mạng
     * (GPRS, 3G, HIPRI, UTMS, etc.) hay không?
     *
     * @param context
     * @return
     */
    public static boolean isConnectedToInternetMobile(Context context) {
        int value = 0;
        return ((value = getTypeConnectedToIntenet(context)) == 0) || (value == 4) || (value == 5) || (value == 2)
                || (value == 3);
    }

    /**
     * Kiểm tra xem ứng dụng đã cấp quyền truy cập nào đó hay chưa? Nếu ứng dụng
     * được cấp quyền thì có thể lấy các thông tin hoặc sử dụng các thông tin đó
     * trên thiết bị của người dùng.
     *
     * @param context
     * @param permission
     * @return
     * @throws NullPointerException
     */
    public static boolean checkPermission(Context context, String permission) throws NullPointerException {
        return context.getPackageManager().checkPermission(permission, context.getPackageName()) != 0;
    }

    /**
     * Kiểm tra một Activity có trong file manifest hay chưa?
     *
     * @param context
     * @param mClass
     * @return
     */
    public static boolean isExsistActivity(Context context, Class<?> mClass) throws NameNotFoundException {
        return context.getPackageManager().getActivityInfo(new ComponentName(context, mClass),
                PackageManager.GET_META_DATA) != null;
    }

    /**
     * Kiểm tra một Service có trong file manifest hay chưa?
     *
     * @param context
     * @param mClass
     * @return
     */
    public static boolean isExsistServices(Context context, Class<?> mClass) throws NameNotFoundException {
        return context.getPackageManager().getServiceInfo(new ComponentName(context, mClass),
                PackageManager.GET_META_DATA) != null;
    }

    /**
     * Kiểm tra đang chạy máy ảo hay máy thật
     *
     * @return false là máy thật | true là máy ảo
     */
    public static boolean isRunningOnEmulator() {
        String model = Build.MODEL;
        Debug.d("model=" + model);
        String product = Build.PRODUCT;
        Debug.d("product=" + product);
        boolean isEmulator = false;
        if (product != null) {
            isEmulator = product.equals("sdk") || product.contains("_sdk") || product.contains("sdk_");
        }
        Debug.d("isEmulator=" + isEmulator);
        return isEmulator;
    }

    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder localStringBuilder = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String str;
            int j;
            if ((j = (str = Integer.toHexString(arr[i])).length()) == 1) {
                str = "0" + str;
            }
            if (j > 2) {
                str = str.substring(j - 2, j);
            }
            localStringBuilder.append(str.toUpperCase());
            if (i < arr.length - 1) {
                localStringBuilder.append(':');
            }
        }
        return localStringBuilder.toString();
    }

}
