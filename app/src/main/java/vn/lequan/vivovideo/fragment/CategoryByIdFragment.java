package vn.lequan.vivovideo.fragment;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.smile.studio.network.ver2.face.Face_CategoryID;
import com.smile.studio.network.ver2.model.BetMatch.BetItem;
import com.smile.studio.network.ver2.model.CategoryById.CategoryByID;
import com.smile.studio.network.ver2.model.CategoryById.ItemCategory;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.CategoryIDAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

public class CategoryByIdFragment extends Fragment {

    private View view;
    private ViewPager viewPager = null;
    private Point point = null;
    private LinearLayout root = null;
    private GridLayoutManager layout;
    private Timer timer = null;
    private int page = 1, pageSize = 10, numberColumns = 3;
    private int dem;
    private static final String OBJECT = "object";
    private static final String OB_type = "TYPE";
    private RecyclerView recyclerView;
    private BetItem betItem;
    private CategoryIDAdapter categoryAdapter;
    private ProgressBar marker_progress;

    public CategoryByIdFragment() {
    }

    public static CategoryByIdFragment newInstance(int id, String type) {
        Bundle args = new Bundle();
        args.putInt(OBJECT, id);
        args.putString(OB_type, type);
        CategoryByIdFragment fragment = new CategoryByIdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CategoryByIdFragment newInstance() {
        Bundle args = new Bundle();
        CategoryByIdFragment fragment = new CategoryByIdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_categoryid, container, false);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            layout = new GridLayoutManager(getActivity(), numberColumns);
            categoryAdapter = new CategoryIDAdapter(getContext(), new ArrayList<ItemCategory>(), numberColumns);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(categoryAdapter);
//            recyclerView.setNestedScrollingEnabled(false);
            getData(getArguments().getInt(OBJECT), getArguments().getString(OB_type));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Debug.e("Phệt");
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            loadMore();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        GlobalApp.getInstance().fragment = CategoryByIdFragment.newInstance();
    }

    private void loadMore() {
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout, page, pageSize) {
            @Override
            public void onLoadMore(int current_page, int totalItemsCount, RecyclerView view) {
                page = current_page;
                getData(getArguments().getInt(OBJECT), getArguments().getString(OB_type));
            }
        });
    }
    private void getData(int ID, String type) {
        Call<CategoryByID> call = GlobalApp.getInstance().retrofit_ver2.create(Face_CategoryID.class).getCategoryById(type,page, pageSize,  GlobalApp.getInstance().Platform, ID, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<CategoryByID>() {
            @Override
            public void onResponse(Call<CategoryByID> call, Response<CategoryByID> response) {
                try {
                    CategoryByID value = response.body();
                    if (value.getData()!=null) {
                        categoryAdapter.addAll(value.getData());
                        marker_progress.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CategoryByID> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

}
