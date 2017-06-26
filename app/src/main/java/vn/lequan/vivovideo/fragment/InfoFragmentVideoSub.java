package vn.lequan.vivovideo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.smile.studio.network.ver2.model.VideoItem.ItemVideo;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.AccountActivity;
import vn.lequan.vivovideo.adapter.VideoAdapter;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;
import vn.lequan.vivovideo.view.DialogRating;

/**
 * Created by admin on 22/08/2016.
 */
public class InfoFragmentVideoSub extends Fragment implements View.OnClickListener {

    private final String TYPE = "type";
    private static final String OBJECT = "object";
    private View view;
    private ImageView thumb, img_xemsau, img_share;
    private TextView tv_title, tv_description;
    private Point point;
    private LinearLayoutManager layout;
    private RecyclerView recyclerVideo;
    private VideoAdapter adapter;
    private final int numberColums = 1;
    private static String DetailVideo = "DetailVideo";
    private int page = 1, pagesize = 20;
    private FragmentManager fragmentManager;
    private CallbackManager callback;
    private boolean xemsau = true, yeuthich = true, share = true;
    private MaterialRatingBar ratingbar;
    private LinearLayout layout_rating;
    private TextView viewcount;
    private ItemVideo itemVideo;

    public InfoFragmentVideoSub() {
    }

    public static InfoFragmentVideoSub newInstance(ItemVideo itemVideo) {
        Bundle args = new Bundle();
        args.putParcelable(DetailVideo, itemVideo);
        InfoFragmentVideoSub fragment = new InfoFragmentVideoSub();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_info_video, container, false);
            point = AndroidDeviceInfo.getScreenSize(getActivity());
            callback = CallbackManager.Factory.create();
            viewcount = (TextView) view.findViewById(R.id.viewcount);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            img_xemsau = (ImageView) view.findViewById(R.id.img_xemsau);
            img_share = (ImageView) view.findViewById(R.id.img_share);
            ratingbar = (MaterialRatingBar) view.findViewById(R.id.ratingbar);
            layout_rating = (LinearLayout) view.findViewById(R.id.layout_rating);
            layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerVideo = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerVideo.setLayoutManager(layout);
            adapter = new VideoAdapter(getChildFragmentManager(), view.getContext(), new ArrayList<ScreenMenuItem>(), 2, false);

            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            alphaAdapter.setFirstOnly(true);
            alphaAdapter.setDuration(1000);
            alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));
            recyclerVideo.setAdapter(alphaAdapter);

            recyclerVideo.setHasFixedSize(true);
            recyclerVideo.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.TRANSPARENT).build());
            recyclerVideo.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.TRANSPARENT).build());
            itemVideo = getArguments().getParcelable(DetailVideo);
            Event();
            img_xemsau.setOnClickListener(this);
            img_share.setOnClickListener(this);
            layout_rating.setOnClickListener(this);
            if (itemVideo.getData().getBookmark() == 1) {
                img_xemsau.setImageResource(R.drawable.ic_xemsau_click);
            } else {
                img_xemsau.setImageResource(R.drawable.ic_xemsau);
            }
        }
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        page = 1;
    }

    public void Event() {
        try {
            tv_title.setText(itemVideo.getData().getName());
            tv_description.setText(Html.fromHtml(itemVideo.getData().getDescription()));
            ratingbar.setRating(itemVideo.getData().getRating());
            viewcount.setText(itemVideo.getData().getViewCount() + " view");
            for (ScreenMenuItem screenMenuItem : itemVideo.getData().getRelatedVideo()) {
                screenMenuItem.setType("videos");
            }
            adapter.addAll(itemVideo.getData().getRelatedVideo());
        } catch (Exception e) {
            Debug.e("Lỗi: " + e.getMessage());
        }

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
                                    if (itemVideo.getData().getBookmark() == 1) {
                                        BookMarkRequest bookMarkRequest = new BookMarkRequest(0);
                                        Call<BookMark> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BookMark.class).creatBookmark(TYPEITEM.VIDEO.getValue(), itemVideo.getData().getId(), bookMarkRequest, GlobalApp.getInstance().acessToken);
                                        call.enqueue(new Callback<BookMark>() {
                                            @Override
                                            public void onResponse(Call<BookMark> call, Response<BookMark> response) {
                                                try {
                                                    BookMark value = response.body();
                                                    Debug.e(value.getMessage());
                                                    img_xemsau.setImageResource(R.drawable.ic_xemsau);
                                                    itemVideo.getData().setBookmark(0);
                                                    Toast.makeText(getContext(), "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
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
                                        Call<BookMark> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BookMark.class).creatBookmark(TYPEITEM.VIDEO.getValue(), itemVideo.getData().getId(), bookMarkRequest, GlobalApp.getInstance().acessToken);
                                        call.enqueue(new Callback<BookMark>() {
                                            @Override
                                            public void onResponse(Call<BookMark> call, Response<BookMark> response) {
                                                try {
                                                    BookMark value = response.body();
                                                    Debug.e(value.getMessage());
                                                    img_xemsau.setImageResource(R.drawable.ic_xemsau_click);
                                                    itemVideo.getData().setBookmark(1);
                                                    Toast.makeText(getContext(), "Thêm vào yêu thích thành công!", Toast.LENGTH_SHORT).show();
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
                                    Intent intent = new Intent(getActivity(), AccountActivity.class);
                                    getActivity().startActivity(intent);
                                    return;
                                }
                                break;
                            case R.id.img_share:
                                onActionShareFacebook(itemVideo.getData().getName(), itemVideo.getData().getDescription(), AndroidUtils.gotoWebStore("vn.lequan.vivovideo"), itemVideo.getData().getImage().getThumbnailUrl());
                                break;
                            case R.id.layout_rating:
                                DialogRating dialogSetting = new DialogRating(TYPEITEM.VIDEO.getValue(), itemVideo.getData().getId());
                                if (!dialogSetting.isHidden()) {
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
