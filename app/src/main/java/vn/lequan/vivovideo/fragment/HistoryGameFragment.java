package vn.lequan.vivovideo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_BetMatch;
import com.smile.studio.network.ver2.model.BetMatch.BetItem;
import com.smile.studio.network.ver2.model.BetMatch.BetMatch;
import com.smile.studio.network.ver2.model.BetMatch.Tournament;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.TournamentsAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

public class HistoryGameFragment extends Fragment {

    private View view;
    private static final String OBJECT = "object";
    private LinearLayout root;
    int dem;
    private RecyclerView recyclerView;
    private TextView txt_dudoan;
    private BetItem betItem;
    private TournamentsAdapter tournamentsAdapter;
    private ProgressBar marker_progress;

    public HistoryGameFragment() {
    }

    public static HistoryGameFragment newInstance(BetItem betItem) {
        Bundle args = new Bundle();
        args.putParcelable(OBJECT, betItem);
        HistoryGameFragment fragment = new HistoryGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_history_game, container, false);
            betItem = getArguments().getParcelable(OBJECT);
            txt_dudoan = (TextView) view.findViewById(R.id.txt_dudoan);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            tournamentsAdapter = new TournamentsAdapter(getActivity(), new ArrayList<Tournament>(), 1);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            loadMatchList();
        }
        return view;
    }

    private void loadMatchList() {
        Call<BetMatch> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BetMatch.class).getHistoryBetMatch(GlobalApp.getInstance().acessToken, betItem.getId(), GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<BetMatch>() {
            @Override
            public void onResponse(Call<BetMatch> call, Response<BetMatch> response) {
                try {
                    marker_progress.setVisibility(View.GONE);
                    BetMatch value = response.body();
                    txt_dudoan.setVisibility(View.GONE);
                    tournamentsAdapter.addAll(value.getData().get(0).getTournaments());
                    recyclerView.setAdapter(tournamentsAdapter);
                } catch (Exception e) {
                    txt_dudoan.setVisibility(View.VISIBLE);
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BetMatch> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }


}
