package com.smile.studio.libsmilestudio.security;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.smile.studio.libsmilestudio.utils.Debug;

import org.magiclen.magiccrypt.MagicCrypt;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by admin on 30/04/2016.
 */
public class OEM1 {

    private static final String SIGNATURE = "Rm8vl1C1Rnn3CSC3pj+zYxxQ3aI=";
    private final int loop = 3;
    private MagicCrypt crypt = null;
    private String password = "";
    private long time_create = 0L;

    public OEM1(Context context, String filename) {
        password = MD5.encrypt(genKey(context, filename), loop);
        if (crypt == null) {
            crypt = new MagicCrypt(password, 256);
        }
    }

    public String KeyClient() {
        String textToEncrypt = System.currentTimeMillis() + "#" + password;
        Debug.e("Ndung mã hóa: " + textToEncrypt);
        return crypt.encrypt(textToEncrypt);
    }

    public String KeyServer() {
        Debug.e("Ndung server: " + password);
        return password;
    }

    public long getTime() {
        return time_create;
    }

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

    private static final int VALID = 0;

    private static final int INVALID = 1;

    public static int checkAppSignature(Context context) {
        String hahs = getHashKey(context);
        Debug.e(hahs);
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);

            for (Signature signature : packageInfo.signatures) {

                byte[] signatureBytes = signature.toByteArray();

                MessageDigest md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());

                final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT).replace("\n","");

                Debug.e(currentSignature + "\n" + SIGNATURE);

                //compare signatures
                if (SIGNATURE.equals(currentSignature)) {
                    return VALID;
                }
            }

        } catch (Exception e) {
            Debug.e("Lỗi: " + e.toString());
        }

        return INVALID;

    }

    public static String getSHA1(Context context) {
        String str = null;
        try {
            for (Signature sig : context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures) {
                MessageDigest localMessageDigest;
                (localMessageDigest = MessageDigest.getInstance("SHA1")).update(sig.toByteArray());
                str = byte2HexFormatted(localMessageDigest.digest());
            }
        } catch (Exception e) {
            Debug.e(e.toString());
        }
        return str;
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

    private void readMF() throws IOException {
        Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
        while (resources.hasMoreElements()) {
            try {
                Manifest manifest = new Manifest(resources.nextElement().openStream());
                for (Map.Entry<String, Attributes> str : manifest.getEntries().entrySet()) {
                    String key = str.getKey();
                    if (key.contains("ic_launcher")) {
                        Debug.e("key:\n" + key);
                        Debug.e("Att:\n" + str.getValue().getValue("SHA1-Digest"));
                    }
                }
            } catch (IOException e) {
                Debug.e("Lỗi: " + e.toString());
            }
        }

    }

    private String genKey(Context context, String filename) {
        String name = context.getPackageName();
        PackageManager pm = context.getPackageManager();
        ApplicationInfo info = null;
        ZipFile apk = null;
        try {
            readMF();
            info = pm.getApplicationInfo(name, PackageManager.GET_META_DATA);
            if (info != null && info.sourceDir != null) {
                apk = new ZipFile(info.sourceDir);
                Enumeration<ZipEntry> files = (Enumeration<ZipEntry>) apk.entries();
                while (files.hasMoreElements()) {
                    ZipEntry file = files.nextElement();
                    Debug.e("file: " + filename);
                    if (file.getName().contains(filename)) {
                        time_create = file.getTime();
                        break;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (apk != null) {
                try {
                    apk.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Debug.e("time_create: " + dateFormat.format(new Date(time_create)));
        return time_create + "#" + name;
    }

}
