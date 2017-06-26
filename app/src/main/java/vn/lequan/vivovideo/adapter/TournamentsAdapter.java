package vn.lequan.vivovideo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.network.ver2.model.BetMatch.Match;
import com.smile.studio.network.ver2.model.BetMatch.Tournament;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.R;

public class TournamentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Tournament> lstData;
    private Point point;
    private RequestManager manager;
    private int numberColmns = 2;
    private FragmentManager fragmentManager;
    private boolean isVideo;
    private ProgressDialog pDialog;
    private int lastPosition = -1;

    public TournamentsAdapter(Context context, List<Tournament> lstData, int numberColmns) {
        this.context = context;
        this.lstData = lstData;
        this.numberColmns = numberColmns;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_home_test, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(lstData.get(position));
    }


    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<Tournament> data) {
        this.lstData.addAll(data);
        int size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public Tournament getItemAtPosition(int position) {
        return lstData.get(position);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RecyclerView recyclerView;
        private ImageView icon_title;
        private RelativeLayout layout_title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            layout_title = (RelativeLayout) view.findViewById(R.id.layout_title);
            icon_title = (ImageView) view.findViewById(R.id.icon_title);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(context.getApplicationContext()).color(Color.TRANSPARENT).sizeResId(R.dimen.padding1).build());
        }

        public void init(final Tournament item) {
            if (item.getMatches() != null) {
                icon_title.setImageResource(R.drawable.ic_gamedudoan);
                title.setText(item.getName());
                MatchAdapter matchAdapter = new MatchAdapter(context.getApplicationContext(), new ArrayList<Match>());
                recyclerView.setAdapter(matchAdapter);
                matchAdapter.addAll(item.getMatches());
            } else {
                layout_title.setVisibility(View.GONE);
            }
        }

    }
}
