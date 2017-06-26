package vn.lequan.vivovideo.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.AnimCore;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuList;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.fragment.CategoryByIdFragment;
import vn.lequan.vivovideo.fragment.ChannelFragment;
import vn.lequan.vivovideo.fragment.FragmentByCategory;
import vn.lequan.vivovideo.fragment.FragmentByCategoryChanel;
import vn.lequan.vivovideo.fragment.SeriesFragmentByID;
import vn.lequan.vivovideo.fragment.VideoFragmentByCategory;
import vn.lequan.vivovideo.google.analytics.AnalyticsApplication;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ScreenMenuList> lstData;
    private Point point;
    private RequestManager manager;
    private int numberColmns = 2;
    private FragmentManager fragmentManager;
    private boolean isVideo;
    private ProgressDialog pDialog;
    private int lastPosition = -1;
    private Fragment fragment;


    public HomeAdapter(FragmentManager fragmentManager, Context context, List<ScreenMenuList> lstData) {
        this.context = context;
        this.lstData = lstData;
        this.numberColmns = numberColmns;
        this.fragmentManager = fragmentManager;
        this.isVideo = isVideo;
        point = AndroidDeviceInfo.getScreenSize(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_home_test, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
//        setAnimation(holder.itemView, position);
        myViewHolder.init(lstData.get(position));
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fadein_adapter);
            AnimCore.anim(viewToAnimate, R.anim.fadein_adapter, false, position * 3000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<ScreenMenuList> data) {
        this.lstData.addAll(data);
        int size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public ScreenMenuList getItemAtPosition(int position) {
        return lstData.get(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RecyclerView recyclerView;
        private ImageView icon_title;
        private MovieAdapterHome adapter;
        private ChannelAdapterHome adapterMovie;
        private VideoAdapter adapterVideo;
        private RelativeLayout layout_title;
        private SeriesAdapter seriesAdapter;

        public MyViewHolder(View view) {
            super(view);
            layout_title = (RelativeLayout) view.findViewById(R.id.layout_title);
            title = (TextView) view.findViewById(R.id.title);
            icon_title = (ImageView) view.findViewById(R.id.icon_title);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(context).color(Color.TRANSPARENT).sizeResId(R.dimen.padding1).build());
        }

        @SuppressLint("SetTextI18n")
        public void init(final ScreenMenuList item) {
            if (item.getType().equals(TYPEITEM.MOVIE.getValue())) {
                adapter = new MovieAdapterHome(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 3, false);
                recyclerView.setAdapter(setAdapterAnimation(adapter));
                adapter.addAll(item.getScreenMenuItems());
            } else if (item.getType().equals(TYPEITEM.CHANEL.getValue())) {
                adapterMovie = new ChannelAdapterHome(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 2);
                recyclerView.setAdapter(setAdapterAnimation(adapterMovie));
                adapterMovie.addAll(item.getScreenMenuItems());
            } else if (item.getType().equals(TYPEITEM.SERIES.getValue())) {
                seriesAdapter = new SeriesAdapter(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 3, false);
                recyclerView.setAdapter(setAdapterAnimation(seriesAdapter));
                seriesAdapter.addAll(item.getScreenMenuItems());
            } else {
                adapterVideo = new VideoAdapter(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 2, false);
                recyclerView.setAdapter(setAdapterAnimation(adapterVideo));
                adapterVideo.addAll(item.getScreenMenuItems());
            }
            title.setText(item.getName());
            if (item.getScreenMenuItems().size() == 0) {
                layout_title.setVisibility(View.GONE);
            } else {
                title.setText(item.getName());
            }
            Glide.with(context)
                    .load(item.getIconFile())
                    .asBitmap()
                    .error(R.drawable.ic_gameshow)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(icon_title);
            layout_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.e(item.getType());
                    if (item.getType().equals(context.getString(R.string.type_videos))) {
                        if (item.getIsHasChild() == 1) {
                            fragment = FragmentByCategory.newInstance(item.getCategoryId());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
                        } else {
                            fragment = VideoFragmentByCategory.newInstance(item.getCategoryId());
                            GlobalApp.getInstance().application.trackScreenView(VideoFragmentByCategory.class.getSimpleName());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack("back").commit();
                        }
                    } else if (item.getType().equals(context.getString(R.string.type_series))) {
                        if (item.getIsHasChild() == 1) {
                            fragment = FragmentByCategory.newInstance(item.getCategoryId());
                            GlobalApp.getInstance().application.trackScreenView(FragmentByCategory.class.getSimpleName());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
                        } else {
                            fragment = SeriesFragmentByID.newInstance(item.getCategoryId());
                            GlobalApp.getInstance().application.trackScreenView(SeriesFragmentByID.class.getSimpleName());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
                        }
                    } else if (item.getType().equals(context.getString(R.string.type_movies))) {
                        if (item.getIsHasChild() == 1) {
                            fragment = FragmentByCategory.newInstance(item.getCategoryId());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
                        } else {
                            fragment = CategoryByIdFragment.newInstance(item.getCategoryId(), "movie");
                            GlobalApp.getInstance().application.trackScreenView(CategoryByIdFragment.class.getSimpleName());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack("back").commit();
                        }
                    } else if (item.getType().equals(context.getString(R.string.type_live_channels))) {
                        if (item.getIsHasChild() == 1) {
                            fragment = FragmentByCategoryChanel.newInstance(item.getCategoryId());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
                        } else {
                            fragment = ChannelFragment.newInstance(item.getCategoryId());
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
                        }
                    }
                }
            });
        }

        private AlphaInAnimationAdapter setAdapterAnimation(RecyclerView.Adapter adapter) {
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            alphaAdapter.setFirstOnly(true);
            alphaAdapter.setDuration(1000);
            alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));
            return alphaAdapter;
        }

    }
}
