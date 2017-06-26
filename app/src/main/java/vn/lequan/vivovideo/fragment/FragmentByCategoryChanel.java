package vn.lequan.vivovideo.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_CategoryID;
import com.smile.studio.network.ver2.model.SeriesCategory.ChildCategory;
import com.smile.studio.network.ver2.model.SeriesCategory.SeriesCategory;

import java.util.ArrayList;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.ChanelAdapterCategory;
import vn.lequan.vivovideo.adapter.SeriesAdapterCategory;
import vn.lequan.vivovideo.model.GlobalApp;

public class FragmentByCategoryChanel extends Fragment {

    private View view;
    private ViewPager viewPager = null;
    private Point point = null;
    private Timer timer = null;
    private int dem;
    private static final String OBJECT = "object";
    private RecyclerView recyclerView;
    private ChanelAdapterCategory seriesAdapterCategory;
    private boolean check = false;
    private ProgressBar marker_progress;

    public FragmentByCategoryChanel() {
    }

    public static FragmentByCategoryChanel newInstance(int ID) {
        Bundle args = new Bundle();
        args.putInt(OBJECT, ID);
        FragmentByCategoryChanel fragment = new FragmentByCategoryChanel();
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentByCategoryChanel newInstance() {
        Bundle args = new Bundle();
        FragmentByCategoryChanel fragment = new FragmentByCategoryChanel();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        GlobalApp.getInstance().fragment = FragmentByCategory.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            Debug.e("Chanel");
            view = inflater.inflate(R.layout.fragment_category_by_id, container, false);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            marker_progress.setVisibility(View.VISIBLE);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            seriesAdapterCategory = new ChanelAdapterCategory(getFragmentManager(), getActivity(), new ArrayList<ChildCategory>());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(seriesAdapterCategory);
            getData(getArguments().getInt(OBJECT));
        }
        return view;
    }

    private void getData(int ID) {
        Call<SeriesCategory> call = GlobalApp.getInstance().retrofit_ver2.create(Face_CategoryID.class).getListCategory(ID, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<SeriesCategory>() {
            @Override
            public void onResponse(Call<SeriesCategory> call, Response<SeriesCategory> response) {
                try {
                    SeriesCategory value = response.body();
                    seriesAdapterCategory.addAll(value.getData().getCategoryTypeName(), value.getData().getChildren());
                    marker_progress.setVisibility(View.GONE);
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SeriesCategory> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }


}
