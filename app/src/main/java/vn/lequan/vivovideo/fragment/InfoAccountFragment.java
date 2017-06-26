package vn.lequan.vivovideo.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.PakageAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoAccountFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private View view;
    private ImageView ivAvatar,updatePass;
    private EditText edtEmail, edtSoDienThoai, edtMatKhau;
    private TextView tvUserName, tvSoDuChiTiet, tvLogOut;
    private RecyclerView recyclerViewProductList;
    private UserProfile userProfile;
    private AlertDialog dialogExit;
    private LinearLayout dangxuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_account_detail, container, false);
            ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
            updatePass = (ImageView) view.findViewById(R.id.updatePass);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvSoDuChiTiet = (TextView) view.findViewById(R.id.tvSoDuChiTiet);
            tvLogOut = (TextView) view.findViewById(R.id.tvLogOut);
            edtEmail = (EditText) view.findViewById(R.id.edtEmail);
            dangxuat = (LinearLayout) view.findViewById(R.id.dangxuat);
            edtSoDienThoai = (EditText) view.findViewById(R.id.edtSoDienThoai);
            edtMatKhau = (EditText) view.findViewById(R.id.edtMatKhau);
            recyclerViewProductList = (RecyclerView) view.findViewById(R.id.recyclerViewProductList);
            PakageAdapter adapter = new PakageAdapter(getActivity(), GlobalApp.getInstance().getListPakageService());
            recyclerViewProductList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerViewProductList.setHasFixedSize(true);
            recyclerViewProductList.setNestedScrollingEnabled(false);
            recyclerViewProductList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding2).color(Color.TRANSPARENT).build());
            recyclerViewProductList.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding5).color(Color.TRANSPARENT).build());
            recyclerViewProductList.setAdapter(adapter);
            edtEmail.setOnFocusChangeListener(this);
            edtEmail.getBackground().setAlpha(80);
            edtSoDienThoai.setOnFocusChangeListener(this);
            edtSoDienThoai.getBackground().setAlpha(80);
            edtMatKhau.setOnFocusChangeListener(this);
            edtMatKhau.getBackground().setAlpha(80);
            tvLogOut.setOnClickListener(this);
            tvUserName.setOnClickListener(this);
            updatePass.setOnClickListener(this);
            dangxuat.setOnClickListener(this);
            getData();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).getUserProfile(GlobalApp.getInstance().acessToken, "true");
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    userProfile = response.body();
                    initView(response.body().getData().getProfile().getPhone());
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

    private void initView(String phone) {
        if (userProfile != null) {
            String user_name = "";
            if (userProfile.getData().getProfile().getLastName() == null) {
                user_name = userProfile.getData().getProfile().getFirstName();
            } else {
                user_name = userProfile.getData().getProfile().getFirstName() + " " + userProfile.getData().getProfile().getLastName();
            }
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
            if (!SharedPreferencesUtils.getString(getActivity(), API.Name).equals("New User")) {
                tvUserName.setText(SharedPreferencesUtils.getString(getActivity(), API.Name));
            } else {
                tvUserName.setText(phone);
            }
            if (userProfile.getData().getProfile().getEmail() != null)
                edtEmail.setText(userProfile.getData().getProfile().getEmail());
            if (userProfile.getData().getProfile().getPhone() != null) {
                edtSoDienThoai.setText(userProfile.getData().getProfile().getPhone());
            }

            tvSoDuChiTiet.setText(String.valueOf(userProfile.getData().getProfile().getBalance()));
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
        ViewCompat.animate(v)
                .setDuration(200)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setInterpolator(new CycleInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(final View view) {

                    }

                    @Override
                    public void onAnimationEnd(final View view) {
                        switch (view.getId()) {
                            case R.id.tvLogOut:
                                onActionExit();
                                break;
                            case R.id.tvUserName:
                                getFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                        .replace(R.id.fragment, new UpdateProfileAcount())
                                        .addToBackStack(null).commit();
                                break;
                            case R.id.updatePass:
                                getFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                        .replace(R.id.fragment, new UpdatePassword())
                                        .addToBackStack(null).commit();
                                break;
                            case R.id.dangxuat:
                                onActionExit();
                                break;
                        }
                    }

                    @Override
                    public void onAnimationCancel(final View view) {

                    }
                })
                .withLayer()
                .start();
    }

    private class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }
    }

    private void onActionExit() {
        if (dialogExit == null)
            dialogExit = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar).setMessage(R.string.message_exit)
                    .setNegativeButton(R.string.exit_account_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setPositiveButton(R.string.exit_account_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            actionExit();
                        }
                    })
                    .create();
        if (!dialogExit.isShowing())
            dialogExit.show();
    }

    private void actionExit() {
        getActivity().finish();
        GlobalApp.getInstance().ListNavMenu.remove(2);
        Toast.makeText(getContext(), "Thoát thành công", Toast.LENGTH_SHORT).show();
        GlobalApp.getInstance().acessToken = "";
        GlobalApp.getInstance().token = "";
        SharedPreferencesUtils.putString(getContext(), API.Email, null);
        SharedPreferencesUtils.putString(getContext(), API.Token, "");
        SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, false);// Save user token
        SharedPreferencesUtils.putString(getContext(), API.Name, null);
        SharedPreferencesUtils.putString(getContext(), API.Avatar, null);
        SharedPreferencesUtils.putString(getContext(), API.Phone_User, null);
    }
}
