package vn.lequan.vivovideo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.R;


public class PakageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> lstData;
    private Point point;
    private RequestManager manager;
    private int numberColmns = 2;
    private int size;

    public PakageAdapter(Context context, List<String> lstData) {
        this.context = context;
        this.lstData = lstData;
        point = AndroidDeviceInfo.getScreenSize(context);
        manager = Glide.with(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pakage_service, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(point.x / numberColmns - Utils.convertDpToPixel(context, 30) , point.x / numberColmns * 6 / 17));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(lstData.get(position), position);
    }


    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(ArrayList<String> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public String getItemAtPosition(int position) {
        return lstData.get(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title_pakage;

        public MyViewHolder(View view) {
            super(view);
            title_pakage = (TextView) view.findViewById(R.id.title_pakage);
        }

        public void init(final String item, int i) {
            title_pakage.setText(item);
        }

    }
}
