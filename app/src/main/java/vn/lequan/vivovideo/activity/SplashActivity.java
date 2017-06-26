package vn.lequan.vivovideo.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smile.studio.libsmilestudio.utils.AndroidUtils;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_AppVertion;
import com.smile.studio.network.ver2.face.Face_Banner;
import com.smile.studio.network.ver2.face.Face_NavMenu;
import com.smile.studio.network.ver2.face.Face_ScreenMenu;
import com.smile.studio.network.ver2.model.AppVertion.AppVertion;
import com.smile.studio.network.ver2.model.Banner.Banner;
import com.smile.studio.network.ver2.model.NavMenu.ActionClick;
import com.smile.studio.network.ver2.model.NavMenu.MenuItem;
import com.smile.studio.network.ver2.model.NavMenu.NavMenu;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenu;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;
import okhttp3.internal.JavaNetCookieJar;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.google.analytics.AnalyticsApplication;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.view.DialogError;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout layout_network;
    private LinearLayout linearLayout;
    private ImageView ivLogoSplash;
    private ImageView thumb;
    private Button retry_button;
    public static OkHttpClient clientplay;
    private OkHttpClient client;

    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Debug.e(message);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.e(AndroidUtils.getSHA1(this));
        try {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            CookieHandler cookieHandler = new CookieManager();

                client = new OkHttpClient().newBuilder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                Request request = original.newBuilder()
                                        .method(original.method(), original.body())
                                        .build();
                                return chain.proceed(request);
                            }
                        })
                        .addInterceptor(logging)
                        .cookieJar(new JavaNetCookieJar(cookieHandler))
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            GlobalApp.getInstance().retrofit = new Retrofit.Builder().baseUrl("https://www.googleapis.com/youtube/v3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GlobalApp.getInstance().application = (AnalyticsApplication) getApplication();
        GlobalApp.getInstance().application.trackScreenView(SplashActivity.class.getSimpleName());
        setContentView(R.layout.activity_splash);
        layout_network = (RelativeLayout) findViewById(R.id.layout_network);
        ivLogoSplash = (ImageView) findViewById(R.id.ivLogoSplash);
        linearLayout = (LinearLayout) findViewById(R.id.layout_linear);
        retry_button = (Button) findViewById(R.id.retry_button);
        retry_button.setOnClickListener(this);
        thumb = (ImageView) findViewById(R.id.thumb);
        layout_network.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        if (!AndroidUtils.isConnectedToInternet(this)) {
            layout_network.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        } else {
            startMainActivity();
        }
        animationLoading();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void hidelayout() {
        layout_network.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
    }


    private void animationLoading() {
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivLogoSplash.setAnimation(fadein);
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }

    private SSLSocketFactory getSSLSocketFactory(Context context)
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = context.getResources().openRawResource(R.raw.googleapi); // File path: app\src\main\res\raw\your_cert.cer
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }


    @Override
    public void onClick(View view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setInterpolator(new CycleInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(final View view) {

                    }

                    @Override
                    public void onAnimationEnd(final View view) {
                        switch (view.getId()) {
                            case R.id.retry_button:
                                layout_network.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                if (!AndroidUtils.isConnectedToInternet(getApplicationContext())) {
                                    layout_network.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.GONE);
                                } else {
                                    connectServer();
                                }
                        }
                    }

                    @Override
                    public void onAnimationCancel(final View view) {

                    }
                })
                .withLayer()
                .start();
    }

    public class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }

    }

    private void connectServer() {
        getAppVertion();

//        getNavMenu();
    }

    private void getScreenMenu() {
        Call<ScreenMenu> call = GlobalApp.getInstance().retrofit_ver2.create(Face_ScreenMenu.class).getListScreen();
        call.enqueue(new Callback<ScreenMenu>() {
            @Override
            public void onResponse(Call<ScreenMenu> call, Response<ScreenMenu> response) {
                try {
                    ScreenMenu value = response.body();
                    GlobalApp.item_screenMenu = value.getData().getScreenMenu();
                    getBanner();
//
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                    hidelayout();
                }
            }

            @Override
            public void onFailure(Call<ScreenMenu> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
                hidelayout();
            }
        });
    }

    private void getAppVertion() {
        Call<AppVertion> call = GlobalApp.getInstance().retrofit_ver2.create(Face_AppVertion.class).getAppVertion(getPackageName(), GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<AppVertion>() {
            @Override
            public void onResponse(Call<AppVertion> call, Response<AppVertion> response) {
                try {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    int version = pInfo.versionCode;
                    if (version < response.body().getData().getApps().get(0).getVersionCode()) {
                        linearLayout.setVisibility(View.GONE);
                        DialogError dialogSetting = new DialogError("Hiện tại đã có phiên bản mới, bạn hãy cập nhật để trải nghiệm!", "Cập Nhật", "update");
                        if (!dialogSetting.isHidden()) {
                            dialogSetting.show(getSupportFragmentManager(), DialogError.class.getSimpleName());
                        }
                    } else {
                        getNavMenu();
                    }
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                    hidelayout();
                }
            }

            @Override
            public void onFailure(Call<AppVertion> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
                hidelayout();
            }
        });
    }

    private void getBanner() {
        Call<Banner> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Banner.class).getBanner(GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<Banner>() {
            @Override
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                try {
                    Banner value = response.body();
                    GlobalApp.item_Banner = value.getData();
                    startMainActivity();
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                    hidelayout();
                }
            }

            @Override
            public void onFailure(Call<Banner> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
                hidelayout();
            }
        });
    }

    private void startMainActivity() {
        if (SharedPreferencesUtils.getString(this, API.Token) != null)
            GlobalApp.getInstance().acessToken = SharedPreferencesUtils.getString(this, API.Token);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void getNavMenu() {
        Call<NavMenu> call = GlobalApp.getInstance().retrofit_ver2.create(Face_NavMenu.class).getListNavMenu(GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<NavMenu>() {
            @Override
            public void onResponse(Call<NavMenu> call, Response<NavMenu> response) {
                try {
                    NavMenu value = response.body();
                    GlobalApp.getInstance().ListNavMenu = value.getData().getMenuItem();
                    ActionClick actionabout = new ActionClick(1, getString(R.string.about), 0, 0);
                    ActionClick actionrate = new ActionClick(1, getString(R.string.rate), 0, 0);
                    ActionClick actionshare = new ActionClick(1, getString(R.string.share), 0, 0);
                    ActionClick actionfeedback = new ActionClick(1, getString(R.string.feedback), 0, 0);
                    MenuItem menuabout = new MenuItem(null, getString(R.string.about), null, actionabout, null, null, null, null, null, null, null);
                    MenuItem menurate = new MenuItem(null, getString(R.string.rate), null, actionrate, null, null, null, null, null, null, null);
                    MenuItem menushare = new MenuItem(null, getString(R.string.share), null, actionshare, null, null, null, null, null, null, null);
                    MenuItem menufeedback = new MenuItem(null, getString(R.string.feedback), null, actionfeedback, null, null, null, null, null, null, null);
                    List<MenuItem> listMenuItem = new ArrayList<MenuItem>();
                    listMenuItem.add(menuabout);
                    listMenuItem.add(menurate);
                    listMenuItem.add(menushare);
                    listMenuItem.add(menufeedback);
                    MenuItem menuItemThongTin = new MenuItem(null, "Thông tin", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", null, null, null, null, null, null, null, listMenuItem);
                    GlobalApp.getInstance().ListNavMenu.add(menuItemThongTin);
                    if (SharedPreferencesUtils.getBoolean(getApplicationContext(), API.checkLogin)) {
                        ActionClick actionClick = new ActionClick(1, "bookmark", 0, 0);
                        MenuItem menuItem = new MenuItem(null, "Yêu thích", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", actionClick, null, null, null, null, null, null, null);
                        GlobalApp.getInstance().ListNavMenu.add(2, menuItem);
                    }
                    getScreenMenu();
                    Debug.e(GlobalApp.getInstance().ListNavMenu.get(0).getName());
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                    hidelayout();
                }
            }

            @Override
            public void onFailure(Call<NavMenu> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
                hidelayout();
            }
        });

    }
}
