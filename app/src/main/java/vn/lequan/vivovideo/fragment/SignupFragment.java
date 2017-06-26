package vn.lequan.vivovideo.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Auth;
import com.smile.studio.network.ver2.model.Login.Login;
import com.smile.studio.network.ver2.model.Login.LoginRequest;
import com.smile.studio.network.ver2.model.LoginFacebook.LoginFacebookToken;
import com.smile.studio.network.ver2.model.NavMenu.ActionClick;
import com.smile.studio.network.ver2.model.NavMenu.MenuItem;
import com.smile.studio.network.ver2.model.Register.Register;
import com.smile.studio.network.ver2.model.UserProfile.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignupFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private View view;
    private String phone, passwd, repasswd, name;
    private static ProgressDialog pDialog;
    private EditText edtEmail, edtPassword, edtPasswordReType, edtName;
    private Button btnRegister;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_signup, container, false);
            pDialog = new ProgressDialog(getActivity());
            edtEmail = (EditText) view.findViewById(R.id.edtEmail);
            btnRegister = (Button) view.findViewById(R.id.btnRegister);
            EditText edtHoVaTen = (EditText) view.findViewById(R.id.edtHoVaTen);
            edtName = (EditText) view.findViewById(R.id.edtName);
            edtPassword = (EditText) view.findViewById(R.id.edtPassword);
            edtPasswordReType = (EditText) view.findViewById(R.id.edtPasswordReType);
            edtEmail.setOnFocusChangeListener(this);
            edtHoVaTen.setOnFocusChangeListener(this);
            edtPassword.setOnFocusChangeListener(this);
            edtPasswordReType.setOnFocusChangeListener(this);
            edtEmail.getBackground().setAlpha(80);
            edtName.setOnFocusChangeListener(this);
            edtName.getBackground().setAlpha(80);
            edtHoVaTen.getBackground().setAlpha(80);
            edtPassword.getBackground().setAlpha(80);
            edtPasswordReType.getBackground().setAlpha(80);
            btnRegister.setOnClickListener(this);
        }
        return view;
    }


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
            case R.id.btnRegister:
                phone = edtEmail.getText().toString();
                passwd = edtPassword.getText().toString();
                repasswd = edtPasswordReType.getText().toString();
                name = edtName.getText().toString();
                if (checkInput(phone, passwd, repasswd)) {
                    pDialog.setMessage(getString(R.string.text_dialog));
                    pDialog.setCancelable(true);
                    pDialog.show();
                    Call<Register> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).userRegister(phone, passwd, name, API.client_id,
                            API.client_secret);
                    call.enqueue(new Callback<Register>() {

                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            try {
                                if (response.body().getCode() == 0) {
                                    if (pDialog.isShowing())
                                        pDialog.dismiss();
                                    GlobalApp.getInstance().token = response.body().getData().getToken();
                                } else {
                                    if (pDialog.isShowing())
                                        pDialog.dismiss();
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
//                                    acticonLoginAccount(phone, passwd);
                            } catch (Exception e) {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Debug.e("Lỗi: " + e.getMessage());
                            }

                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            Debug.e("Lỗi: " + t.getMessage());
                        }
                    });
                }
                break;
        }
    }




    private void actionLogintokenFacebook(String accessToken, String provider) {
        pDialog.setMessage(getString(R.string.text_dialog));
        pDialog.setCancelable(true);
        pDialog.show();
        Call<LoginFacebookToken> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).loginFacebook(provider, accessToken, API.client_id,
                API.client_secret);
        call.enqueue(new Callback<LoginFacebookToken>() {

            @Override
            public void onResponse(Call<LoginFacebookToken> call, Response<LoginFacebookToken> response) {
                try {
                    LoginFacebookToken value = response.body();
                    Debug.e(value.getData().getAccessToken());
                    String access_token = "Bearer " + value.getData().getAccessToken();
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                } catch (Exception e) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginFacebookToken> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }
    private class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }
    }


    private boolean checkInput(String phone, String passwd, String repasswd) {
        boolean flag = true;
        if (phone.equals("")) {
            edtEmail.setError(getString(R.string.message_error_input_signup_email));
            flag = false;
        }
        if (name.equals("")) {
            edtEmail.setError("Hãy nhập họ tên");
            flag = false;
        }
        if (passwd.equals("")) {
            edtPassword.setError(getString(R.string.message_error_input_signup_passwd));
            flag = false;
        }
        if (!passwd.equals(repasswd) || repasswd.isEmpty()) {
            edtPasswordReType.setError(getString(R.string.message_error_input_signup_repasswd));
            flag = false;
        }
        return flag;
    }


}
