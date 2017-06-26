package vn.lequan.vivovideo.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smile.studio.network.ver2.model.BetMatch.BetItem;
import com.smile.studio.network.ver2.model.BetMatch.Tournament;

import java.util.ArrayList;
import java.util.Timer;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.TournamentsAdapter;

public class HomeGameFragment extends Fragment {

    private View view;
    private ViewPager viewPager = null;
    private Point point = null;
    private LinearLayout root = null;
    private Timer timer = null;
    private int dem;
    private static final String OBJECT = "object";
    private RecyclerView recyclerView;
    private BetItem betItem;
    private TextView txt_dudoan;
    private TournamentsAdapter tournamentsAdapter;
    private boolean check = false;

    public HomeGameFragment() {
    }

    public static HomeGameFragment newInstance(BetItem betItem) {
        Bundle args = new Bundle();
        args.putParcelable(OBJECT, betItem);
        HomeGameFragment fragment = new HomeGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_game, container, false);
            txt_dudoan = (TextView) view.findViewById(R.id.txt_dudoan);
            betItem = getArguments().getParcelable(OBJECT);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            tournamentsAdapter = new TournamentsAdapter(getActivity(), new ArrayList<Tournament>(), 1);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(tournamentsAdapter);

            if (betItem.getTournaments() != null) {
                tournamentsAdapter.addAll(betItem.getTournaments());
                for (Tournament tournament : betItem.getTournaments()) {
                    if (tournament.getMatches() != null) {
                        check = true;
                    }
                }
                if (!check){
                    txt_dudoan.setVisibility(View.VISIBLE);
                }
            } else {
                txt_dudoan.setVisibility(View.VISIBLE);
            }

        }
        return view;
    }

}
