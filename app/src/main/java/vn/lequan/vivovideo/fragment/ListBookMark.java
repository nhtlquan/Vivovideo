package vn.lequan.vivovideo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.smile.studio.libsmilestudio.fragment.TabFragmentAdapter;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_BookMark;
import com.smile.studio.network.ver2.model.BetMatch.BetItem;
import com.smile.studio.network.ver2.model.ListBookMark.ItemListBookMark;
import com.smile.studio.network.ver2.model.ListBookMark.ModelListBookMark;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.ListBookMarkAdapter;
import vn.lequan.vivovideo.adapter.VideoAdapterListBookMark;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;

/**
 * Created by admin on 17/08/2016.
 */
public class ListBookMark extends Fragment {

    private View view;
    private static final String OBJECT = "object";
    private VideoAdapterListBookMark listBookMarkAdapter;
    private ListBookMarkAdapter listBookMarkAdapter1;
    private VideoAdapterListBookMark listBookMarkAdapter2;
    private int page = 1, pagesize = 50;
    private int numberColumns = 2;
    private ProgressBar marker_progress;
    private LinkedHashMap<Integer, Fragment> mapFragments;


    public ListBookMark() {
    }

    public static ListBookMark newInstance() {
        Bundle args = new Bundle();
        ListBookMark fragment = new ListBookMark();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //        GlobalApp.getInstance().fragment = ListBookMark.newInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_video_clips_hot, container, false);
            mapFragments = new LinkedHashMap<Integer, Fragment>();
            BetItem betItem = getArguments().getParcelable(OBJECT);
            listBookMarkAdapter1 = new ListBookMarkAdapter(getChildFragmentManager(), getContext(), new ArrayList<ItemListBookMark>(), 3, false);
            listBookMarkAdapter = new VideoAdapterListBookMark(getChildFragmentManager(), getContext(), new ArrayList<ItemListBookMark>(), 3, false);
            listBookMarkAdapter2 = new VideoAdapterListBookMark(getChildFragmentManager(), getContext(), new ArrayList<ItemListBookMark>(), 3, false);
            mapFragments.put(R.string.video, BookMarkFragment.newInstance(listBookMarkAdapter, TYPEITEM.VIDEO.getValue()));
            mapFragments.put(R.string.movie_signle, BookMarkFragment.newInstance(listBookMarkAdapter1, TYPEITEM.MOVIE.getValue()));
            mapFragments.put(R.string.series, BookMarkFragment.newInstance(listBookMarkAdapter2, TYPEITEM.SERIES.getValue()));
            TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getContext(), getChildFragmentManager(),
                    mapFragments);
            final ViewPager pager = (ViewPager) view.findViewById(R.id.viewpager1);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            marker_progress.setVisibility(View.VISIBLE);
            ViewGroup tab = (ViewGroup) view.findViewById(R.id.tab);
            tab.addView(LayoutInflater.from(getContext()).inflate(R.layout.demo_always_in_center, tab, false));
            final SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
            pager.setAdapter(tabFragmentAdapter);
            pager.setOffscreenPageLimit(0);
            viewPagerTab.setViewPager(pager);
            getListBookMark();
        }
        return view;
    }

    private void getListBookMark() {
        Call<ModelListBookMark> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BookMark.class).getListBookmark(page, pagesize, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<ModelListBookMark>() {
            @Override
            public void onResponse(Call<ModelListBookMark> call, Response<ModelListBookMark> response) {
                try {
                    List<ItemListBookMark> value = response.body().getData();
                    marker_progress.setVisibility(View.GONE);
                    for (ItemListBookMark screenMenuItems : value) {
                        switch (screenMenuItems.getType()) {
                            case "videos":
                                listBookMarkAdapter.add(screenMenuItems);
                                break;
                            case "movies":
                                listBookMarkAdapter1.add(screenMenuItems);
                                break;
                            case "episodes":
                                listBookMarkAdapter2.add(screenMenuItems);
                                break;
                        }
                    }
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ModelListBookMark> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");
    }


}
