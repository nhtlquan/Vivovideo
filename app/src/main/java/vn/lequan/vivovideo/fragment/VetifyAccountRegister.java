package vn.lequan.vivovideo.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Auth;
import com.smile.studio.network.ver2.model.NavMenu.ActionClick;
import com.smile.studio.network.ver2.model.NavMenu.MenuItem;
import com.smile.studio.network.ver2.model.UserProfile.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * A placeholder fragment containing a simple view.
 */
@SuppressLint("ValidFragment")
public class VetifyAccountRegister extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private View view;
    private static ProgressDialog pDialog;
    private EditText edtLastName;
    private Button btnUpdate, btn_resend;
    private TextView txt_maxacthuc;
    private String code;
    private String phone = "", name = "";
    private boolean check_resendSMS = false;

    @SuppressLint("ValidFragment")
    public VetifyAccountRegister(String email, String name) {
        this.phone = email;
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_account_vetify, container, false);
            edtLastName = (EditText) view.findViewById(R.id.edtLastName);
            btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
            btn_resend = (Button) view.findViewById(R.id.btn_resend);
            txt_maxacthuc = (TextView) view.findViewById(R.id.txt_maxacthuc);
            if (phone != null) {
                String sourceString = "Mã xác thực đã được gửi tới " + "<b>" + phone + "</b> ";
                txt_maxacthuc.setText(Html.fromHtml(sourceString));
            }
            pDialog = new ProgressDialog(getActivity());
            btnUpdate.setOnClickListener(this);
            btn_resend.setOnClickListener(this);
            countTimer();
        }
        return view;
    }

    private void countTimer() {
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
                btn_resend.setText("Không nhận được mã? Gửi lại" + " (" + (int) millisUntilFinished / 1000 + ")");
            }

            public void onFinish() {
                btn_resend.setText("Không nhận được mã? Gửi lại");
                check_resendSMS = true;
            }

        }.start();
    }

    private void vetifyAccount() {
        final String header = "Bearer " + GlobalApp.getInstance().token;
        Debug.e("header: " + header);
        pDialog.setMessage(getString(R.string.text_dialog));
        pDialog.setCancelable(true);
        pDialog.show();
        if (edtLastName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Bạn vui lòng nhập mã", Toast.LENGTH_SHORT).show();
            if (pDialog.isShowing())
                pDialog.dismiss();
            return;
        }
        code = edtLastName.getText().toString();
        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).vetifyCode(code, GlobalApp.getInstance().token, API.client_id, API.client_secret, header);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    if (response.body().getCode() == 0) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        GlobalApp.getInstance().acessToken = header;
                        Debug.e(GlobalApp.getInstance().acessToken);
                        SharedPreferencesUtils.putString(getContext(), API.Email, phone);
                        SharedPreferencesUtils.putString(getContext(), API.Token, GlobalApp.getInstance().acessToken);
                        SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, true);
                        SharedPreferencesUtils.putString(getContext(), API.Name, name);
                        SharedPreferencesUtils.putString(getContext(), API.Avatar, response.body().getData().getProfile().getAvatarUrl());
                        SharedPreferencesUtils.putString(getContext(), API.Phone_User, phone);
                        ActionClick actionClick = new ActionClick(1, "bookmark", 0, 0);
                        MenuItem menuItem = new MenuItem(null, "Yêu thích", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", actionClick, null, null, null, null, null, null, null);
                        GlobalApp.getInstance().ListNavMenu.add(2, menuItem);
                        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = getActivity().getIntent();
                        Bundle bundle = intent.getBundleExtra(MainActivity.bundle);
                        if (bundle != null) {
                            intent.putExtra(MainActivity.bundle, bundle);
                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT, intent);
                        } else {
                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT);
                        }
                        getActivity().finish();
                    } else {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Toast.makeText(getActivity(), "Bạn nhập sai mã", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

//    private void getUserProfile(final String acessToken) {
//        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).getUserProfile(GlobalApp.getInstance().acessToken, "true");
//        call.enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                try {
//                    String user_name = "";
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    UserProfile userProfile = response.body();
//                    SharedPreferencesUtils.putString(getContext(), API.Token, acessToken);// Save user token
//                    SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, true);// Save user token
//                    if (userProfile.getData().getProfile().getLastName() == null) {
//                        user_name = userProfile.getData().getProfile().getFirstName();
//                    } else {
//                        user_name = userProfile.getData().getProfile().getFirstName() + " " + userProfile.getData().getProfile().getLastName();
//                    }
//                    SharedPreferencesUtils.putString(getContext(), API.Name, user_name);
//                    SharedPreferencesUtils.putString(getContext(), API.Phone_User, userProfile.getData().getProfile().getPhone());
//                    ActionClick actionClick = new ActionClick(1, "bookmark", 0, 0);
//                    MenuItem menuItem = new MenuItem(null, "Yêu thích", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", actionClick, null, null, null, null, null, null, null);
//                    GlobalApp.getInstance().ListNavMenu.add(2, menuItem);
//                    Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = getActivity().getIntent();
//                    Bundle bundle = intent.getBundleExtra(MainActivity.bundle);
//                    if (bundle != null) {
//                        intent.putExtra(MainActivity.bundle, bundle);
//                        getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT, intent);
//                    } else {
//                        getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT);
//                    }
//                    getActivity().finish();
//                } catch (Exception e) {
//                    Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    Debug.e("Lỗi: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserProfile> call, Throwable t) {
//                if (pDialog.isShowing())
//                    pDialog.dismiss();
//                Debug.e("Lỗi: " + t.getMessage());
//            }
//        });
//    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
        } else {
            v.getBackground().setAlpha(80);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                vetifyAccount();
                break;
            case R.id.btn_resend:
                if (check_resendSMS) {
                    resentSMS();
                    countTimer();
                    check_resendSMS = false;
                }
        }
    }

    private class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }
    }

    private void resentSMS() {
        String header = "Bearer " + GlobalApp.getInstance().token;
        Debug.e("header: " + header);
        pDialog.setMessage(getString(R.string.text_dialog));
        pDialog.setCancelable(true);
        pDialog.show();
        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).reQuestCode(phone, GlobalApp.getInstance().token, API.client_id, API.client_secret, header);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(getActivity(), "Đã gửi mã xác nhận", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }


}
