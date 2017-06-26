package vn.lequan.vivovideo.adapter;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.network.ver2.model.NavMenu.MenuItem;

import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.R;

/**
 * Created by admin on 18/08/2016.
 */
public class MenuChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MenuItem> lstData;
    private Point point;
    private int seleted = Integer.MIN_VALUE;
    private RequestManager manager;
    private int numberColmns = 0;
    private int size;

    public MenuChildAdapter(Context context, List<MenuItem> lstData) {
        this.context = context;
        this.lstData = lstData;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    public int getSeleted() {
        return seleted;
    }

    public void setSeleted(int seleted) {
        this.seleted = seleted;
    }

    public MenuChildAdapter(Context context, ArrayList<MenuItem> lstData, int numberColmns) {
        this.context = context;
        this.lstData = lstData;
        this.numberColmns = numberColmns;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(position, lstData.get(position));
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<MenuItem> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyDataSetChanged();
    }

    public void clean() {
        lstData.clear();
    }

    public MenuItem getItemAtPosition(int position) {
        return lstData.get(position);
    }

    public void selectItemPosition(int position) {
        if (seleted != position) {
            notifyItemChanged(seleted);
            seleted = position;
            notifyItemChanged(seleted);
        }

    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumb;
        private TextView title;

        public MyViewHolder(View view) {
            super(view);
            thumb = (ImageView) view.findViewById(R.id.thumb);
            title = (TextView) view.findViewById(R.id.title);
        }

        public void init(final int position, MenuItem item) {
            title.setText(item.getName());
//            if (item.getActionClick().getClickable() == 0) {
//                title.setTextSize(24);
//                title.setTextColor(context.getResources().getColor(R.color.text_disabled));
//            } else {
//                title.setTextSize(16);
//                title.setTextColor(context.getResources().getColor(R.color.white));
//            }
            Typeface font_bold = Typeface.createFromAsset(
                    context.getAssets(),
                    "fonts/Roboto-Bold.ttf");
            Typeface font_light = Typeface.createFromAsset(
                    context.getAssets(),
                    "fonts/Roboto-Light.ttf");
            if (seleted == position) {
                title.setTypeface(font_bold);
                thumb.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                title.setTypeface(font_light);
                thumb.setBackgroundColor(context.getResources().getColor(R.color.background_menu));
            }
        }

    }
}