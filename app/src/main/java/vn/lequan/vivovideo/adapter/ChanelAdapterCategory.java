package vn.lequan.vivovideo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.network.ver2.model.SeriesCategory.ChildCategory;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.fragment.CategoryByIdFragment;
import vn.lequan.vivovideo.fragment.ChannelFragment;
import vn.lequan.vivovideo.fragment.SeriesFragmentByID;
import vn.lequan.vivovideo.fragment.VideoFragmentByCategory;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;

public class ChanelAdapterCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ChildCategory> lstData;
    private Point point;
    private FragmentManager fragmentManager;
    private String type;
    private Fragment fragment;


    public ChanelAdapterCategory(FragmentManager fragmentManager, Context context, List<ChildCategory> lstData) {
        this.context = context;
        this.lstData = lstData;
        this.fragmentManager = fragmentManager;
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
        myViewHolder.init(lstData.get(position));
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(String categoryTypeName, List<ChildCategory> data) {
        this.lstData.addAll(data);
        this.type = categoryTypeName;
        int size = getItemCount();
        notifyDataSetChanged();
    }

    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public ChildCategory getItemAtPosition(int position) {
        return lstData.get(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RecyclerView recyclerView;
        private ImageView icon_title;
        private MovieAdapterHome adapter;
        ChannelAdapterHome adapterMovie;
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
        }

        public void init(final ChildCategory item) {
            if (item.getItems() != null) {
                for (ScreenMenuItem screenMenuItem : item.getItems()) {
                    screenMenuItem.setType(type);
                }
                if (type.equals(TYPEITEM.MOVIE.getValue())) {
                    adapter = new MovieAdapterHome(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 3, false);
                    recyclerView.setAdapter(setAdapterAnimation(adapter));
                    adapter.addAll(item.getItems());
                } else if (type.equals(TYPEITEM.CHANEL.getValue())) {
                    adapterMovie = new ChannelAdapterHome(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 2);
                    recyclerView.setAdapter(setAdapterAnimation(adapterMovie));
                    adapterMovie.addAll(item.getItems());
                } else if (type.equals(TYPEITEM.VIDEO.getValue())) {
                    adapterVideo = new VideoAdapter(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 2, false);
                    recyclerView.setAdapter(setAdapterAnimation(adapterVideo));
                    adapterVideo.addAll(item.getItems());
                } else if (type.equals(TYPEITEM.SERIES.getValue())) {
                    seriesAdapter = new SeriesAdapter(fragmentManager, context, new ArrayList<ScreenMenuItem>(), 1, false);
                    recyclerView.setAdapter(setAdapterAnimation(seriesAdapter));
                    seriesAdapter.addAll(item.getItems());
                }
            }
            if (item.getItems() == null) {
                recyclerView.setVisibility(View.GONE);
                layout_title.setVisibility(View.GONE);
            } else {
                layout_title.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                title.setText(item.getName());
            }
            Glide.with(context)
                    .load(item.getImageUrl())
                    .asBitmap()
                    .error(R.drawable.ic_gameshow)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(icon_title);
            layout_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals(context.getString(R.string.type_videos))) {
                        fragment = VideoFragmentByCategory.newInstance(item.getId());
                        GlobalApp.getInstance().application.trackScreenView(VideoFragmentByCategory.class.getSimpleName());
                        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack("back").commit();
                    } else if (type.equals(context.getString(R.string.type_series))) {
                        fragment = SeriesFragmentByID.newInstance(item.getId());
                        GlobalApp.getInstance().application.trackScreenView(SeriesFragmentByID.class.getSimpleName());
                        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack("back").commit();
                    } else if (type.equals(context.getString(R.string.type_movies))) {
                        fragment = CategoryByIdFragment.newInstance(item.getId(), "movie");
                        GlobalApp.getInstance().application.trackScreenView(CategoryByIdFragment.class.getSimpleName());
                        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack("back").commit();
                    } else if (type.equals(context.getString(R.string.type_live_channels))) {
                        fragment = ChannelFragment.newInstance(item.getId());
                        GlobalApp.getInstance().application.trackScreenView(ChannelFragment.class.getSimpleName());
                        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack("back").commit();
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

        public class CycleInterpolator implements android.view.animation.Interpolator {

            private final float mCycles = 0.5f;

            @Override
            public float getInterpolation(final float input) {
                return (float) Math.sin(2.0f * mCycles * Math.PI * input);
            }
        }
    }
}
