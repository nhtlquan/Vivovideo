package vn.lequan.vivovideo.model;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.pedrovgs.DraggablePanel;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.security.MD5;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.model.Banner.BannerItem;
import com.smile.studio.network.ver2.model.NavMenu.MenuItem;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuList;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.JavaNetCookieJar;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.MenuChildAdapter;
import vn.lequan.vivovideo.fragment.BottomPlayerFragment;
import vn.lequan.vivovideo.google.analytics.AnalyticsApplication;

/**
 * Created by admin on 15/08/2016.
 */
public class GlobalApp {

    public static final String KEY_YOUTUBE = "AIzaSyCC6xdQmKTqKaIBSupfgjFttbHk-yP482A";
    public int ID;
    public Fragment fragment;
    public static boolean fragmentaccount = false;
    public DraggablePanel draggablePanel;
    public static boolean checktabfragment;
    public static boolean checkCloseFragment = false;
    public static boolean checkvideo;
    public static int width, height;
    public Retrofit retrofit, retrofit2;
    public Retrofit retrofit_ver2;
    public String acessToken = "";
    public String token = "";
    public String Platform = "Phone";
    public String id_old = "";
    public List<MenuItem> ListNavMenu;
    public static List<BannerItem> item_Banner;
    public static List<ScreenMenuList> item_screenMenu;
    public static String KEY = "AIzaSyCMsPL8kSe5rPIxrooy5F5YU8XxUEwElgs";
    public static String CHANEL_ID= "UC2hIbTRRMwBkSjGZPGgMqLA";
    public static String PART= "snippet,id";
    public static String ODER= "date";
    public static int maxResults= 5;

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Debug.e("\n" + message);
        }
    });
    public AnalyticsApplication application;

    private static GlobalApp ourInstance = new GlobalApp();

    public static GlobalApp getInstance() {
        return ourInstance;
    }


    public static int getColor(String char_firt) {
        int color = 0;
        switch (char_firt) {
            case "A":
                color = -31894819;
                break;
            case "B":
                color = -30661105;
                break;
            case "C":
                color = -21310463;
                break;
            case "D":
                color = -29369616;
                break;
            case "E":
                color = -25394107;
                break;
            case "F":
                color = -17852281;
                break;
            case "G":
                color = -28724378;
                break;
            case "H":
                color = -33416147;
                break;
            case "I":
                color = -27498423;
                break;
            case "J":
                color = -21676365;
                break;
            case "K":
                color = -32550767;
                break;
            case "L":
                color = -28108909;
                break;
            case "M":
                color = -32212814;
                break;
            case "N":
                color = -18621051;
                break;
            case "O":
                color = -25179587;
                break;
            case "P":
                color = -18060766;
                break;
            case "Q":
                color = -18996410;
                break;
            case "R":
                color = -23866310;
                break;
            case "S":
                color = -30523017;
                break;
            case "T":
                color = -28613946;
                break;
            case "U":
                color = -29116346;
                break;
            case "V":
                color = -20673289;
                break;
            case "W":
                color = -27106090;
                break;
            case "X":
                color = -20637766;
                break;
            case "Y":
                color = -32473770;
                break;
            case "Z":
                color = -20012456;
                break;
            default:
                color =-20012456;
                break;
        }
        return color;
    }


    public List<String> getListPakageService() {
        List<String> lstData = new ArrayList<>();
        lstData.add("Truyền Hình");
        lstData.add("Phim Gói");

        return lstData;
    }


    private GlobalApp() {
        if (Debug.isDebuggable())
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(logging)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }


}
