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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.smile.studio.libsmilestudio.recyclerviewer.EndlessRecyclerOnScrollListener;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_Series;
import com.smile.studio.network.ver2.model.ListSeries.ItemSeries;
import com.smile.studio.network.ver2.model.ListSeries.ListSeries;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.CategoryAdapterSeries;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 17/08/2016.
 */
public class SeriesFragmentByID extends Fragment {

    private static final String category_ID = "categoryID";
    private View view;
    private int index = -1, page = 1, pagesize = 10, numberColumns = 1;
    private RecyclerView recyclerView;
    private CategoryAdapterSeries adapter;
    private GridLayoutManager layout;
    private RelativeLayout layout_title;
    private ProgressBar marker_progress;

    public SeriesFragmentByID() {
    }

    public static SeriesFragmentByID newInstance(int categoryID) {
        Bundle args = new Bundle();
        args.putInt(category_ID, categoryID);
        SeriesFragmentByID fragment = new SeriesFragmentByID();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_item_home_test, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            layout_title = (RelativeLayout) view.findViewById(R.id.layout_title);
            layout_title.setVisibility(View.GONE);
            marker_progress.setVisibility(View.VISIBLE);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getContext()).color(Color.TRANSPARENT).sizeResId(R.dimen.padding1).build());
            layout = new GridLayoutManager(getActivity(), numberColumns);
            recyclerView.setLayoutManager(layout);
            adapter = new CategoryAdapterSeries(getFragmentManager(), getActivity(), new ArrayList<ItemSeries>(), numberColumns, false);
            recyclerView.setAdapter(adapter);
            getListSeriesByCategory(getArguments().getInt(category_ID));
            loadMore();
        }
        return view;
    }


    private void loadMore() {
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout, page, pagesize) {
            @Override
            public void onLoadMore(int current_page, int totalItemsCount, RecyclerView view) {
                page = current_page;
                getListSeriesByCategory(getArguments().getInt(category_ID));
            }
        });
    }

    private void getListSeriesByCategory(int genreID) {
        Call<ListSeries> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Series.class).getListSeries(page, pagesize, genreID, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<ListSeries>() {
            @Override
            public void onResponse(Call<ListSeries> call, Response<ListSeries> response) {
                try {
                    ListSeries value = response.body();
                    adapter.addAll(value.getData());
                    marker_progress.setVisibility(View.GONE);
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListSeries> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }
}
