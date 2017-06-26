package vn.lequan.vivovideo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smile.studio.network.ver2.model.ChanelItem.ItemChanel;
import com.smile.studio.network.ver2.model.ChanelItem.LiveChannel;
import com.smile.studio.network.ver2.model.ListChanel.ChanelItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.ChannelAdapter;

/**
 * Created by admin on 20/08/2016.
 */
public class RelationChannelFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager layout;
    private ChannelAdapter adapter;
    private static String Itemchanel = "itemChanel";
    private final int numberColums = 3;
    private int page = 1, firstVisibleItem, visibleItemCount, totalItemCount, previousTotal, pagesize = 20;
    private boolean loading;

    public RelationChannelFragment() {
    }

    public static RelationChannelFragment newInstance(ItemChanel channelID) {
        Bundle args = new Bundle();
        args.putParcelable(Itemchanel, channelID);
        RelationChannelFragment fragment = new RelationChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_row_title_recyclerview, container, false);
            view.findViewById(R.id.title).setVisibility(View.GONE);
            view.findViewById(R.id.icon_title).setVisibility(View.GONE);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            layout = new GridLayoutManager(getActivity(), numberColums);
            recyclerView.setLayoutManager(layout);
            adapter = new ChannelAdapter(getChildFragmentManager(), getActivity(), new ArrayList<ChanelItem>(), numberColums);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.TRANSPARENT).build());
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.TRANSPARENT).build());
            ItemChanel itemChanel = getArguments().getParcelable(Itemchanel);
            loadData(itemChanel);
//            loadMore();
        }
        return view;
    }

    private void loadMore() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layout.getItemCount();
                firstVisibleItem = layout.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + pagesize)) {
                    page++;
                    loading = true;
                    ItemChanel itemChanel = getArguments().getParcelable(Itemchanel);
                    loadData(itemChanel);
                }
            }
        });
    }

    private void loadData(ItemChanel channelID) {
        adapter.addAll(channelID.getData().getRelatedChannels());
    }


}
