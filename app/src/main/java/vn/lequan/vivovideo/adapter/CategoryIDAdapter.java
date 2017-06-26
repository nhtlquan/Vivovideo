package vn.lequan.vivovideo.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.libsmilestudio.utils.Utils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.model.CategoryById.ItemCategory;

import java.util.List;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.AccountActivity;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.model.GlobalApp;

public class CategoryIDAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ItemCategory> lstData;
    private Point point;
    private RequestManager manager;
    private int numberColmns = 2;
    private FragmentManager fragmentManager;
    private boolean isVideo;
    private ProgressDialog pDialog;
    private int lastPosition = -1;

    public CategoryIDAdapter(Context context, List<ItemCategory> lstData, int numberColmns) {
        this.context = context;
        this.lstData = lstData;
        this.numberColmns = numberColmns;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_movie_sub, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(point.x / numberColmns, point.x / numberColmns * 3 / 2));
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

    public void addAll(List<ItemCategory> data) {
        this.lstData.addAll(data);
        int size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public ItemCategory getItemAtPosition(int position) {
        return lstData.get(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RecyclerView recyclerView;
        private ImageView thumb;

        public MyViewHolder(View view) {
            super(view);
            thumb = (ImageView) view.findViewById(R.id.thumb);
        }

        public void init(final ItemCategory item, int po) {
            Glide.with(context)
                    .load(item.getImage().getThumbnailUrl())
//                    .load(GlobalApp.getInstance().getListImageMovie().get(Utils.random(GlobalApp.getInstance().getListImageMovie().size())))
                    .asBitmap()
                    .error(R.drawable.placeholder_ngang)
                    .placeholder(R.drawable.placeholder_ngang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(thumb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!SharedPreferencesUtils.getBoolean(context, API.checkLogin)) {
                        Intent intent = new Intent(context, AccountActivity.class);
                        ((Activity) context).startActivityForResult(intent,  MainActivity.RESULTCODE_LOGIN_ACCOUNT);
                        return;
                    }
                        MainActivity.openDraggableView();
                        MainActivity.loadmoviehomesub(item.getId());
                }
            });
        }

    }
}
