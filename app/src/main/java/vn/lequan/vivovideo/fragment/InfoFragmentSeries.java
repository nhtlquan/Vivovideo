package vn.lequan.vivovideo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.smile.studio.libsmilestudio.facebook.ObjectFacebook;
import com.smile.studio.libsmilestudio.facebook.ShareFacebook;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.AndroidUtils;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_BookMark;
import com.smile.studio.network.ver2.model.BookMark.BookMark;
import com.smile.studio.network.ver2.model.BookMark.BookMarkRequest;
import com.smile.studio.network.ver2.model.DetailEpisode.DetailEpisode;
import com.smile.studio.network.ver2.model.SeasonOfSeries.Episode;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.AccountActivity;
import vn.lequan.vivovideo.adapter.SeriesAdapterRelate;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;
import vn.lequan.vivovideo.view.DialogRating;

/**
 * Created by admin on 22/08/2016.
 */
public class InfoFragmentSeries extends Fragment implements View.OnClickListener {

    private final String TYPE = "type";
    private static final String ItemMovie = "itemmovie";
    private View view;
    private TextView tv_title, tv_description, textView4, titleRelate;
    private boolean xemsau = true, yeuthich = true, share = true;
    private Point point;
    private LinearLayoutManager layout;
    private RecyclerView recyclerView;
    private LinearLayout layout_rating;
    private MaterialRatingBar ratingbar;
    private ImageView thumb, img_xemsau, img_share;
    private SeriesAdapterRelate adapter;
    private DetailEpisode itemMovie;
    private CallbackManager callback;
    private static final String IDseries = "ID_series";
    private static final String IDseason = "ID_season";
    boolean isTextViewClicked = false;
    private TextView viewcount;
    private int numberColumns = 3;

    public InfoFragmentSeries() {
    }

