package vn.lequan.vivovideo.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Auth;
import com.smile.studio.network.ver2.model.Login.ResponeLogin;
import com.smile.studio.network.ver2.model.UserProfile.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecoverPassword extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private View view;
    private static ProgressDialog pDialog;
    private EditText edtLastName;
    private Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_account_update_phone, container, false);
            edtLastName = (EditText) view.findViewById(R.id.edtLastName);
            btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
            pDialog = new ProgressDialog(getActivity());
            btnUpdate.setOnClickListener(this);
        }
        return view;
    }

    private void vetifyAccount() {
        pDialog.setMessage(getString(R.string.text_dialog));
        pDialog.setCancelable(true);
        pDialog.show();
        if (edtLastName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Bạn vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            if (pDialog.isShowing())
                pDialog.dismiss();
            return;
        }
        final String phone = edtLastName.getText().toString();
        Call<ResponeLogin> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).recoverPassword(phone, API.client_id, API.client_secret);
        call.enqueue(new Callback<ResponeLogin>() {
            @Override
            public void onResponse(Call<ResponeLogin> call, Response<ResponeLogin> response) {
                try {
                    if (response.body().getCode() == 0) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Debug.e(response.body().getData());
                        Debug.e(response.body().getMessages());
                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.fragment, new VetifyAccountRecover(phone, response.body().getData()))
                                .addToBackStack(null).commit();
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
            public void onFailure(Call<ResponeLogin> call, Throwable t) {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                vetifyAccount();
                break;
        }
    }


}
