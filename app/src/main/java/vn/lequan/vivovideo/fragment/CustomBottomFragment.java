package vn.lequan.vivovideo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.smile.studio.libsmilestudio.fragment.TabFragmentAdapter;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.enums.TYPE;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 28/08/2016.
 */

@SuppressLint("ValidFragment")
public class CustomBottomFragment extends Fragment {

    private static final String OBJECT = "object";
    private View view;
    private int id = Integer.MIN_VALUE;
    private LinkedHashMap<Integer, Fragment> mapFragments;
    private SmartTabLayout viewPagerTab;
    private ViewGroup tab;
    private ViewPager pager;

    public CustomBottomFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_base_swipe_view_test, container, false);
            pager = (ViewPager) view.findViewById(R.id.viewpager1);
            tab = (ViewGroup) view.findViewById(R.id.tab);
            tab.addView(LayoutInflater.from(getContext()).inflate(R.layout.demo_custom_tab_icon_and_notification_mark_bottom, tab, false));
            viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        }
        return view;
    }




}


