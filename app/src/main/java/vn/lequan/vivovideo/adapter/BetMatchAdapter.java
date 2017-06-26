package vn.lequan.vivovideo.adapter;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.Utils;
import com.smile.studio.network.ver2.model.BetMatch.BetItem;

import java.util.List;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.fragment.BetMatchFragment;
import vn.lequan.vivovideo.fragment.GameFragment;
import vn.lequan.vivovideo.google.analytics.AnalyticsApplication;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 16/08/2016.
 */
public class BetMatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BetItem> lstData;
    private Point point;
    private int numberColums = 0;
    private int size;
    private FragmentManager fm_manager;

    public BetMatchAdapter(FragmentManager fragmentManager, Context context, List<BetItem> lstData, int numberColums) {
        this.context = context;
        this.lstData = lstData;
        this.fm_manager = fragmentManager;
        this.numberColums = numberColums;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_bet_match, parent, false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, point.y / 4);
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

    public void addAll(List<BetItem> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }


    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public BetItem getItemAtPosition(int position) {
        return lstData.get(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumb, gradient;
        private TextView title;

        public MyViewHolder(View view) {
            super(view);
            thumb = (ImageView) view.findViewById(R.id.thumb);
            title = (TextView) view.findViewById(R.id.title);
            gradient = (ImageView) view.findViewById(R.id.gradient);
        }

        public void init(final BetItem item, int i) {
            Object localObject2 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{context.getApplicationContext().getResources().getColor(R.color.background_signon), 0});
            gradient.setBackground((Drawable) localObject2);
            title.setText(item.getName());
            Glide.with(context)
                    .load(item.getThumbnailUrl())
                    .asBitmap()
                    .error(R.drawable.placeholder_ngang)
                    .placeholder(R.drawable.placeholder_ngang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(thumb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    GlobalApp.getInstance().fragment = GameFragment.newInstance(item);
                    fm_manager.beginTransaction().replace(R.id.content_main,  GameFragment.newInstance(item)).addToBackStack(null).commit();
                }
            });
        }

    }
}
