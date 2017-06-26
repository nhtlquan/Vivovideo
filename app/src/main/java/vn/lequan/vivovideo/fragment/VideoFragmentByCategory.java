package vn.lequan.vivovideo.fragment;

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
import com.smile.studio.network.ver2.face.Face_Video;
import com.smile.studio.network.ver2.model.VideoByCategory.VideoByCategory;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.VideoAdapterList;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 17/08/2016.
 */
public class VideoFragmentByCategory extends Fragment {

    private static final String category_ID = "categoryID";
    private View view;
    private int index = -1, page = 1, pagesize = 10, numberColumns = 1;
    private GridLayoutManager layout;
    private RecyclerView recyclerView;
    private VideoAdapterList adapter;
    private ProgressBar marker_progress;
    private RelativeLayout layout_title;

    public VideoFragmentByCategory() {
    }

    public static VideoFragmentByCategory newInstance(int categoryID) {
        Bundle args = new Bundle();
        args.putInt(category_ID, categoryID);
        VideoFragmentByCategory fragment = new VideoFragmentByCategory();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_item_home_test, container, false);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            marker_progress.setVisibility(View.VISIBLE);
            layout_title = (RelativeLayout) view.findViewById(R.id.layout_title);
            layout_title.setVisibility(View.GONE);
            layout = new GridLayoutManager(getActivity(), numberColumns);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            adapter = new VideoAdapterList(getChildFragmentManager(), getActivity(), new ArrayList<ScreenMenuItem>(), numberColumns, true);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layout);
            getListVideosByCategory(getArguments().getInt(category_ID));
            loadMore();
        }
        return view;
    }


    private void loadMore() {
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout, page, pagesize) {
            @Override
            public void onLoadMore(int current_page, int totalItemsCount, RecyclerView view) {
                page = current_page;
                getListVideosByCategory(getArguments().getInt(category_ID));
            }
        });
    }

    private void getListVideosByCategory(int genreID) {
        Call<VideoByCategory> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Video.class).getVideoByCategory(genreID, page, pagesize, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<VideoByCategory>() {
            @Override
            public void onResponse(Call<VideoByCategory> call, Response<VideoByCategory> response) {
                try {
                    VideoByCategory value = response.body();
                    marker_progress.setVisibility(View.GONE);
                    for (ScreenMenuItem screenMenuItem : value.getData().getItems()) {
                        screenMenuItem.setType("videos");
                    }
                    adapter.addAll(value.getData().getItems());
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<VideoByCategory> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }
}
