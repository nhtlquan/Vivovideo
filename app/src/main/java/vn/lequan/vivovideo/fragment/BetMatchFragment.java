package vn.lequan.vivovideo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_BetMatch;
import com.smile.studio.network.ver2.model.BetMatch.BetItem;
import com.smile.studio.network.ver2.model.BetMatch.BetMatch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.BetMatchAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 16/08/2016.
 */
public class BetMatchFragment extends Fragment {

    private View view;
    private LinearLayout root;
    private int index = -1, numberColumns = 2;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BetMatchAdapter betMatchAdapter;

    public BetMatchFragment() {
    }

    public static BetMatchFragment newInstance() {
        Bundle args = new Bundle();
        BetMatchFragment fragment = new BetMatchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalApp.checktabfragment = true;
        getActivity().setTitle("");
    }

    @Override
    public void onPause() {
        super.onPause();
        GlobalApp.checktabfragment = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_betmatch, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            progressBar = (ProgressBar) view.findViewById(R.id.marker_progress);
            betMatchAdapter = new BetMatchAdapter(getFragmentManager(), getActivity(), new ArrayList<BetItem>(), numberColumns);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(betMatchAdapter);
            getListBet();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void getListBet() {
        Call<BetMatch> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BetMatch.class).getBetMatch(GlobalApp.getInstance().acessToken, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<BetMatch>() {
            @Override
            public void onResponse(Call<BetMatch> call, Response<BetMatch> response) {
                try {
                    BetMatch value = response.body();
                    betMatchAdapter.addAll(value.getData());
                    Debug.e(value.getData().get(0).getName());
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
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
