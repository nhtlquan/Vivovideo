package vn.lequan.vivovideo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.recyclerviewer.EndlessRecyclerOnScrollListener;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_Movie;
import com.smile.studio.network.ver2.model.MovieCategory.MovieCategory;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.MovieAdapterHome;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 17/08/2016.
 */
public class MovieFragmentByCategory extends Fragment {

    private static final String category_ID = "categoryID";
    private View view;
    private int index = -1, page = 1, pagesize = 10, numberColumns = 3;
    private GridLayoutManager layout;
    private RecyclerView recyclerView;
    private MovieAdapterHome adapter;
    private ImageView icon_title;
    private TextView title;

    public MovieFragmentByCategory() {
    }

    public static MovieFragmentByCategory newInstance(int categoryID) {
        Bundle args = new Bundle();
        args.putInt(category_ID, categoryID);
        MovieFragmentByCategory fragment = new MovieFragmentByCategory();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_item_home_test, container, false);
            icon_title = (ImageView) view.findViewById(R.id.icon_title);
            title = (TextView) view.findViewById(R.id.title);
            icon_title.setVisibility(View.GONE);
//            layout = new
            title.setVisibility(View.GONE);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getContext()).color(Color.TRANSPARENT).sizeResId(R.dimen.padding1).build());
            adapter = new MovieAdapterHome(getChildFragmentManager(), getActivity(), new ArrayList<ScreenMenuItem>(), numberColumns, false);
            recyclerView.setAdapter(adapter);
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
        Call<MovieCategory> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Movie.class).getMovieCategory(genreID, page, pagesize, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<MovieCategory>() {
            @Override
            public void onResponse(Call<MovieCategory> call, Response<MovieCategory> response) {
                try {
                    if (response.body().getCode()==-1){
                        Toast.makeText(getActivity(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MovieCategory value = response.body();
                    adapter.addAll(value.getData());
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieCategory> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }
}
