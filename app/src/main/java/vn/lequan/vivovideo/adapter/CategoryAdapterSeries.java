package vn.lequan.vivovideo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.Utils;
import com.smile.studio.network.ver2.model.ListSeries.ItemSeries;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.fragment.ListSeasonOfSeries;

/**
 * Created by admin on 16/08/2016.
 */
public class CategoryAdapterSeries extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Parcelable {

    private Context context;
    private List<ItemSeries> lstData;
    private Point point;
    private int numberColmns = 0;
    private FragmentManager fragmentManager;
    private boolean isVideo;
    private int size;
    private int lastAnimatedPosition = -1;
    private int lastPosition = -1;
    private boolean screenlist;


    public CategoryAdapterSeries(FragmentManager fragmentManager, Context context, List<ItemSeries> lstData, int numberColmns, boolean isVideo) {
        this.context = context;
        this.lstData = lstData;
        this.numberColmns = numberColmns;
        this.fragmentManager = fragmentManager;
        this.isVideo = isVideo;
        point = AndroidDeviceInfo.getScreenSize(context);
    }


    protected CategoryAdapterSeries(Parcel in) {
        point = in.readParcelable(Point.class.getClassLoader());
        numberColmns = in.readInt();
        isVideo = in.readByte() != 0;
        size = in.readInt();
        lastAnimatedPosition = in.readInt();
        lastPosition = in.readInt();
        screenlist = in.readByte() != 0;
    }

    public static final Creator<CategoryAdapterSeries> CREATOR = new Creator<CategoryAdapterSeries>() {
        @Override
        public CategoryAdapterSeries createFromParcel(Parcel in) {
            return new CategoryAdapterSeries(in);
        }

        @Override
        public CategoryAdapterSeries[] newArray(int size) {
            return new CategoryAdapterSeries[size];
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_video_fake, parent, false);
        if (numberColmns!=0){
            view.setLayoutParams(new ViewGroup.LayoutParams(point.x , point.x * 9 / 32 + Utils.convertDpToPixel(context, 10)));
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
//        holder.setIsRecyclable(false);
        myViewHolder.init(lstData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<ItemSeries> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public void add(ItemSeries data) {
        this.lstData.add(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }


    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public ItemSeries getItemAtPosition(int position) {
        return lstData.get(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(point, flags);
        dest.writeInt(numberColmns);
        dest.writeByte((byte) (isVideo ? 1 : 0));
        dest.writeInt(size);
        dest.writeInt(lastAnimatedPosition);
        dest.writeInt(lastPosition);
        dest.writeByte((byte) (screenlist ? 1 : 0));
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_thumb, gradient;
        private TextView title, sub_title;
        private MaterialRatingBar ratingbar;

        public MyViewHolder(View view) {
            super(view);
            img_thumb = (ImageView) view.findViewById(R.id.img_thumb);
//            gradient = (ImageView) view.findViewById(R.id.gradient);
            title = (TextView) view.findViewById(R.id.title);
            ratingbar = (MaterialRatingBar) view.findViewById(R.id.ratingbar);
            sub_title = (TextView) view.findViewById(R.id.sub_title);
        }


        @SuppressLint("SetTextI18n")
        public void init(final ItemSeries item, int po) {
            int i = Utils.random(3);
            ratingbar.setRating(item.getRating());
            title.setText(item.getName());
            sub_title.setText(item.getViewCount().toString() + " view");
            Glide.with(context)
                    .load(item.getImage().getThumbnailUrl())
//                    .load(GlobalApp.getInstance().getListImageMovie().get(Utils.random(GlobalApp.getInstance().getListImageMovie().size())))
                    .asBitmap()
                    .error(R.drawable.placeholder_ngang)
                    .placeholder(R.drawable.placeholder_ngang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(img_thumb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout).replace(R.id.content_main, ListSeasonOfSeries.newInstance(item.getId())).addToBackStack("back").commit();
                }
            });
        }

    }
}
