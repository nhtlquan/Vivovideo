package vn.lequan.vivovideo.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.smile.studio.libsmilestudio.fragment.TabFragmentAdapter;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.ver2.model.BetMatch.BetItem;
import com.smile.studio.network.ver2.model.BetMatch.Tournament;

import java.util.LinkedHashMap;
import java.util.List;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 17/08/2016.
 */
public class GameFragment extends Fragment {

    private View view;
    public static final int VIDEO_GAME = 3341;
    private static final String OBJECT = "object";

    public GameFragment() {
    }

    public static GameFragment newInstance(BetItem betItem) {
        Bundle args = new Bundle();
        args.putParcelable(OBJECT, betItem);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
//        GlobalApp.getInstance().fragment = GameFragment.newInstance(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_video_clips_hot, container, false);
            LinkedHashMap<Integer, Fragment> mapFragments = new LinkedHashMap<Integer, Fragment>();
            BetItem betItem =  getArguments().getParcelable(OBJECT);
            mapFragments.put(R.string.title_game, HomeGameFragment.newInstance(betItem));
//            mapFragments.put(R.string.about_game, BrowserFragment.newInstance());
            mapFragments.put(R.string.history_game, HistoryGameFragment.newInstance(betItem));
            mapFragments.put(R.string.top_game, TopGameFragment.newInstance(betItem.getId()));
//            mapFragments.put(R.string.video_game, VideoNoGroupsFragment.newInstance(VIDEO_GAME));
            TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getContext(), getChildFragmentManager(),
                    mapFragments);
            final ViewPager pager = (ViewPager) view.findViewById(R.id.viewpager1);
            ViewGroup tab = (ViewGroup) view.findViewById(R.id.tab);
            tab.addView(LayoutInflater.from(getContext()).inflate(R.layout.demo_always_in_center, tab, false));
            final SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
            pager.setAdapter(tabFragmentAdapter);
            pager.setOffscreenPageLimit(0);
            viewPagerTab.setViewPager(pager);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");
    }



}
