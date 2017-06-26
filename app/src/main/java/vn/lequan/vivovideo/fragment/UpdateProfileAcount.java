package vn.lequan.vivovideo.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Auth;
import com.smile.studio.network.ver2.model.UserProfile.UserProfile;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpdateProfileAcount extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private View view;
    private ImageView ivAvatar;
    private EditText  edtFirstName, edtEmail, edtMatKhau, edtDiaChi;
    private RecyclerView recyclerViewProductList;
    private RadioButton rd_nam, rd_nu;
    private UserProfile userProfile;
    private Button btnUpdate;
    private AlertDialog dialogExit;
    private RadioGroup rd_group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_account_update, container, false);
            ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
            rd_group = (RadioGroup) view.findViewById(R.id.rd_group);
            edtFirstName = (EditText) view.findViewById(R.id.edtFirstName);
            edtEmail = (EditText) view.findViewById(R.id.edtEmail);
            edtDiaChi = (EditText) view.findViewById(R.id.edtDiaChi);
            edtMatKhau = (EditText) view.findViewById(R.id.edtMatKhau);
            btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
            rd_nam = (RadioButton) view.findViewById(R.id.rd_nam);
            rd_nu = (RadioButton) view.findViewById(R.id.rd_nu);
            btnUpdate.setOnClickListener(this);
            rd_nam.setOnClickListener(this);
            rd_nu.setOnClickListener(this);
            getData();
        }
        return view;
    }

    private void getData() {
        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).getUserProfile(GlobalApp.getInstance().acessToken, "true");
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    userProfile = response.body();
                    initView();
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    private void initView() {
        if (userProfile != null) {
            String user_name = userProfile.getData().getProfile().getFirstName();
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound(String.valueOf(user_name.charAt(0)).toUpperCase(), GlobalApp.getColor(String.valueOf(user_name.charAt(0))));// set avatar with random color
            if (userProfile.getData().getProfile().getAvatarUrl() != null) {
                Glide.with(getActivity()).load(userProfile.getData().getProfile().getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivAvatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivAvatar.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
                ivAvatar.setImageDrawable(drawable1);
            }
            edtFirstName.setText(userProfile.getData().getProfile().getFirstName());
            if (userProfile.getData().getProfile().getEmail() != null)
                edtEmail.setText(userProfile.getData().getProfile().getEmail());
            if (userProfile.getData().getProfile().getAddress() != null)
                edtDiaChi.setText(userProfile.getData().getProfile().getAddress());
            if (userProfile.getData().getProfile().getGenderId().equals("1")) {
                rd_nam.setChecked(true);
                rd_nu.setChecked(false);
            } else {
                rd_nam.setChecked(false);
                rd_nu.setChecked(true);
            }
        }
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
                onActionUpdate();
                break;
        }
    }

    private void onActionUpdate() {
        int isCheck = rd_group.getCheckedRadioButtonId();
        String gender_id = "";
        if (isCheck == R.id.rd_nam) {
            gender_id = "1";
        } else {
            gender_id = "2";
        }
        String last_name = edtFirstName.getText().toString();
        final String name = last_name;
        final String email = edtEmail.getText().toString();
        String address = edtDiaChi.getText().toString();
//        if (first_name.equals("") || last_name.equals("") || email.equals("") || address.equals("")) {
//            Toast.makeText(getActivity(), "Bạn vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//        }
        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).setUserProfile(gender_id, last_name, email, address, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    userProfile = response.body();
                    if (userProfile.getCode() == -1) {
                        Toast.makeText(getActivity(), "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferencesUtils.putString(getContext(), API.Email, email);
                        SharedPreferencesUtils.putString(getContext(), API.Name, name);

                        Toast.makeText(getActivity(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
//                        reQuestCode();
                    }
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

//    private void reQuestCode() {
//        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).reQuestCode( GlobalApp.getInstance().token, API.client_id, API.client_secret);
//        call.enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                try {
//                    getFragmentManager()
//                            .beginTransaction()
//                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//                            .replace(R.id.fragment, new VetifyAccount())
//                            .addToBackStack(null).commit();
//                } catch (Exception e) {
//                    DebugPlayer.e("Lỗi: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserProfile> call, Throwable t) {
//                DebugPlayer.e("Lỗi: " + t.getMessage());
//            }
//        });
//    }

}
