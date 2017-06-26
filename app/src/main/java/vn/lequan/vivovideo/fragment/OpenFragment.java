package vn.lequan.vivovideo.fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import vn.lequan.vivovideo.R;

@SuppressLint("UseSparseArrays")
public class OpenFragment extends Fragment implements
        SmartTabLayout.TabProvider {


    public OpenFragment() {
    }

    public static OpenFragment newInstance() {
        Bundle args = new Bundle();
        OpenFragment fragment = new OpenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open, container, false);
        final ViewPager pager = (ViewPager) view.findViewById(R.id.viewpager1);
        ViewGroup tab = (ViewGroup) view.findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(getContext()).inflate(R.layout.demo_custom_tab_icon_and_notification_mark, tab, false));
        final SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setCustomTabView(this);
        FragmentPagerItems pages = new FragmentPagerItems(getContext());
        pages.add(FragmentPagerItem.of("1", HomeFragment_Test.class));
        pages.add(FragmentPagerItem.of("2", HomeFragment.class));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), pages);
        pager.setAdapter(adapter);
        viewPagerTab.setViewPager(pager);
        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        Resources res = container.getContext().getResources();
        View tab = inflater.inflate(R.layout.custom_tab_icon_and_notification_mark, container, false);
        View mark = tab.findViewById(R.id.custom_tab_notification_mark);
        mark.setVisibility(View.GONE);
        ImageView icon = (ImageView) tab.findViewById(R.id.custom_tab_icon);
        ImageView icon1 = (ImageView) tab.findViewById(R.id.custom_tab_icon_sub);
        switch (position) {
            case 0:
                icon.setImageDrawable(res.getDrawable(R.drawable.ic_home));
                break;
            case 1:
                icon.setImageDrawable(res.getDrawable(R.drawable.ic_tivi));
                break;
            default:
                throw new IllegalStateException("Invalid position: " + position);
        }
        return tab;
    }
}
