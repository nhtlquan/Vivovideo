package vn.lequan.vivovideo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smile.studio.network.utils.Debug;
import com.smile.studio.network.ver2.face.Face_BetMatch;
import com.smile.studio.network.ver2.model.TopBet.TopBet;
import com.smile.studio.network.ver2.model.TopBet.TopBetUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.UserTopAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 19/08/2016.
 */
public class TopGameFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private UserTopAdapter adapter;
    private static final String OBJECT = "object";
    private int sizeTop = 100;
    private TextView title;

    public TopGameFragment() {
    }

    public static TopGameFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(OBJECT, id);
        TopGameFragment fragment = new TopGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_row_title_recyclerview, container, false);
            title = (TextView) view.findViewById(R.id.title);
            view.findViewById(R.id.icon_title).setVisibility(View.GONE);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new UserTopAdapter(getActivity(), new ArrayList<TopBetUser>());
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            getTopGame(getArguments().getInt(OBJECT));
        }
        return view;
    }


    public void getTopGame(int anInt) {
        Call<TopBet> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BetMatch.class).getTopBet(anInt, GlobalApp.getInstance().acessToken, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<TopBet>() {
            @Override
            public void onResponse(Call<TopBet> call, Response<TopBet> response) {
                try {
                    TopBet value = response.body();
                    title.setVisibility(View.GONE);
                    adapter.addAll(value.getData());
                } catch (Exception e) {
                    title.setVisibility(View.VISIBLE);
                    title.setText("Chưa có bảng xếp hạng người chơi!");
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<TopBet> call, Throwable t) {
                com.smile.studio.libsmilestudio.utils.Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }
}
