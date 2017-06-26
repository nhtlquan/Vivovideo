package vn.lequan.vivovideo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.smile.studio.libsmilestudio.facebook.ObjectFacebook;
import com.smile.studio.libsmilestudio.facebook.ShareFacebook;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.AndroidUtils;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.libsmilestudio.utils.Utils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Chanel;
import com.smile.studio.network.ver2.face.Face_Episode;
import com.smile.studio.network.ver2.face.Face_Movie;
import com.smile.studio.network.ver2.face.Face_Video;
import com.smile.studio.network.ver2.model.ChanelItem.ItemChanel;
import com.smile.studio.network.ver2.model.DetailEpisode.DetailEpisode;
import com.smile.studio.network.ver2.model.MovieItem.Movie_Key;
import com.smile.studio.network.ver2.model.VideoItem.ItemVideo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.fragment.AboutFragment;
import vn.lequan.vivovideo.fragment.BetMatchFragment;
import vn.lequan.vivovideo.fragment.BottomPlayerFragment;
import vn.lequan.vivovideo.fragment.CategoryByIdFragment;
import vn.lequan.vivovideo.fragment.ChannelFragment;
import vn.lequan.vivovideo.fragment.FragmentByCategory;
import vn.lequan.vivovideo.fragment.FragmentByCategoryChanel;
import vn.lequan.vivovideo.fragment.HomeFragment_Test;
import vn.lequan.vivovideo.fragment.ListBookMark;
import vn.lequan.vivovideo.fragment.MovieFragmentByCategory;
import vn.lequan.vivovideo.fragment.OpenFragment;
import vn.lequan.vivovideo.fragment.SearchResultFragment;
import vn.lequan.vivovideo.fragment.SeriesFragmentByID;
import vn.lequan.vivovideo.fragment.TimeShiftFragment;
import vn.lequan.vivovideo.fragment.TopPlayerFragment;
import vn.lequan.vivovideo.fragment.VideoFragmentByCategory;
import vn.lequan.vivovideo.google.analytics.AnalyticsApplication;
import vn.lequan.vivovideo.menu.FragmentDrawer;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;
import vn.lequan.vivovideo.view.DialogExit;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, View.OnClickListener {

    private boolean isShowDraggableView = false;
    private boolean isFirstOpenApp, doubleBackToExitPressedOnce;
    public static TopPlayerFragment topFragment;
    public static BottomPlayerFragment bottomFragment;
    private AlertDialog dialogExit;
    private CallbackManager callback;
    private ImageView thumb_user;
    private TextView tv_name, tv_button;
    private static ProgressDialog pDialog;
    private String keyword = null;
    private boolean pendingIntroAnimation;
    private FragmentDrawer drawerFragment;
    private Fragment fragment;
    public static String type;
    public static Activity context;
    private boolean check_Fragment = false;
    private String title = "";
    public static String type_Detail = "Type_Detail";
    public static String bundle = "BUNDLE";

    private static final String MyPREFERENCES = "Ver2_DANHGIA";
    int PRIVATE_MODE = 0;
    public static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        callback = CallbackManager.Factory.create();
        pDialog = new ProgressDialog(this, R.style.YourDialogStyle);
        GlobalApp.getInstance().application = (AnalyticsApplication) getApplication();
        GlobalApp.getInstance().application.trackScreenView(MainActivity.class.getSimpleName());
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        int i = getResources().getColor(R.color.background_signon);
        thumb_user = (ImageView) findViewById(R.id.thumb);
        thumb_user.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            openChannelNotification(bundle.getInt(API.ID));
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        GlobalApp.getInstance().draggablePanel = (DraggablePanel) findViewById(R.id.draggablePanel);
        GlobalApp.getInstance().draggablePanel.setFragmentManager(getSupportFragmentManager());
        GlobalApp.getInstance().draggablePanel.setTopFragment(topFragment = new TopPlayerFragment());
        GlobalApp.getInstance().draggablePanel.setBottomFragment(bottomFragment = new BottomPlayerFragment());
        GlobalApp.getInstance().draggablePanel.initializeView();
        GlobalApp.getInstance().draggablePanel.setTopFragmentResize(false);
        GlobalApp.getInstance().draggablePanel.getDraggableView().setClickToMaximizeEnabled(true);
        GlobalApp.getInstance().draggablePanel.getDraggableView().setClickToMinimizeEnabled(false);
        GlobalApp.getInstance().draggablePanel.getDraggableView().setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
            }

            @Override
            public void onMinimized() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                GlobalApp.getInstance().draggablePanel.getDraggableView().setClickToMaximizeEnabled(true);
            }

            @Override
            public void onClosedToLeft() {
                isShowDraggableView = false;
                topFragment.onDetach();
            }

            @Override
            public void onClosedToRight() {
                isShowDraggableView = false;
                topFragment.onDetach();
            }
        });
        if (savedInstanceState != null) {
            String SHOWDRAGGABLEVIEW = "ShowDraggableView";
            isShowDraggableView = savedInstanceState.getBoolean(SHOWDRAGGABLEVIEW);
            isFirstOpenApp = savedInstanceState.getBoolean(MainActivity.class.getSimpleName());
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            pendingIntroAnimation = true;
        }
        if (!isFirstOpenApp) {
            com.smile.studio.network.ver2.model.NavMenu.MenuItem menuItem = new com.smile.studio.network.ver2.model.NavMenu.MenuItem();
            menuItem.setId(0);
//            onDrawerItemSelected(null, "home", menuItem);
            fragment = HomeFragment_Test.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }
        loadMenu();
        onConfigurationChanged(getResources().getConfiguration());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callback.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTCODE_LOGIN_ACCOUNT) {
            loadMenu();
            if (data != null) {
                Bundle bundle = data.getBundleExtra(MainActivity.bundle);
                String type = bundle.getString(type_Detail);
                int ID = bundle.getInt("ID_Detail");
                if (type != null) {
                    if (type.equals(TYPEITEM.CHANEL.getValue())) {
                        loadchanelhomesub(ID);
                    } else if (type.equals(TYPEITEM.MOVIE.getValue())) {
                        loadmoviehomesub(ID);
                    } else if (type.equals(TYPEITEM.VIDEO.getValue())) {
                        loadvideohomesub(ID);
                    } else if (type.equals(TYPEITEM.SERIES.getValue())) {
                        loadserieshomesub(bundle.getInt("ID_series"), bundle.getInt("ID_season"), bundle.getInt("ID_episode"));
                    }
                }

            }
            if (drawerFragment.checkNavMenu()) {
                drawerFragment.closeNavMenu();
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                Point point = AndroidDeviceInfo.getScreenSize(this);
                GlobalApp.getInstance().draggablePanel.getDraggableView().setTopViewHeight(point.x);
                GlobalApp.getInstance().draggablePanel.getDraggableView().setClickToMinimizeEnabled(false);
                GlobalApp.getInstance().draggablePanel.getDraggableView().setVisibility(View.VISIBLE);
                GlobalApp.getInstance().draggablePanel.getDraggableView().maximize();
                GlobalApp.getInstance().draggablePanel.getDraggableView().setEnabled(false);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                point = AndroidDeviceInfo.getScreenSize(this);
                GlobalApp.getInstance().draggablePanel.getDraggableView().setTopViewHeight(point.x * 9 / 16);
                GlobalApp.getInstance().draggablePanel.getDraggableView().setMinimumWidth(point.x);
                GlobalApp.getInstance().draggablePanel.getDraggableView().maximize();
                GlobalApp.getInstance().draggablePanel.getDraggableView().setVisibility(View.VISIBLE);
                GlobalApp.getInstance().draggablePanel.getDraggableView().setEnabled(true);
                break;
        }
//        topFragment.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowDraggableView) {
            GlobalApp.getInstance().draggablePanel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topFragment.onDetach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (GlobalApp.checktabfragment) {
                getSupportFragmentManager().popBackStack();
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StringFormatInvalid")
    private void loadMenu() {
        tv_name.setOnClickListener(this);
        initViewAccount();
    }

    private void initViewAccount() {
        try {
            if (SharedPreferencesUtils.getBoolean(this, API.checkLogin)) {
                String name = SharedPreferencesUtils.getString(this, API.Name);
                String avatarUrl = SharedPreferencesUtils.getString(this, API.Avatar);
                TextDrawable drawable1 = TextDrawable.builder()
                        .buildRound(String.valueOf(name.charAt(0)).toUpperCase(), GlobalApp.getColor(String.valueOf(name.charAt(0))));// set avatar with random color
                if (avatarUrl != null) {
                    Glide.with(context).load(avatarUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(thumb_user) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            thumb_user.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                } else {
                    thumb_user.setImageDrawable(drawable1);
                }
                tv_name.setText(SharedPreferencesUtils.getString(this, API.Name));

            } else {
                tv_name.setText(R.string.signin);
                thumb_user.setImageResource(R.drawable.user_null);
            }
        } catch (Exception e) {
            Debug.e("Lỗi: " + e.getMessage());
        }
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(MainActivity.class.getSimpleName(), true);
    }

    public static void openDraggableView() {
        if (GlobalApp.getInstance().draggablePanel.isClosedAtRight() || GlobalApp.getInstance().draggablePanel.isClosedAtLeft()) {
            GlobalApp.getInstance().draggablePanel.setVisibility(View.VISIBLE);
            GlobalApp.getInstance().draggablePanel.maximize();
        } else {
            if (GlobalApp.getInstance().draggablePanel.getVisibility() != View.VISIBLE) {
                GlobalApp.getInstance().draggablePanel.setVisibility(View.VISIBLE);
            }
            GlobalApp.getInstance().draggablePanel.maximize();
        }
    }


    public static void loadvideohomesub(final int ID) {

        if (!SharedPreferencesUtils.getBoolean(context, API.checkLogin)) {
            Intent intent = new Intent(context, AccountActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID_Detail", ID);
            bundle.putString(type_Detail, TYPEITEM.VIDEO.getValue());
            intent.putExtra(MainActivity.bundle, bundle);
            context.startActivityForResult(intent, MainActivity.RESULTCODE_LOGIN_ACCOUNT);
            return;
        }
        if (GlobalApp.getInstance().id_old.equals("") || !GlobalApp.getInstance().id_old.equals(String.valueOf(ID) + TYPEITEM.VIDEO.getValue())) {
            GlobalApp.getInstance().id_old = (String.valueOf(ID) + TYPEITEM.VIDEO.getValue());
        } else {
            return;
        }
        MainActivity.openDraggableView();
        Call<ItemVideo> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Video.class).getDetailVideo(ID, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<ItemVideo>() {

            @Override
            public void onResponse(Call<ItemVideo> call, Response<ItemVideo> response) {
                try {
                    if (response.body().getCode() == -1) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final ItemVideo value = response.body();
                    topFragment.switchPlayer(value.getData().getId(), value.getData().getName(), value.getData().getLinkStream(), TYPEITEM.VIDEO.getValue(), value.getData().getWatchLog(), true, null);
//                    bottomFragment.loadDataSub(value);
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ItemVideo> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());

            }
        });
    }


    public static void loadchanelhomesub(final int ID) {
        if (!SharedPreferencesUtils.getBoolean(context, API.checkLogin)) {
            Intent intent = new Intent(context, AccountActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID_Detail", ID);
            bundle.putString(type_Detail, TYPEITEM.CHANEL.getValue());
            intent.putExtra(MainActivity.bundle, bundle);
            context.startActivityForResult(intent, MainActivity.RESULTCODE_LOGIN_ACCOUNT);
            return;
        }
        if (GlobalApp.getInstance().id_old.equals("") || !GlobalApp.getInstance().id_old.equals(String.valueOf(ID) + TYPEITEM.CHANEL.getValue())) {
            GlobalApp.getInstance().id_old = (String.valueOf(ID) + TYPEITEM.CHANEL.getValue());
        } else {
            return;
        }

        Call<ItemChanel> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Chanel.class).getDetailChanel(ID, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<ItemChanel>() {

            @Override
            public void onResponse(Call<ItemChanel> call, Response<ItemChanel> response) {
                try {
                    if (response.body().getData().getLiveChannel() == null) {
                        Toast.makeText(context, "Hiện tại kênh không được phát sóng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MainActivity.openDraggableView();
                    if (response.body().getCode() == -1) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final ItemChanel value = response.body();
                    topFragment.switchPlayer(value.getData().getLiveChannel().getId(), value.getData().getLiveChannel().getName(), value.getData().getLiveChannel().getLink(), TYPEITEM.CHANEL.getValue(), 0, false, value.getData().getLiveChannel().getLinkType());
//                    bottomFragment.loadDataSub(value);
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ItemChanel> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());

            }
        });
    }

    public static void loadserieshomesub(final int ID_series, final int ID_season, int ID_episode) {
        if (!SharedPreferencesUtils.getBoolean(context, API.checkLogin)) {
            Intent intent = new Intent(context, AccountActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID_series", ID_series);
            bundle.putInt("ID_season", ID_season);
            bundle.putInt("ID_episode", ID_episode);
            bundle.putString(type_Detail, TYPEITEM.SERIES.getValue());
            intent.putExtra(MainActivity.bundle, bundle);
            context.startActivityForResult(intent, MainActivity.RESULTCODE_LOGIN_ACCOUNT);
            return;
        }
        if (GlobalApp.getInstance().id_old.equals("") || !GlobalApp.getInstance().id_old.equals(String.valueOf(ID_episode) + TYPEITEM.SERIES.getValue())) {
            GlobalApp.getInstance().id_old = (String.valueOf(ID_episode) + TYPEITEM.SERIES.getValue());
        } else {
            return;
        }
        MainActivity.openDraggableView();
        Call<DetailEpisode> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Episode.class).getDetailEpisode(ID_series, ID_season, ID_episode, GlobalApp.getInstance().acessToken, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<DetailEpisode>() {

            @Override
            public void onResponse(Call<DetailEpisode> call, Response<DetailEpisode> response) {
                try {
                    if (response.body().getCode() == -1) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final DetailEpisode value = response.body();
                    topFragment.switchPlayer(value.getData().getEpisode().getId(), value.getData().getEpisode().getName(), value.getData().getEpisode().getFileUrl(), TYPEITEM.EPISODE.getValue(), value.getData().getEpisode().getWatchLog(), true, null);
                    bottomFragment.loadDataSeries(value, ID_series, ID_season);
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DetailEpisode> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }
    public static void loadvideoYoutbe(String videoId) {
        MainActivity.openDraggableView();
        topFragment.switchPlayer(videoId);
        bottomFragment.loadDataSub(videoId);
    }
    public static void loadmoviehomesub(final int ID) {
        if (!SharedPreferencesUtils.getBoolean(context, API.checkLogin)) {
            Intent intent = new Intent(context, AccountActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID_Detail", ID);
            bundle.putString(type_Detail, TYPEITEM.MOVIE.getValue());
            intent.putExtra(MainActivity.bundle, bundle);
            Debug.e(bundle.getString(type_Detail));
            context.startActivityForResult(intent, MainActivity.RESULTCODE_LOGIN_ACCOUNT);
            return;
        }
        if (GlobalApp.getInstance().id_old.equals("") || !GlobalApp.getInstance().id_old.equals(String.valueOf(ID) + TYPEITEM.MOVIE.getValue())) {
            GlobalApp.getInstance().id_old = (String.valueOf(ID) + TYPEITEM.MOVIE.getValue());
        } else {
            return;
        }
        MainActivity.openDraggableView();
        Call<Movie_Key> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Movie.class).getDetailMovie(ID, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<Movie_Key>() {

            @Override
            public void onResponse(Call<Movie_Key> call, Response<Movie_Key> response) {
                try {
                    if (response.body().getCode() == -1) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final Movie_Key value = response.body();
                    topFragment.switchPlayer(value.getData().getMovie().getId(), value.getData().getMovie().getName(), value.getData().getMovie().getFileUrl(), TYPEITEM.MOVIE.getValue(), value.getData().getMovie().getWatchLog(), true, null);
//                    bottomFragment.loadDataSub(value);
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Movie_Key> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        if (doubleBackToExitPressedOnce) {
            if (topFragment != null)
                topFragment.onDetach();
            Process.killProcess(Process.myPid());
            return;
        }
        if (GlobalApp.getInstance().draggablePanel.isMaximized() && GlobalApp.getInstance().draggablePanel.getVisibility() == View.VISIBLE) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                GlobalApp.getInstance().draggablePanel.minimize();
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                keyword = null;
                getSupportFragmentManager().popBackStack();
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            } else {
                if (sharedpreferences.getBoolean("check", true)) {
                    DialogExit dialogSetting = new DialogExit();
                    if (!dialogSetting.isHidden()) {
                        dialogSetting.show(getSupportFragmentManager(), DialogExit.class.getSimpleName());
                        return;
                    }
                }
                this.doubleBackToExitPressedOnce = true;
                Debug.showAlert(this, getString(R.string.double_back));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewMenuItem);
        int searchImgId = android.support.v7.appcompat.R.id.search_button; // I used the explicit layout ID of searchview's ImageView
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_search);
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHintTextColor(Color.WHITE);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (keyword == null || !keyword.equals(query)) {
            keyword = query;
//            if (GlobalApp.getInstance().fragment instanceof SearchResultFragment) {
//                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            }
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            fragment = SearchResultFragment.newInstance(keyword);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("back").commit();
            GlobalApp.getInstance().application.trackEvent(MainActivity.class.getSimpleName(), "search keyword", keyword);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public static final int RESULTCODE_LOGIN_ACCOUNT = 200;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                Intent intent = new Intent(this, AccountActivity.class);
                startActivityForResult(intent, RESULTCODE_LOGIN_ACCOUNT);
                break;
            case R.id.thumb:
                Intent intent1 = new Intent(this, AccountActivity.class);
                startActivityForResult(intent1, RESULTCODE_LOGIN_ACCOUNT);
                break;
            case R.id.nav_header_main:
                Intent intent2 = new Intent(this, AccountActivity.class);
                startActivityForResult(intent2, RESULTCODE_LOGIN_ACCOUNT);
                break;
            case R.id.toolbar_logo:
                fragment = HomeFragment_Test.newInstance();
                GlobalApp.getInstance().application = (AnalyticsApplication) getApplication();
                GlobalApp.getInstance().application.trackScreenView(HomeFragment_Test.class.getSimpleName());
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("home").commit();
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


}
