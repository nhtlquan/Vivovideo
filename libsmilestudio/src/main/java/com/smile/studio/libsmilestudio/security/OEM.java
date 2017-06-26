package com.smile.studio.libsmilestudio.security;

import android.content.Context;

import com.smile.studio.libsmilestudio.BuildConfig;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.security.api.OEMAPI;
import com.smile.studio.libsmilestudio.utils.ApplicationInfo;

import org.magiclen.magiccrypt.MagicCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 05/06/2016.
 */
public class OEM {

    private static final String HOST = "http://192.168.1.71:180";
    private static String keyClient, keyServer;

    public static void genKeyOEM(Context context, String email, String password) {
        Debug.e("Delete function when release application. Thanks you !!!");
        boolean isDebug = Debug.isDebuggable();
        String appname = ApplicationInfo.getApplicationName(context);
        String packagename = ApplicationInfo.getPackageName(context);
        String version = ApplicationInfo.getVersionName(context);
        int VersionCode = ApplicationInfo.getVersionCode(context);
        keyClient = genKeyClient(context);
        String passwd = MD5.encrypt(password, 2);
        MagicCrypt magicCrypt = new MagicCrypt(passwd, 256);
        keyServer = magicCrypt.encrypt(keyClient);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Debug.e("\n" + message);
            }
        });
        if (BuildConfig.DEBUG)
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        else
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HOST).client(client).addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<Base> call = retrofit.create(OEMAPI.class).putCreateApp(isDebug, packagename, version, keyClient, keyServer, email, passwd);
        call.enqueue(new Callback<Base>() {

            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                try {
                    Base value = response.body();
                    value.trace();
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    private static  String genKeyClient(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Enumeration<URL> resources = context.getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                try {
                    Manifest manifest = new Manifest(resources.nextElement().openStream());
                    for (Map.Entry<String, Attributes> str : manifest.getEntries().entrySet()) {
                        String key = str.getKey();
                        if (key.contains("ic_launcher") && BuildConfig.DEBUG) {
                            stringBuffer.append(str.getValue().getValue("SHA1-Digest"));
                        } else {
                            stringBuffer.append(str.getValue().getValue("SHA1-Digest"));
                        }
                    }
                } catch (IOException e) {
                    Debug.e("Lỗi: " + e.toString());
                    return "";
                }
            }
        } catch (Exception e) {
            Debug.e("Lỗi: " + e.getMessage());
            return "";
        }
        return keyClient = MD5.encrypt(stringBuffer.toString());
    }

    public static String getKeyClient() {
        return keyClient;
    }

    public static String getKeyServer() {
        return keyServer;
    }
}
