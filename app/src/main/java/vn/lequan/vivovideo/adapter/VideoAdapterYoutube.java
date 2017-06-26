package vn.lequan.vivovideo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.Utils;
import com.smile.studio.network.Detail_Video.VideoDetail;
import com.smile.studio.network.Face.Face_Youtube;
import com.smile.studio.network.ListVideoChanel.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TimeZone;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.fragment.ListSeasonOfSeries;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 16/08/2016.
 */
@SuppressLint("ParcelCreator")
public class VideoAdapterYoutube extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Parcelable {

    private Context context;
    private List<Item> lstData;
    private Point point;
    private int numberColmns = 0;
    private FragmentManager fragmentManager;
    private int size;
    private boolean screenlist;
    private int lastPosition;


    public VideoAdapterYoutube(FragmentManager fragmentManager, Context context, List<Item> lstData, int numberColmns, boolean screenlist) {
        this.context = context;
        this.lstData = lstData;
        this.numberColmns = numberColmns;
        this.fragmentManager = fragmentManager;
        this.screenlist = screenlist;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_video, parent, false);
        if (screenlist) {
            view.setLayoutParams(new ViewGroup.LayoutParams(point.x, point.x * 9 / 16));
        } else {
            view.setLayoutParams(new ViewGroup.LayoutParams(point.x / 2 + Utils.convertDpToPixel(context, 20), point.x / 2 * 9 / 16 + Utils.convertDpToPixel(context, 20)));
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
//        holder.setIsRecyclable(false);
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        lastPosition = position;
        holder.itemView.startAnimation(animation);
        lastPosition = position;
        myViewHolder.init(lstData.get(position), position);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<Item> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public void add(Item data) {
        this.lstData.add(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }


    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public Item getItemAtPosition(int position) {
        return lstData.get(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_thumb, gradient;
        private TextView title, time_publish, view_count, title_chanel;

        public MyViewHolder(View view) {
            super(view);
            img_thumb = (ImageView) view.findViewById(R.id.img_thumb);
            gradient = (ImageView) view.findViewById(R.id.gradient);
            title = (TextView) view.findViewById(R.id.title);
            time_publish = (TextView) view.findViewById(R.id.time_publish);
            view_count = (TextView) view.findViewById(R.id.view_count);
            title_chanel = (TextView) view.findViewById(R.id.title_chanel);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    title.setSelected(true);
                    return true;
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void init(final Item item, int po) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");// set date time of comment
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                Date date = (Date) sdf.parse(item.getSnippet().getPublishedAt());
                time_publish.setText(converteTimestamp(String.valueOf(date.getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int i = 0;
            title_chanel.setText(item.getSnippet().getChannelTitle().replace("Muvik", "Vivo").replace("muvik","vivo"));
            i = context.getApplicationContext().getResources().getColor(R.color.background_signon);
            Object localObject2 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{i, 0});
            gradient.setBackground((Drawable) localObject2);
            title.setText(item.getSnippet().getTitle().replace("Muvik", "Vivo").replace("muvik","vivo"));
            Glide.with(context)
                    .load("http://i3.ytimg.com/vi/" + item.getId().getVideoId() + "/maxresdefault.jpg")
                    .asBitmap()
                    .error(R.drawable.placeholder_ngang)
                    .placeholder(R.drawable.placeholder_ngang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(img_thumb);

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
                                    MainActivity.loadvideoYoutbe(item.getId().getVideoId());
                                }

                                @Override
                                public void onAnimationCancel(final View view) {

                                }
                            })
                            .withLayer()
                            .start();
                }
            });
            view_count.setText(format(Utils.random(100000000)));
//            getDetailVideo(item.getId().getVideoId(),view_count);
        }

        private CharSequence converteTimestamp(String mileSegundos) {
            return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSegundos), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        }

        public class CycleInterpolator implements android.view.animation.Interpolator {

            private final float mCycles = 0.5f;

            @Override
            public float getInterpolation(final float input) {
                return (float) Math.sin(2.0f * mCycles * Math.PI * input);
            }
        }
    }

    private void getDetailVideo(String videoId, final TextView view_count) {
            Call<VideoDetail> call = GlobalApp.getInstance().retrofit.create(Face_Youtube.class).getDetailVideo(GlobalApp.KEY, videoId, "statistics", GlobalApp.ODER, GlobalApp.maxResults);
            call.enqueue(new Callback<VideoDetail>() {
                @Override
                public void onResponse(Call<VideoDetail> call, Response<VideoDetail> response) {
                    try {
                        view_count.setText(format(Long.parseLong(response.body().getItems().get(0).getStatistics().getViewCount())));
                    } catch (Exception e) {
                        com.smile.studio.network.utils.Debug.e("Lỗi: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<VideoDetail> call, Throwable t) {
                    com.smile.studio.libsmilestudio.utils.Debug.e("Lỗi: " + t.getMessage());
                }
            });
    }
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
