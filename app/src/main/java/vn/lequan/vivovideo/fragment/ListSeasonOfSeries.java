package vn.lequan.vivovideo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.smile.studio.network.ver2.face.Face_SeasonOfSeries;
import com.smile.studio.network.ver2.model.SeasonOfSeries.Episode;
import com.smile.studio.network.ver2.model.SeasonOfSeries.ItemSeasonOfSeries;
import com.smile.studio.network.ver2.model.SeasonOfSeries.SeasonOfSeries;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.SeriesItemAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

@SuppressLint("UseSparseArrays")
public class ListSeasonOfSeries extends Fragment {
    public static String OBJECT = "object";
    private LinkedHashMap<String, Fragment> mapFragments;
    private SeriesItemAdapter seriesAdapter;
    private ViewPager pager;
    private SmartTabLayout viewPagerTab;
    private ProgressBar marker_progress;

    public static ListSeasonOfSeries newInstance(int ID) {
        Bundle args = new Bundle();
        args.putInt(OBJECT, ID);
        ListSeasonOfSeries fragment = new ListSeasonOfSeries();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_clips_hot, container, false);
        marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
        marker_progress.setVisibility(View.VISIBLE);
        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = (ViewPager) view.findViewById(R.id.viewpager1);
        ViewGroup tab = (ViewGroup) view.findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(getContext()).inflate(R.layout.demo_always_in_center, tab, false));
        viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        mapFragments = new LinkedHashMap<String, Fragment>();
        getListSeasonOfSeries(getArguments().getInt(OBJECT));
//        pager.setOffscreenPageLimit(0);

    }

    private void getListSeasonOfSeries(final int ID) {
        Call<SeasonOfSeries> call = GlobalApp.getInstance().retrofit_ver2.create(Face_SeasonOfSeries.class).getListSeason(ID, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<SeasonOfSeries>() {
            @Override
            public void onResponse(Call<SeasonOfSeries> call, Response<SeasonOfSeries> response) {
                try {
                    if (response.body().getData() != null) {
                        List<ItemSeasonOfSeries> value = response.body().getData();
                        for (ItemSeasonOfSeries itemSeasonOfSeries : value) {
                            seriesAdapter = new SeriesItemAdapter(getContext(), new ArrayList<Episode>(), 1, false);
                            seriesAdapter.addAll(itemSeasonOfSeries.getEpisodes(), ID, itemSeasonOfSeries.getId());
                            mapFragments.put(itemSeasonOfSeries.getName(), SeasonFragment.newInstance(seriesAdapter));
                        }
                        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getContext(), getChildFragmentManager(),
                                mapFragments, false);
                        pager.setAdapter(tabFragmentAdapter);
                        viewPagerTab.setViewPager(pager);
                        marker_progress.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
//
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SeasonOfSeries> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

}
