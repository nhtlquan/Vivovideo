package vn.lequan.vivovideo.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.smile.studio.libsmilestudio.fragment.TabFragmentAdapter;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.Detail_Video.VideoDetail;
import com.smile.studio.network.DetailtChanel.ChanelDetail;
import com.smile.studio.network.Face.Face_Youtube;
import com.smile.studio.network.ListVideoChanel.Item;
import com.smile.studio.network.ListVideoChanel.ListVideoChanel;
import com.smile.studio.network.ver2.face.Face_BetMatch;
import com.smile.studio.network.ver2.model.BetMatch.BetMatch;
import com.smile.studio.network.ver2.model.ChanelItem.ItemChanel;
import com.smile.studio.network.ver2.model.DetailEpisode.DetailEpisode;
import com.smile.studio.network.ver2.model.MovieItem.Movie_Key;
import com.smile.studio.network.ver2.model.SeasonOfSeries.Episode;
import com.smile.studio.network.ver2.model.VideoItem.ItemVideo;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.VideoAdapterYoutube;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;

/**
 * Created by admin on 28/08/2016.
 */
public class BottomPlayerFragment extends Fragment {

    private View view;
    public static final String ID = "id";
    private int id = Integer.MIN_VALUE;
    private ProgressBar progressBar;
    private int time = 0;
    private TextView title_chanel, tv_title, viewcount, like, dislike, tv_description, sub_chanel;
    private RecyclerView recyclerView;
    private ImageView image_chanel;
    private VideoAdapterYoutube adapter;
    private LinearLayoutManager layout;
    private CountDownTimer countDownTimer;

    public BottomPlayerFragment() {
    }

    public static BottomPlayerFragment newInstance() {
        Bundle args = new Bundle();
        BottomPlayerFragment fragment = new BottomPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_bottom_player, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.marker_progress);
            title_chanel = (TextView) view.findViewById(R.id.title_chanel);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            sub_chanel = (TextView) view.findViewById(R.id.sub_chanel);
            viewcount = (TextView) view.findViewById(R.id.viewcount);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            image_chanel = (ImageView) view.findViewById(R.id.image_chanel);
            like = (TextView) view.findViewById(R.id.like);
            dislike = (TextView) view.findViewById(R.id.dislike);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            adapter = new VideoAdapterYoutube(getChildFragmentManager(), getActivity(), new ArrayList<Item>(), 1, false);
            layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void loadDataSeries(final DetailEpisode object, final int ID_series, final int ID_season) {

    }

    public void clearData() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void loadDataSub(final String id_youtube) {
        adapter.clean();
        progressBar.setVisibility(View.VISIBLE);
        loadDetail_video(id_youtube);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                loadRelateVideo(id_youtube);
            }

        }.start();
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    private void loadRelateVideo(String id_youtube) {
        Call<ListVideoChanel> call = GlobalApp.getInstance().retrofit.create(Face_Youtube.class).getListRelateVideo(GlobalApp.KEY, id_youtube, "video", "snippet", GlobalApp.ODER, 30);
        call.enqueue(new Callback<ListVideoChanel>() {
            @Override
            public void onResponse(Call<ListVideoChanel> call, Response<ListVideoChanel> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    adapter.addAll(response.body().getItems());
                } catch (Exception e) {
                    com.smile.studio.network.utils.Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListVideoChanel> call, Throwable t) {
                com.smile.studio.libsmilestudio.utils.Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    private void loadDetail_video(String id_youtube) {
        Call<VideoDetail> call = GlobalApp.getInstance().retrofit.create(Face_Youtube.class).getDetailVideo(GlobalApp.KEY, id_youtube, "snippet,id,statistics", GlobalApp.ODER, GlobalApp.maxResults);
        call.enqueue(new Callback<VideoDetail>() {
            @Override
            public void onResponse(Call<VideoDetail> call, Response<VideoDetail> response) {
                try {
                    initdata(response.body());
                } catch (Exception e) {
                    com.smile.studio.network.utils.Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<VideoDetail> call, Throwable t) {
                com.smile.studio.libsmilestudio.utils.Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initdata(VideoDetail body) {
        title_chanel.setText("VIVO Video");
        tv_title.setText(body.getItems().get(0).getSnippet().getTitle().replace("Muvik", "Vivo").replace("muvik","vivo"));
        viewcount.setText(format(Long.parseLong(body.getItems().get(0).getStatistics().getViewCount())) + " view");
        like.setText(format(Long.parseLong(body.getItems().get(0).getStatistics().getLikeCount())));
        dislike.setText(format(Long.parseLong(body.getItems().get(0).getStatistics().getDislikeCount())));
        getChanelID(body.getItems().get(0).getSnippet().getChannelId());
        tv_description.setText(Html.fromHtml(body.getItems().get(0).getSnippet().getDescription().replace("Muvik", "Vivo").replace("muvik","vivo")));
    }

    private void getChanelID(String channelId) {
        Call<ChanelDetail> call = GlobalApp.getInstance().retrofit.create(Face_Youtube.class).getDetailChanel(GlobalApp.KEY, channelId, "snippet,id,statistics", GlobalApp.ODER, GlobalApp.maxResults);
        call.enqueue(new Callback<ChanelDetail>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ChanelDetail> call, Response<ChanelDetail> response) {
                try {
                    Glide.with(getActivity())
                            .load(R.mipmap.ic_launcher)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .thumbnail(0.1f)
                            .into(new BitmapImageViewTarget(image_chanel) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    image_chanel.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                    sub_chanel.setText(format(Long.parseLong(response.body().getItems().get(0).getStatistics().getSubscriberCount())) + " theo dõi");
                } catch (Exception e) {

                    com.smile.studio.network.utils.Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ChanelDetail> call, Throwable t) {
                com.smile.studio.libsmilestudio.utils.Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }


}
