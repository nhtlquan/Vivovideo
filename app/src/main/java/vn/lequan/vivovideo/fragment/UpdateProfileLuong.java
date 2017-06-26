package vn.lequan.vivovideo.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Auth;
import com.smile.studio.network.ver2.model.Login.ResponeUpdatePhone;
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
public class UpdateProfileLuong extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private View view;
    private static ProgressDialog pDialog;
    private EditText edtLastName, edtName, edtReLastName;
    private Button btnUpdate;
    private String name;
    private String phoneNumber;

    public UpdateProfileLuong(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_account_update_phone, container, false);
            edtLastName = (EditText) view.findViewById(R.id.edtLastName);
            edtReLastName = (EditText) view.findViewById(R.id.edtReLastName);
            edtName = (EditText) view.findViewById(R.id.edtName);
            btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
            pDialog = new ProgressDialog(getActivity());
            btnUpdate.setOnClickListener(this);
        }
        return view;
    }

    private void vetifyAccount() {

        if (edtLastName.getText().toString().equals("")) {
            edtLastName.setError("Bạn vui lòng nhập số điện thoại");
            return;
        }
        if (edtReLastName.getText().toString().equals("")) {
            edtReLastName.setError("Bạn vui lòng nhập số điện thoại");
            return;
        }
        if (!edtReLastName.getText().toString().equals(edtLastName.getText().toString())) {
            Toast.makeText(getActivity(), "Mật khẩu không khớp", Toast.LENGTH_LONG).show();
            return;
        }
        if (edtName.getText().toString().equals("")) {
            edtName.setError("Bạn vui lòng nhập tên");
            return;
        }
        pDialog.setMessage(getString(R.string.text_dialog));
        pDialog.setCancelable(true);
        pDialog.show();
        final String header = "Bearer " + GlobalApp.getInstance().token;
        String password = edtLastName.getText().toString();
        name = edtName.getText().toString();
        Call<ResponeUpdatePhone> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).updatePhone(GlobalApp.getInstance().token, API.client_id, API.client_secret, password, name);
        call.enqueue(new Callback<ResponeUpdatePhone>() {
            @Override
            public void onResponse(Call<ResponeUpdatePhone> call, Response<ResponeUpdatePhone> response) {
                try {
                    if (response.body().getCode() == 0) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        GlobalApp.getInstance().acessToken = header;
                        SharedPreferencesUtils.putString(getContext(), API.Token, header);// Save user token
                        SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, true);// Save user token
                        SharedPreferencesUtils.putString(getContext(), API.Name, name);
                        SharedPreferencesUtils.putString(getContext(), API.Phone_User, phoneNumber);
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
                        Toast.makeText(getActivity(), response.body().getMessages(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponeUpdatePhone> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
        } else {
            v.getBackground().setAlpha(80);
        }
    }

    private void reQuestCode(final String phone) {
        String header = "Bearer " + GlobalApp.getInstance().token;
        Debug.e("header: " + header);
        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).reQuestCode(phone
                , GlobalApp.getInstance().token, API.client_id, API.client_secret, header);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    if (response.body().getCode() == 0) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.fragment, new VetifyAccount(phone, name))
                                .addToBackStack(null).commit();
                    } else {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Toast.makeText(getActivity(), "Bạn vui lòng sử dụng số điện thoại khác", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                vetifyAccount();
                break;
        }
    }


}
