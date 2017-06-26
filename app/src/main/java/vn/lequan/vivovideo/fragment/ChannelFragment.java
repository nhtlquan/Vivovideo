package vn.lequan.vivovideo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.recyclerviewer.EndlessRecyclerOnScrollListener;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_Chanel;
import com.smile.studio.network.ver2.model.ChanelItem.LiveChannel;
import com.smile.studio.network.ver2.model.ListChanel.ChanelItem;
import com.smile.studio.network.ver2.model.ListChanel.ListChanel;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.ChannelAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 16/08/2016.
 */
public class ChannelFragment extends Fragment {

    private View view;
    private LinearLayout root;
    private int page = 1, pagesize = 30;
    private int index = -1, numberColumns = 3;
    private RecyclerView recyclerView;
    private static final String category_ID = "categoryID";
    private GridLayoutManager layout;
    private ProgressBar progressBar;
    private ChannelAdapter chanelAdapterSub;

    public ChannelFragment() {
    }

    public static ChannelFragment newInstance(int categoryId) {
        Bundle args = new Bundle();
        args.putInt(category_ID, categoryId);
        ChannelFragment fragment = new ChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalApp.checktabfragment = true;
        getActivity().setTitle("");
    }

    @Override
    public void onPause() {
        super.onPause();
        GlobalApp.checktabfragment = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_chanel_test, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChanel);
            progressBar = (ProgressBar) view.findViewById(R.id.marker_progress);
            chanelAdapterSub = new ChannelAdapter(getChildFragmentManager(), getActivity(), new ArrayList<ChanelItem>(), 3);
            layout = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(layout);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.TRANSPARENT).build());
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.TRANSPARENT).build());
            recyclerView.setAdapter(chanelAdapterSub);
            getListChannel(getArguments().getInt(category_ID));
            loadMore();
        }
        return view;
    }

    private void loadMore() {
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout, page, pagesize) {
            @Override
            public void onLoadMore(int current_page, int totalItemsCount, RecyclerView view) {
                page = current_page;
                getListChannel(getArguments().getInt(category_ID));
            }
        });
    }

    private void getListChannel(int ID) {
        Call<ListChanel> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Chanel.class).getListChanelCategoryID(ID, page, pagesize, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<ListChanel>() {
            @Override
            public void onResponse(Call<ListChanel> call, Response<ListChanel> response) {
                try {
                    ListChanel value = response.body();
                    if (value.getData() != null) {
                        chanelAdapterSub.addAll(value.getData());
                        if (value.getData().size() > 0) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }, 300);
                        }
                    } else {
                        Toast.makeText(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListChanel> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }


}
