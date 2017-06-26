package vn.lequan.vivovideo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.model.ChanelItem.LiveChannelSchedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.R;

/**
 * Created by admin on 16/08/2016.
 */
public class TimeShiftVODAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<LiveChannelSchedule> lstData;
    private SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
    private int poisitoncheck = -1;
    private int size;

    public TimeShiftVODAdapter(Context context, List<LiveChannelSchedule> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_time_shift_vod, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(lstData.get(position), position);
    }

    public void setPoi(int i) {
        poisitoncheck = i;
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<LiveChannelSchedule> data) {
        this.lstData.clear();
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public void clean() {
        this.lstData.clear();
        notifyDataSetChanged();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_timestart, tv_title, tv_description;
        private ImageView icon_dot;

        public MyViewHolder(View view) {
            super(view);
            tv_timestart = (TextView) view.findViewById(R.id.tv_timestart);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            icon_dot = (ImageView) view.findViewById(R.id.icon_dot);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    tv_title.setSelected(true);
                    tv_description.setSelected(true);
                    return true;
                }
            });
        }

        public void init(LiveChannelSchedule item, int poisition) {
            tv_title.setText(item.getTitle().trim());
            if (poisitoncheck == poisition) {
                tv_timestart.setText("LIVE");
                icon_dot.setBackground(context.getResources().getDrawable(R.drawable.player_live_dot));
                tv_timestart.setTextColor(context.getResources().getColor(R.color.accent));
            } else {
                try {
                    icon_dot.setBackground(context.getResources().getDrawable(R.color.background_signon));
                    tv_timestart.setText(dateFormat2.format(dateFormat1.parse(item.getStartAt())));
                    tv_timestart.setTextColor(context.getResources().getColor(R.color.background_item_game));
                } catch (Exception e) {
                    Debug.e(e.toString());
                    tv_timestart.setText("");
                }
            }
            if (item.getDescription()!=null)
            tv_description.setText(item.getDescription().trim());
        }

    }

}
