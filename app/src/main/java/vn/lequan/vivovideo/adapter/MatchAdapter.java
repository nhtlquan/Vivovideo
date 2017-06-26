package vn.lequan.vivovideo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.model.DuDoan;
import com.smile.studio.network.ver2.model.BetMatch.Match;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.DuDoanActivity;
import vn.lequan.vivovideo.activity.MainActivity;

@SuppressLint("SimpleDateFormat")
public class MatchAdapter extends Adapter<ViewHolder> {

    private Context context;
    private ArrayList<Match> lstData;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private SimpleDateFormat dateFormat4 = new SimpleDateFormat("HH:mm");

    public MatchAdapter(Context context, ArrayList<Match> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<Match> data) {
        this.lstData.addAll(data);
        int size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(lstData.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_match, parent, false));
    }

    private class MyViewHolder extends ViewHolder {

        private CircularImageView thumb1, thumb2;
        private TextView team1, team2;
        private TextView  titso;
        private TextView btn_dudoan;

        public MyViewHolder(View view) {
            super(view);
            thumb1 = (CircularImageView) view.findViewById(R.id.thumb_team1);
            thumb2 = (CircularImageView) view.findViewById(R.id.thumb_team2);
            team1 = (TextView) view.findViewById(R.id.team1);
            team2 = (TextView) view.findViewById(R.id.team2);
            titso = (TextView) view.findViewById(R.id.titso);
            btn_dudoan = (TextView) view.findViewById(R.id.btn_dudoan);
            team1.setSelected(true);
            team2.setSelected(true);
        }

        public void init(final Match item) {
            btn_dudoan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    try {
                        String s_date = dateFormat2.format(dateFormat.parse(item.getStartAt()));
                        String time = dateFormat4.format(dateFormat.parse(item.getStartAt()));
                        Intent intent = new Intent(context, DuDoanActivity.class);
                        intent.putExtra(DuDoan.MATCHID, item.getId());
                        intent.putExtra(DuDoan.MATCH, item.getClubA().getName() + " vs " + item.getClubB().getName());
                        intent.putExtra(DuDoan.TIME, time + ", " + "ngày " + s_date);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (ParseException e) {
                        Debug.e("Lỗi: " + e.toString());
                    }

                }
            });

//            try {
//                dateFormat3.setTimeZone(TimeZone.getTimeZone("UTC"));
//                Date dateServer = dateFormat3.parse(item.getServerDateTime());
//                Date dateStart = dateFormat.parse(item.getStartDay());
//                if (dateServer.getTime() < dateStart.getTime()) {
//                    titso.setText(dateFormat4.format(dateStart));
//                } else {
//                    titso.setText(item.getTeamAScore() + " - " + item.getTeamBScore());
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            titso.setText(item.getTeamAScore() + " - " + item.getTeamBScore());
//            if (item.getStatus() == 0) {
//                btn_dudoan.setBackgroundResource(R.drawable.background_button_dudoan_blue);
//            } else {
//                btn_dudoan.setBackgroundResource(R.drawable.background_button_dudoan_white);
//            }
//            table.setText(item.get);
            team1.setText(item.getClubA().getName());
            team2.setText(item.getClubB().getName());
            Glide.with(context)
                    .load(item.getClubA().getThumbnailUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.ic_flag_defalut)
                    .placeholder(R.mipmap.ic_flag_defalut)
                    .thumbnail(0.1f)
                    .animate(R.anim.fadein).dontAnimate()
                    .into(thumb1);
            Glide.with(context)
                    .load(item.getClubB().getThumbnailUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.ic_flag_defalut)
                    .placeholder(R.mipmap.ic_flag_defalut)
                    .thumbnail(0.1f)
                    .animate(R.anim.fadein).dontAnimate()
                    .into(thumb2);
        }

    }

}
