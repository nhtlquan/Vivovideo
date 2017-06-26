package vn.lequan.vivovideo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.libsmilestudio.utils.Utils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;

import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.AccountActivity;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.fragment.ListSeasonOfSeries;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 16/08/2016.
 */
public class ChannelAdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ScreenMenuItem> lstData;
    private FragmentManager fragmentManager;
    private Point point;
    private int numberColums = 0;
    private int size;


    public ChannelAdapterHome(FragmentManager fragmentManager, Context applicationContext, ArrayList<ScreenMenuItem> lstData, int numberColums) {
        this.context = applicationContext;
        this.lstData = lstData;
        this.numberColums = numberColums;
        this.fragmentManager = fragmentManager;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_channel_test, parent, false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(point.x / numberColums + Utils.convertDpToPixel(context, 20), point.x / numberColums * 9 / 16 + Utils.convertDpToPixel(context, 20));
        layoutParams.setMargins(0, 0, 10, 0);
        view.setLayoutParams(layoutParams);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        holder.setIsRecyclable(false);
        myViewHolder.init(lstData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<ScreenMenuItem> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }


    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public ScreenMenuItem getItemAtPosition(int position) {
        return lstData.get(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumb;

        public MyViewHolder(View view) {
            super(view);
            thumb = (ImageView) view.findViewById(R.id.ivChannel);
        }

        public void init(final ScreenMenuItem item, int i) {
            Glide.with(context)
                    .load(item.getImage().getThumbnailUrl())
//                    .load(GlobalApp.getInstance().getListImageChanel().get(Utils.random(GlobalApp.getInstance().getListImageChanel().size())))
                    .asBitmap()
                    .error(R.drawable.placeholder_ngang)
                    .placeholder(R.drawable.placeholder_ngang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(thumb);
            itemView.setOnClickListener(new View.OnClickListener() {
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
                                    if (item.getType().equals(context.getString(R.string.type_videos))) {
                                        MainActivity.loadvideohomesub(item.getId());
                                    } else if (item.getType().equals(context.getString(R.string.type_movies))) {
                                        MainActivity.loadmoviehomesub(item.getId());
                                    } else if (item.getType().equals(context.getString(R.string.type_live_channels))) {
                                        MainActivity.loadchanelhomesub(item.getId());
                                    } else if (item.getType().equals(context.getString(R.string.type_series))) {
//                                        GlobalApp.getInstance().fragment = ListSeasonOfSeries.newInstance(item.getId());
                                        fragmentManager.beginTransaction().replace(R.id.content_main, ListSeasonOfSeries.newInstance(item.getId())).addToBackStack("back").commit();
                                    }
                                }

                                @Override
                                public void onAnimationCancel(final View view) {

                                }
                            })
                            .withLayer()
                            .start();
                }
            });
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
