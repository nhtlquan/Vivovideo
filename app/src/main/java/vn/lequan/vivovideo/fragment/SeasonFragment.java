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

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.SeriesItemAdapter;

/**
 * Created by admin on 17/08/2016.
 */
public class SeasonFragment extends Fragment {

    private View view;
    private static final String OBJECT = "object";
    private RecyclerView recyclerView;
    private RelativeLayout layout_title;
    private ProgressBar marker_progress;

    public SeasonFragment() {
    }

    public static SeasonFragment newInstance(SeriesItemAdapter seriesAdapter) {
        Bundle args = new Bundle();
        SeasonFragment fragment = new SeasonFragment();
        args.putParcelable(OBJECT, seriesAdapter);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_item_home_test, container, false);
            layout_title = (RelativeLayout) view.findViewById(R.id.layout_title);
            layout_title.setVisibility(View.GONE);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            Object data = getArguments().getParcelable(OBJECT);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            recyclerView.setAdapter((SeriesItemAdapter) data);
        }
        return view;
    }

}
