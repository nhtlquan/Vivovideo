package vn.lequan.vivovideo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.model.TopBet.TopBetUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

public class UserTopAdapter extends Adapter<ViewHolder> {

    private Context context;
    private ArrayList<TopBetUser> lstData;
    private DecimalFormat format = new DecimalFormat("###,###,###");
    private int size;
    public UserTopAdapter(Context context, ArrayList<TopBetUser> lstData) {
        super();
        this.context = context;
        this.lstData = lstData;
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.init(position, lstData.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_user_top_game, parent, false);
        return new MyViewHolder(view);
    }

    private class MyViewHolder extends ViewHolder {

        private TextView tv_no, tv_name, tv_point;
        private ImageView thumb;

        public MyViewHolder(View view) {
            super(view);
            tv_no = (TextView) view.findViewById(R.id.tv_no);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_point = (TextView) view.findViewById(R.id.tv_point);
            thumb = (ImageView) view.findViewById(R.id.logo);
        }
        public void init(int position, TopBetUser item) {
            tv_no.setText(String.valueOf(position + 1));
            tv_name.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    tv_name.setSelected(true);
                }
            });
            String name = item.getName();
            try {
                String[] values = item.getName().split("@");
                name = values[0];
            } catch (Exception e) {
                Debug.e("Lỗi: " + e.getMessage() + "name: " + name);
            }
            tv_name.setText(name);
            tv_point.setText(context.getString(R.string.point_menu, format.format(item.getTotalWin())));
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound(String.valueOf(name.charAt(0)).toUpperCase(), GlobalApp.getColor(String.valueOf(name.charAt(0))));
            thumb.setImageDrawable(drawable1);
        }

    }

    public void addAll(List<TopBetUser> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }
}
