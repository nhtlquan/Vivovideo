package vn.lequan.vivovideo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_BetMatch;
import com.smile.studio.network.ver2.model.QuestionByID.Answer;
import com.smile.studio.network.ver2.model.QuestionByID.Question;
import com.smile.studio.network.ver2.model.UserBet.UserBet;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.AnswserAdapter;
import vn.lequan.vivovideo.adapter.PointAdapter;
import vn.lequan.vivovideo.model.GlobalApp;


@SuppressLint("ValidFragment")
public class BettingDialogFragment extends DialogFragment implements View.OnClickListener {
    private View view;
    private DateFormat dateFormat2 = new SimpleDateFormat("HH'h'mm, 'ngày' dd/MM/yy");
    private ImageView btn_close;
    private TextView btn_signin, tv_question, tv_question2, tv_match, tv_time, btn_dung, btn_sai, btn_confirm,
            btn_value;
    private LinearLayout conent_dudoan1, conent_dudoan2, conent_dudoan3;
    private String match, time;
    private IAction action_button_click;
    Spinner spinner;
    Spinner spiner_answer;
    private int point = 0;
    private int match_ID;
    private AnswserAdapter answserAdapter;
    private PointAdapter pointAdapter;
    private int points[] = new int[]{1000, 2000, 3000, 4000, 5000, 10000};
    Question item;

    public BettingDialogFragment(int match_ID, Question item, String match, String time, IAction action) {
        super();
        this.item = item;
        this.match = match;
        this.time = time;
        this.match_ID = match_ID;
        this.action_button_click = action;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conent_dudoan1 = (LinearLayout) view.findViewById(R.id.conent_dudoan1);
        conent_dudoan1.setVisibility(View.VISIBLE);
        spiner_answer = (Spinner) view.findViewById(R.id.spiner_answer);

        if (item.getAnswers() != null) {
            answserAdapter = new AnswserAdapter(item.getAnswers());
            spiner_answer.setAdapter(answserAdapter);
        }
        spiner_answer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                answserAdapter.setPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner = (Spinner) view.findViewById(R.id.spinner);
        pointAdapter = new PointAdapter(points);
        spinner.setAdapter(pointAdapter);
        btn_close = (ImageView) view.findViewById(R.id.btn_close);
        btn_dung = (TextView) view.findViewById(R.id.btn_dung);
        btn_dung.setOnClickListener(this);
        btn_sai = (TextView) view.findViewById(R.id.btn_sai);
        btn_sai.setOnClickListener(this);
        btn_signin = (TextView) view.findViewById(R.id.btn_signin);
        btn_value = (TextView) view.findViewById(R.id.btn_value);
        btn_confirm = (TextView) view.findViewById(R.id.btn_confirm);
        btn_close.setOnClickListener(this);
        tv_question = (TextView) view.findViewById(R.id.tv_question);
        tv_question2 = (TextView) view.findViewById(R.id.tv_question2);
        tv_question.setText(item.getQuestion());
        tv_match = (TextView) view.findViewById(R.id.tv_match);
        tv_match.setText(match);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_time.setText(time);
    }


    @Override
    public void onClick(View view) {
        Answer answers = (Answer) spiner_answer.getItemAtPosition(spiner_answer.getSelectedItemPosition());
        int money = (int) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
        switch (view.getId()) {
            case R.id.btn_dung:
                if (answers != null)
                    postAnswer(item.getId(), answers.getId(), money);
                break;
            case R.id.btn_sai:
                dismiss();
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }

    private void postAnswer(int questionID, int answerID, final int money) {
        Call<UserBet> call = GlobalApp.getInstance().retrofit_ver2.create(Face_BetMatch.class).userBet(match_ID, questionID, answerID, money, GlobalApp.getInstance().acessToken, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<UserBet>() {

            @Override
            public void onResponse(Call<UserBet> call, Response<UserBet> response) {
                try {
                    UserBet value = response.body();
                    if (action_button_click != null) {
                        action_button_click.callback(money);
                    }
                    Debug.showAlert(getContext(), value.getMessage());
                    dismiss();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_dialog_dudoan, container, false);
        return view;
    }

    public interface IAction {
        void callback(int bet);
    }
}
