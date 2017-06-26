package vn.lequan.vivovideo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.smile.studio.libsmilestudio.fragment.TabFragmentAdapter;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_Search;
import com.smile.studio.network.ver2.model.Search.Search;
import com.smile.studio.network.ver2.model.Search.SearchItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.ListSearchAdapter;
import vn.lequan.vivovideo.adapter.VideoAdapterListSearch;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;

/**
 * Created by admin on 17/08/2016.
 */
public class SearchResultFragment extends Fragment {

    private View view;
    private static final String OBJECT = "object";
    private static final String KEYWORD = "keyword";
    private VideoAdapterListSearch ListSearchAdapter;
    private int page = 1;
    private int pagSize = 50;
    private ListSearchAdapter ListSearchAdapter1;
    private VideoAdapterListSearch ListSearchAdapter2;
    private int numberColumns = 2;
    private ProgressBar marker_progress;
    private LinkedHashMap<Integer, Fragment> mapFragments;


    public SearchResultFragment() {
    }

    public static SearchResultFragment newInstance(String keyword) {
        Bundle args = new Bundle();
        args.putString(KEYWORD, keyword);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //        GlobalApp.getInstance().fragment = SearchResultFragment.newInstance(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_video_clips_hot, container, false);
            mapFragments = new LinkedHashMap<Integer, Fragment>();
            ListSearchAdapter1 = new ListSearchAdapter(getFragmentManager(), getContext(), new ArrayList<SearchItem>(), 3, false);
            ListSearchAdapter = new VideoAdapterListSearch(getFragmentManager(), getContext(), new ArrayList<SearchItem>(), 3, false);
            ListSearchAdapter2 = new VideoAdapterListSearch(getFragmentManager(), getContext(), new ArrayList<SearchItem>(), 3, false);
            mapFragments.put(R.string.video, SearchFragment.newInstance(ListSearchAdapter, TYPEITEM.VIDEO.getValue()));
            mapFragments.put(R.string.movie_signle, SearchFragment.newInstance(ListSearchAdapter1, TYPEITEM.MOVIE.getValue()));
            mapFragments.put(R.string.series, SearchFragment.newInstance(ListSearchAdapter2, TYPEITEM.SERIES.getValue()));
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
            getListBookMark(getArguments().getString(KEYWORD));
        }
        return view;
    }

    private void getListBookMark(String keyword) {
        Call<Search> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Search.class).getListSearch(keyword, page, pagSize, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                    if (response.body().getCode() == 0) {
                        List<SearchItem> value = response.body().getData();
                        for (SearchItem screenMenuItems : value) {
                            switch (screenMenuItems.getType()) {
                                case "videos":
                                    ListSearchAdapter.add(screenMenuItems);
                                    break;
                                case "movies":
                                    ListSearchAdapter1.add(screenMenuItems);
                                    break;
                                case "series":
                                    ListSearchAdapter2.add(screenMenuItems);
                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                    }
                    marker_progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
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