    public static InfoFragmentSeries newInstance(DetailEpisode itemMovie, int ID_series, int ID_season) {
        Bundle args = new Bundle();
        args.putParcelable(ItemMovie, itemMovie);
        args.putInt(IDseries, ID_series);
        args.putInt(IDseason, ID_season);
        InfoFragmentSeries fragment = new InfoFragmentSeries();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_info, container, false);
            point = AndroidDeviceInfo.getScreenSize(getActivity());
            callback = CallbackManager.Factory.create();
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            titleRelate = (TextView) view.findViewById(R.id.titleRelate);
            titleRelate.setText("Danh Sách Tập");
            viewcount = (TextView) view.findViewById(R.id.viewcount);
            ratingbar = (MaterialRatingBar) view.findViewById(R.id.ratingbar);
            img_xemsau = (ImageView) view.findViewById(R.id.img_xemsau);
            layout_rating = (LinearLayout) view.findViewById(R.id.layout_rating);
            img_share = (ImageView) view.findViewById(R.id.img_share);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getContext()).color(Color.TRANSPARENT).sizeResId(R.dimen.padding1).build());
            adapter = new SeriesAdapterRelate(getChildFragmentManager(), getActivity(), new ArrayList<Episode>(), numberColumns, false);
            recyclerView.setAdapter(setAdapterAnimation(adapter));
            recyclerView.setHasFixedSize(true);
            itemMovie = getArguments().getParcelable(ItemMovie);
            onEvent(itemMovie);
            img_xemsau.setOnClickListener(this);
            img_share.setOnClickListener(this);
            layout_rating.setOnClickListener(this);
            if (itemMovie.getData().getEpisode().getBookmark() != null) {
                if (itemMovie.getData().getEpisode().getBookmark() == 1) {
                    img_xemsau.setImageResource(R.drawable.ic_xemsau_click);
                } else {
                    img_xemsau.setImageResource(R.drawable.ic_xemsau);
                }
            }
        }
        return view;
    }
    private AlphaInAnimationAdapter setAdapterAnimation(RecyclerView.Adapter adapter) {
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setFirstOnly(true);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));
        return alphaAdapter;
    }
    @Override
    public void onClick(View v) {
        ViewCompat.animate(v)
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
                            case R.id.img_xemsau:
                                if (SharedPreferencesUtils.getBoolean(getActivity(), API.checkLogin)) {
                                    if (itemMovie.getData().getEpisode().getBookmark() == 1) {
                                        BookMarkRequest bookMarkRequest = new BookMarkRequest(0);
                                        Call<BookMark> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BookMark.class).creatBookmark(TYPEITEM.EPISODE.getValue(), itemMovie.getData().getEpisode().getId(), bookMarkRequest, GlobalApp.getInstance().acessToken);
                                        call.enqueue(new Callback<BookMark>() {
                                            @Override
                                            public void onResponse(Call<BookMark> call, Response<BookMark> response) {
                                                try {
                                                    BookMark value = response.body();
                                                    Debug.e(value.getMessage());
                                                    img_xemsau.setImageResource(R.drawable.ic_xemsau);
                                                    itemMovie.getData().getEpisode().setBookmark(0);
                                                    Toast.makeText(getContext(), "Xóa xem sau thành công!", Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    Debug.e("Lỗi: " + e.getMessage());
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<BookMark> call, Throwable t) {
                                                Debug.e("Lỗi: " + t.getMessage());
                                            }
                                        });
                                    } else {
                                        BookMarkRequest bookMarkRequest = new BookMarkRequest(1);
                                        Call<BookMark> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BookMark.class).creatBookmark(TYPEITEM.EPISODE.getValue(), itemMovie.getData().getEpisode().getId(), bookMarkRequest, GlobalApp.getInstance().acessToken);
                                        call.enqueue(new Callback<BookMark>() {
                                            @Override
                                            public void onResponse(Call<BookMark> call, Response<BookMark> response) {
                                                try {
                                                    BookMark value = response.body();
                                                    Debug.e(value.getMessage());
                                                    img_xemsau.setImageResource(R.drawable.ic_xemsau_click);
                                                    itemMovie.getData().getEpisode().setBookmark(1);
                                                    Toast.makeText(getContext(), "Thêm vào xem sau thành công!", Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    Debug.e("Lỗi: " + e.getMessage());
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<BookMark> call, Throwable t) {
                                                Debug.e("Lỗi: " + t.getMessage());
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Bạn vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.img_share:
                                if (share) {
                                    share = false;
//                    img_share.setImageResource(R.drawable.ic_chiase_click);
                                    onActionShareFacebook(itemMovie.getData().getEpisode().getName(), itemMovie.getData().getEpisode().getDescription(), AndroidUtils.gotoWebStore("vn.lequan.vivovideo"), itemMovie.getData().getEpisode().getImage().getThumbnailUrl());
                                } else {
                                    share = true;
                                    img_share.setImageResource(R.drawable.ic_chiase);
                                }
                                break;
                            case R.id.layout_rating:
                                Debug.e("Quan");
                                DialogRating dialogSetting = new DialogRating(TYPEITEM.EPISODE.getValue(), itemMovie.getData().getEpisode().getId());
                                if (!dialogSetting.isHidden()) {
                                    Debug.e("isHidden");
                                    dialogSetting.show(getFragmentManager(), DialogRating.class.getSimpleName());
                                }
                                break;
                        }
                    }

                    @Override
                    public void onAnimationCancel(final View view) {

                    }
                })
                .withLayer()
                .start();
    }

    private class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(DetailEpisode itemMovie) {
        try {
            ratingbar.setRating(itemMovie.getData().getEpisode().getRating());
            tv_title.setText(itemMovie.getData().getEpisode().getName());
            tv_description.setText(Html.fromHtml(itemMovie.getData().getEpisode().getDescription()));
            viewcount.setText(itemMovie.getData().getEpisode().getViewCount() + " view");
            adapter.addAll(itemMovie.getData().getRelatedEpisode(), getArguments().getInt(IDseries), getArguments().getInt(IDseason));
        } catch (Exception e) {
            Debug.e("Lỗi: " + e.getMessage());
        }
    }

    public void onActionShareFacebook(String title, String description, String url, String image) {
        ShareFacebook performShare = new ShareFacebook(getActivity(), callback);
        performShare.onActionShareFacebook(new ObjectFacebook(title, description, url, image), new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Debug.showAlert(getActivity(), "Đã chia sẻ trên facebook");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Debug.showAlert(getActivity(), error.getMessage());
            }
        });
    }
}

