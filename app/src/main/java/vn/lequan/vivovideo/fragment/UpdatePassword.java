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
import com.smile.studio.network.ver2.model.Login.ResponeUpdatePhone;
import com.smile.studio.network.ver2.model.UserProfile.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpdatePassword extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private View view;
    private static ProgressDialog pDialog;
    private EditText edtPassOld, edtPassNew,edtRePassNew;
    private Button btnUpdate;
    private String PassNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_account_update_pass, container, false);
            edtPassOld = (EditText) view.findViewById(R.id.edtPassOld);
            edtPassNew = (EditText) view.findViewById(R.id.edtPassNew);
            edtRePassNew = (EditText) view.findViewById(R.id.edtRePassNew);
            btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
            pDialog = new ProgressDialog(getActivity());
            btnUpdate.setOnClickListener(this);
        }
        return view;
    }

    private void vetifyAccount() {

        if (edtPassOld.getText().toString().equals("")) {
            edtPassOld.setError("Bạn vui lòng nhập mật khẩu cũ");
            return;
        }
        if (edtPassNew.getText().toString().equals("")) {
            edtPassNew.setError("Bạn vui lòng nhập mật khẩu mới");
            return;
        }
        if (edtRePassNew.getText().toString().equals("")) {
            edtRePassNew.setError("Bạn vui lòng nhập mật khẩu mới");
            return;
        }
        if (!edtRePassNew.getText().toString().equals(edtPassNew.getText().toString())){
            edtRePassNew.setError("Mật khẩu không khớp");
            return;
        }
        pDialog.setMessage(getString(R.string.text_dialog));
        pDialog.setCancelable(true);
        pDialog.show();
        String PassOld = edtPassOld.getText().toString();
        PassNew = edtPassNew.getText().toString();
        Call<ResponeUpdatePhone> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).updatePass(GlobalApp.getInstance().acessToken.replace("Bearer ",""), API.client_id, API.client_secret, PassOld, PassNew);
        call.enqueue(new Callback<ResponeUpdatePhone>() {
            @Override
            public void onResponse(Call<ResponeUpdatePhone> call, Response<ResponeUpdatePhone> response) {
                try {
                    if (response.body().getCode() == 0) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        getActivity().finish();
                        Toast.makeText(getActivity(),response.body().getMessages(),Toast.LENGTH_LONG).show();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                vetifyAccount();
                break;
        }
    }


}
