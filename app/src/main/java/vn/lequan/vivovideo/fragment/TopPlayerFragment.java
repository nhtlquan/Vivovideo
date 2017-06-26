package vn.lequan.vivovideo.fragment;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.enums.TYPE;
import com.smile.studio.network.ver2.face.Face_Chanel;
import com.smile.studio.network.ver2.face.Face_WatchLog;
import com.smile.studio.network.ver2.model.WatchLog.WatchLog;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.lequan.vivovideo.BuildConfig;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.YouTubeVideoInfoRetriever;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.activity.SplashActivity;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static vn.lequan.vivovideo.activity.MainActivity.bundle;
import static vn.lequan.vivovideo.activity.MainActivity.context;

/**
 * Created by admin on 28/08/2016.
 */
public class TopPlayerFragment extends Fragment {
    public Fragment fragment;
    private static final String HUONGDAN = "HUONGDANPLAYER";
    int PRIVATE_MODE = 0;
    private SharedPreferences sharedpreferences;

    public static final String ACTION_VIEW = "com.google.android.exoplayer.demo.action.VIEW";

    public static final String ACTION_VIEW_LIST =
            "com.google.android.exoplayer.demo.action.VIEW_LIST";

    private static final CookieManager DEFAULT_COOKIE_MANAGER;
    private static final String RESUMEWINDOW = "resumeWindow";
    private static final String RESUMEPOSITION = "resumePosition";
    private int time_WatchLog = 0;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private Handler mainHandler;
    private LinearLayout debugRootView;
    private RelativeLayout controlerView;
    private TextView debugTextView;
    private Button retryButton;

    private boolean playerNeedsSource;
    private View view;
    private Runnable myRunnable;
    private Handler myHandler = new Handler();
    private boolean shouldAutoPlay;
    private int resumeWindow;
    private long resumePosition;
    private String userAgent;
    private TextView player_video_title_view, live_label, timer_label;
    private ImageView player_collapse_button, player_share_button, player_control_play_pause_replay_button, fullscreen_button;
    public static TopPlayerFragment.IAction iAction;
    public static final String TYPE_VAULE = "type";
    private YouTubePlayerSupportFragment playerFragment;
    private String typeValue;
    private int ID_linkstream;
    private boolean checkWatchLog;
    private CountDownTimer countDownTimer;
    private String type_Stream;
    private boolean resumeVideo;
    private boolean watchLog = true;
    private YouTubePlayer youTube_Player;

    public TopPlayerFragment() {
    }

    public interface IAction {
        void performShare();
    }

    public static TopPlayerFragment newInstance() {
        Bundle args = new Bundle();
        TopPlayerFragment fragment = new TopPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAgent = getString(R.string.userAgent);
        shouldAutoPlay = true;
        if (savedInstanceState != null) {
            resumeWindow = savedInstanceState.getInt(RESUMEWINDOW);
            resumePosition = savedInstanceState.getLong(RESUMEPOSITION);
        }
        mainHandler = new Handler();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_top_player, container, false);
            sharedpreferences = getActivity().getSharedPreferences(HUONGDAN, PRIVATE_MODE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controlerView.setVisibility(View.VISIBLE);
                }
            });
            playerFragment = (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.tubePlayerFragment);
            playerFragment = (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.tubePlayerFragment);
        }
        return view;
    }

    public void switchPlayer(final String link) {
        if (youTube_Player != null)
            youTube_Player.loadVideo(link);
        playerFragment.initialize(GlobalApp.KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                youTubePlayer.loadVideo(link);
                youTube_Player = youTubePlayer;
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Debug.e("Lỗi: " + youTubeInitializationResult.toString());
            }
        });
    }
    //not chanel
    public void switchPlayer(int streamID, final String title, final String link, final String type, int timeWatchlog, boolean checkloadWatchLog, String linkType) throws UnsupportedEncodingException {

    }

    public static String parseUrl(String surl) throws Exception {
        URL u = new URL(surl);
        Debug.e(u.getQuery());
        Debug.e(u.getPath());
        Debug.e(u.getAuthority());
        Debug.e(u.getProtocol());
        Debug.e(u.getProtocol() + "://" + u.getAuthority() + URLEncoder.encode(u.getPath(), "UTF-8").replace("%2F", "/") + "?" + u.getQuery());
        return new URI(u.getProtocol(), u.getAuthority(), u.getPath(), u.getQuery(), u.getRef()).toString();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RESUMEWINDOW, resumeWindow);
        outState.putLong(RESUMEPOSITION, resumePosition);
    }


}
