package vn.lequan.vivovideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.Utils;
import com.smile.studio.network.ver2.model.ListChanel.ChanelItem;

import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 18/08/2016.
 */
public class TimeShiftChannelAdaper extends BaseAdapter {

    private Context context;
    private ArrayList<ChanelItem> lstData;
    private MyViewHolder myViewHolder;
    private int poAnInt;

    public TimeShiftChannelAdaper(Context context, ArrayList<ChanelItem> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    public void addAll(List<ChanelItem> data) {
        this.lstData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return lstData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_spiner_chanel, viewGroup, false);
            myViewHolder = new MyViewHolder(view);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        myViewHolder.init(lstData.get(position), position);
        return view;
    }

    public void clean() {
        this.lstData.clear();
        notifyDataSetChanged();
    }

    public void setPosition(int position) {
        poAnInt = position;
    }

    private class MyViewHolder {

        private TextView title;
        private TextView view_line;
        private ImageView thumb;

        public MyViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            view_line = (TextView) view.findViewById(R.id.view_line);
            thumb = (ImageView) view.findViewById(R.id.thumb);
        }

        public void init(ChanelItem item, int position) {
//            if (poAnInt == position) {
//                view_line.setVisibility(View.GONE);
//            } else {
//                view_line.setVisibility(View.VISIBLE);
//            }
            Glide.with(context)
                    .load(item.getImage().getThumbnailUrl())
                    .asBitmap()
                    .error(R.drawable.placeholder_ngang)
                    .placeholder(R.drawable.placeholder_ngang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(thumb);
            title.setText(item.getName());
            title.setTextColor(context.getResources().getColor(R.color.white));
        }
    }
}
