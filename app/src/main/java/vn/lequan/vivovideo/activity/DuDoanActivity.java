package vn.lequan.vivovideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.recyclerviewer.OnItemTouchListener;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.model.DuDoan;
import com.smile.studio.network.ver2.face.Face_BetMatch;
import com.smile.studio.network.ver2.model.QuestionByID.Question;
import com.smile.studio.network.ver2.model.QuestionByID.QuestionById;
import com.smile.studio.network.ver2.model.UserBet.UserBet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.DuDoanAdapter;
import vn.lequan.vivovideo.google.analytics.AnalyticsApplication;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.view.BettingDialogFragment;

public class DuDoanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layout;
    private DuDoanAdapter adapter;
    private TextView tv_title;
    private int match_ID;
    private String match, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_du_doan);
        GlobalApp.getInstance().application = (AnalyticsApplication) getApplication();
        GlobalApp.getInstance().application.trackScreenView(DuDoanActivity.class.getSimpleName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        tv_title = (TextView) findViewById(R.id.title);
        match_ID = getIntent().getExtras().getInt(DuDoan.MATCHID);
        match = getIntent().getExtras().getString(DuDoan.MATCH);
        time = getIntent().getExtras().getString(DuDoan.TIME);
        tv_title.setText(match + " " + time);
        Debug.e(match + " " + time);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(true);
        adapter = new DuDoanAdapter(this, new ArrayList<Question>());
        recyclerView.setAdapter(adapter);
        loadData(getIntent().getExtras().getInt(DuDoan.MATCHID));
        recyclerView.addOnItemTouchListener(new OnItemTouchListener(this, recyclerView, new OnItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final Question item = adapter.getItemAtPosition(position);
                if (!SharedPreferencesUtils.getBoolean(getApplicationContext(), API.checkLogin)) {
                    Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(intent);
                    return;
                }
                if (item.getBet() == null) {
                    BettingDialogFragment bettingDialogFragment = null;
                    bettingDialogFragment = new BettingDialogFragment(match_ID, item, match, time, new BettingDialogFragment.IAction() {
                        @Override
                        public void callback(int bet) {
                            adapter.clear();
                            loadData(getIntent().getExtras().getInt(DuDoan.MATCHID));
                        }
                    });
                    bettingDialogFragment.show(getSupportFragmentManager(), BettingDialogFragment.class.getSimpleName());
                } else {
                    sendDeleteAnswer(item.getId());
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void sendDeleteAnswer(final int position) {
        Call<UserBet> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BetMatch.class).userCancelBet(getIntent().getExtras().getInt(DuDoan.MATCHID), position, GlobalApp.getInstance().acessToken, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<UserBet>() {
            @Override
            public void onResponse(Call<UserBet> call, Response<UserBet> response) {
                try {
                    UserBet value = response.body();
                    adapter.clear();
                    loadData(getIntent().getExtras().getInt(DuDoan.MATCHID));
                    Toast.makeText(getApplicationContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserBet> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    private void loadData(int matchID) {
        Debug.e(String.valueOf(matchID));
        Call<QuestionById> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BetMatch.class).getQuestionById(matchID, GlobalApp.getInstance().acessToken, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<QuestionById>() {
            @Override
            public void onResponse(Call<QuestionById> call, Response<QuestionById> response) {
                try {
                    QuestionById value = response.body();
                    adapter.addAll(value.getData().getQuestions());
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<QuestionById> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
