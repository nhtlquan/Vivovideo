//package vn.lequan.vivovideo.fragment;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.MotionEventCompat;
//import android.util.Log;
//import android.util.SparseArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.accountkit.Account;
//import com.facebook.accountkit.AccountKit;
//import com.facebook.accountkit.AccountKitCallback;
//import com.facebook.accountkit.AccountKitError;
//import com.facebook.accountkit.AccountKitLoginResult;
//import com.facebook.accountkit.PhoneNumber;
//import com.facebook.accountkit.ui.AccountKitActivity;
//import com.facebook.accountkit.ui.AccountKitConfiguration;
//import com.facebook.accountkit.ui.LoginType;
//import com.facebook.login.LoginResult;
//import com.google.android.gms.auth.GoogleAuthException;
//import com.google.android.gms.auth.GoogleAuthUtil;
//import com.google.android.gms.auth.UserRecoverableAuthException;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.Scopes;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.smile.studio.libsmilestudio.facebook.LoginFacebook;
//import com.smile.studio.libsmilestudio.utils.Debug;
//import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
//import com.smile.studio.network.model.API;
//import com.smile.studio.network.ver2.face.Face_Auth;
//import com.smile.studio.network.ver2.model.Login.Login;
//import com.smile.studio.network.ver2.model.Login.LoginRequest;
//import com.smile.studio.network.ver2.model.LoginFacebook.LoginFacebookToken;
//import com.smile.studio.network.ver2.model.NavMenu.ActionClick;
//import com.smile.studio.network.ver2.model.NavMenu.MenuItem;
//import com.smile.studio.network.ver2.model.UserProfile.UserProfile;
//
//import java.io.IOException;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import vn.lequan.vivovideo.R;
//import vn.lequan.vivovideo.activity.AccountActivity;
//import vn.lequan.vivovideo.activity.MainActivity;
//import vn.lequan.vivovideo.model.GlobalApp;
//
///**
// * A placeholder fragment containing a simple view.
// */
//public class LoginFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, GoogleApiClient.OnConnectionFailedListener {
//
//    private View view;
//    private static ProgressDialog pDialog;
//    private String email = null;
//    private String passwd = null;
//    private final SparseArray<OnCompleteListener> permissionsListeners = new SparseArray<>();
//    private TextView tvRegisterAccount = null, edtUserName = null, edtPassword = null, btn_recovery;
//    private Button btnSignIn, btnFacebook, btnGoogle;
//    private GoogleApiClient mGoogleApiClient;
//    private static final int RC_SIGN_IN = 007;
//    private String token_google;
//    private int nextPermissionsRequestCode = 4000;
//    public static int APP_REQUEST_CODE = 99;
//    public static int APP_REQUEST_CODE_TOKEN = 98;
//    private String token_phet;//do thằng phệt yêu cầu
//
//    private interface OnCompleteListener {
//        void onComplete();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        GlobalApp.fragmentaccount = false;
//        if (view == null) {
//            view = inflater.inflate(R.layout.fragment_signin, container, false);
//            btnFacebook = (Button) view.findViewById(R.id.btnFacebook);
//            btnGoogle = (Button) view.findViewById(R.id.btnGoogle);
//            tvRegisterAccount = (TextView) view.findViewById(R.id.tvRegisterAccount);
//            pDialog = new ProgressDialog(getActivity());
//            btnSignIn = (Button) view.findViewById(R.id.btnSignIn);
//            btn_recovery = (TextView) view.findViewById(R.id.btn_recovery);
//            edtUserName = (TextView) view.findViewById(R.id.edtUserName);
//            edtPassword = (TextView) view.findViewById(R.id.edtPassword);
//            tvRegisterAccount.setOnClickListener(this);
//            edtUserName.setOnFocusChangeListener(this);
//            edtUserName.getBackground().setAlpha(80);
//            edtPassword.setOnFocusChangeListener(this);
//            edtPassword.getBackground().setAlpha(80);
//            btnSignIn.setOnClickListener(this);
//            btnGoogle.setOnClickListener(this);
//            btnFacebook.setOnClickListener(this);
//            btn_recovery.setOnClickListener(this);
//            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestEmail()
//                    .build();
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .enableAutoManage(getActivity(), this)
//                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                    .build();
//        }
//        return view;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mGoogleApiClient.stopAutoManage(getActivity());
//        mGoogleApiClient.disconnect();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mGoogleApiClient.stopAutoManage(getActivity());
//        mGoogleApiClient.disconnect();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tvRegisterAccount:
//                phoneLogin();
//                break;
//            case R.id.btnSignIn:
//                email = edtUserName.getText().toString();
//                passwd = edtPassword.getText().toString();
//                if (checkInput(email, passwd)) {
//                    acticonLoginAccount(email, passwd);
//                }
//                break;
//            case R.id.btnFacebook:
//                onActionLoginFacebook();
//                break;
//            case R.id.btnGoogle:
//                loginGoogle();
//                break;
//            case R.id.btnVTVCab:
//                Toast.makeText(getActivity(), "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_recovery:
//                Toast.makeText(getActivity(), "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
////                getFragmentManager()
////                        .beginTransaction()
////                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
////                        .replace(R.id.fragment, new RecoverPassword())
////                        .addToBackStack(null).commit();
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void phoneLogin() {
//        final Intent intent = new Intent(getActivity(), AccountKitActivity.class);
//        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
//                new AccountKitConfiguration.AccountKitConfigurationBuilder(
//                        LoginType.PHONE,
//                        AccountKitActivity.ResponseType.TOKEN).setReadPhoneStateEnabled(true).setReceiveSMS(true);
//        configurationBuilder.setReadPhoneStateEnabled(true);
//        configurationBuilder.setReceiveSMS(true);
//        intent.putExtra(
//                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
//                configurationBuilder.build());
//        OnCompleteListener completeListener = new OnCompleteListener() {
//            @Override
//            public void onComplete() {
//                startActivityForResult(intent, APP_REQUEST_CODE);
//            }
//        };
//        final OnCompleteListener receiveSMSCompleteListener = completeListener;
//        completeListener = new OnCompleteListener() {
//            @Override
//            public void onComplete() {
//                requestPermissions(
//                        Manifest.permission.RECEIVE_SMS,
//                        R.string.permissions_receive_sms_title,
//                        R.string.permissions_receive_sms_message,
//                        receiveSMSCompleteListener);
//            }
//        };
//        final OnCompleteListener readPhoneStateCompleteListener = completeListener;
//        completeListener = new OnCompleteListener() {
//            @Override
//            public void onComplete() {
//                requestPermissions(
//                        Manifest.permission.READ_PHONE_STATE,
//                        R.string.permissions_read_phone_state_title,
//                        R.string.permissions_read_phone_state_message,
//                        readPhoneStateCompleteListener);
//            }
//        };
//
//        completeListener.onComplete();
//    }
//
//    public void phoneLoginToken() {
//        final Intent intent = new Intent(getActivity(), AccountKitActivity.class);
//        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
//                new AccountKitConfiguration.AccountKitConfigurationBuilder(
//                        LoginType.PHONE,
//                        AccountKitActivity.ResponseType.TOKEN).setReadPhoneStateEnabled(true).setReceiveSMS(true);
//        configurationBuilder.setReadPhoneStateEnabled(true);
//        configurationBuilder.setReceiveSMS(true);
//        intent.putExtra(
//                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
//                configurationBuilder.build());
//        OnCompleteListener completeListener = new OnCompleteListener() {
//            @Override
//            public void onComplete() {
//                startActivityForResult(intent, APP_REQUEST_CODE_TOKEN);
//            }
//        };
//        final OnCompleteListener receiveSMSCompleteListener = completeListener;
//        completeListener = new OnCompleteListener() {
//            @Override
//            public void onComplete() {
//                requestPermissions(
//                        Manifest.permission.RECEIVE_SMS,
//                        R.string.permissions_receive_sms_title,
//                        R.string.permissions_receive_sms_message,
//                        receiveSMSCompleteListener);
//            }
//        };
//        final OnCompleteListener readPhoneStateCompleteListener = completeListener;
//        completeListener = new OnCompleteListener() {
//            @Override
//            public void onComplete() {
//                requestPermissions(
//                        Manifest.permission.READ_PHONE_STATE,
//                        R.string.permissions_read_phone_state_title,
//                        R.string.permissions_read_phone_state_message,
//                        readPhoneStateCompleteListener);
//            }
//        };
//
//        completeListener.onComplete();
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//        if (requestCode == 1001) {
//            if (token_google != null)
//                actionLogintokenFacebook(token_google, "google");
//        }
//        if (requestCode == APP_REQUEST_CODE) {
//            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
//            String toastMessage;
//            if (loginResult.getError() != null) {
//                toastMessage = loginResult.getError().getErrorType().getMessage();
//                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
//            } else if (loginResult.wasCancelled()) {
//                toastMessage = "Login Cancelled";
//                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
//            } else {
//                if (loginResult.getAccessToken() != null) {
//                    actionLogintokenFacebookCode(loginResult.getAccessToken().getToken(), "accountkit");
//                }
//
//            }
//        }
//        if (requestCode == APP_REQUEST_CODE_TOKEN) {
//            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
//            String toastMessage;
//            if (loginResult.getError() != null) {
//                toastMessage = loginResult.getError().getErrorType().getMessage();
//                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
//            } else if (loginResult.wasCancelled()) {
//                toastMessage = "Login Cancelled";
//                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
//            } else {
//                if (loginResult.getAccessToken() != null) {
//                    actionLogintokenFacebookCodeToken(loginResult.getAccessToken().getToken(), "accountkit");
//                }
//
//            }
//        }
//    }
//
//
//    private void requestPermissions(
//            final String permission,
//            final int rationaleTitleResourceId,
//            final int rationaleMessageResourceId,
//            final OnCompleteListener listener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            if (listener != null) {
//                listener.onComplete();
//            }
//            return;
//        }
//
//        checkRequestPermissions(
//                permission,
//                rationaleTitleResourceId,
//                rationaleMessageResourceId,
//                listener);
//    }
//
//    @TargetApi(23)
//    private void checkRequestPermissions(
//            final String permission,
//            final int rationaleTitleResourceId,
//            final int rationaleMessageResourceId,
//            final OnCompleteListener listener) {
//        if (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
//            if (listener != null) {
//                listener.onComplete();
//            }
//            return;
//        }
//
//        final int requestCode = nextPermissionsRequestCode++;
//        permissionsListeners.put(requestCode, listener);
//
//        if (shouldShowRequestPermissionRationale(permission)) {
//            new AlertDialog.Builder(getActivity())
//                    .setTitle(rationaleTitleResourceId)
//                    .setMessage(rationaleMessageResourceId)
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(final DialogInterface dialog, final int which) {
//                            requestPermissions(new String[]{permission}, requestCode);
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(final DialogInterface dialog, final int which) {
//                            // ignore and clean up the listener
//                            permissionsListeners.remove(requestCode);
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        } else {
//            requestPermissions(new String[]{permission}, requestCode);
//        }
//    }
//
//    @TargetApi(23)
//    @Override
//    public void onRequestPermissionsResult(
//            final int requestCode,
//            final @NonNull String[] permissions,
//            final @NonNull int[] grantResults) {
//        final OnCompleteListener permissionsListener = permissionsListeners.get(requestCode);
//        permissionsListeners.remove(requestCode);
//        if (permissionsListener != null
//                && grantResults.length > 0
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            permissionsListener.onComplete();
//        }
//    }
//
//    private void loginGoogle() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        Debug.e("handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            Debug.e(result.getSignInAccount().getEmail());
//            getAccesstoken(result.getSignInAccount().getEmail());
//        }
//    }
//
//    private void getAccesstoken(final String email) {
//        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String token = null;
//                try {
//                    token = GoogleAuthUtil.getToken(
//                            getActivity(),
//                            email,
//                            "oauth2:"
//                                    + Scopes.PLUS_LOGIN);
//                } catch (IOException transientEx) {
//                    Log.e("IOException", transientEx.toString());
//                } catch (UserRecoverableAuthException e) {
//                    startActivityForResult(e.getIntent(), 1001);
//                    Log.e("AuthException", e.toString());
//                } catch (GoogleAuthException authEx) {
//                    Log.e("GoogleAuthException", authEx.toString());
//                }
//                return token;
//            }
//
//            @Override
//            protected void onPostExecute(String token) {
//                token_google = token;
//                if (token_google != null) {
//                    actionLogintokenFacebook(token_google, "google");
//                }
//            }
//
//        };
//        task.execute();
//    }
//
//
//    private void acticonLoginAccount(final String email, String passwd) {
//        pDialog.setMessage(getString(R.string.text_dialog));
//        pDialog.setCancelable(true);
//        pDialog.show();
//        LoginRequest loginRequest = new LoginRequest(
//                email,
//                passwd,
//                API.client_id,
//                API.client_secret
//        );
//        Call<Login> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).actionLogin(loginRequest);
//        call.enqueue(new Callback<Login>() {
//
//            @Override
//            public void onResponse(Call<Login> call, Response<Login> response) {
//                try {
//                    Login value = response.body();
//                    String access_token = value.getData().getTokenType() + " " + value.getData().getAccessToken();
//                    GlobalApp.getInstance().token = value.getData().getAccessToken();
//                    getUserProfile(email, access_token);
//                } catch (Exception e) {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
//                    Debug.e("Lỗi: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Login> call, Throwable t) {
//                Debug.e("Lỗi: " + t.getMessage());
//            }
//        });
//    }
//
//    private void getUserProfile(final String email, final String access_token) {
//        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).getUserProfile(access_token, "true");
//        call.enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                try {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    if (response.body().getData().getHas_info() == 1) {
//                        GlobalApp.getInstance().acessToken = access_token;
////                        SharedPreferencesUtils.putString(getContext(), API.Email, email);
//                        SharedPreferencesUtils.putString(getActivity(), API.Token, access_token);
//                        String user_name = "";
//                        UserProfile userProfile = response.body();
//                        SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, true);// Save user token
//                        user_name = userProfile.getData().getProfile().getFirstName();
//                        SharedPreferencesUtils.putString(getContext(), API.Avatar, response.body().getData().getProfile().getAvatarUrl());
//                        SharedPreferencesUtils.putString(getContext(), API.Name, user_name);
//                        SharedPreferencesUtils.putString(getContext(), API.Phone_User, userProfile.getData().getProfile().getPhone());
//                        ActionClick actionClick = new ActionClick(1, "bookmark", 0, 0);
//                        MenuItem menuItem = new MenuItem(null, "Yêu thích", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", actionClick, null, null, null, null, null, null, null);
//                        GlobalApp.getInstance().ListNavMenu.add(2, menuItem);
//                        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = getActivity().getIntent();
//                        Bundle bundle = intent.getBundleExtra(MainActivity.bundle);
//                        if (bundle != null) {
//                            intent.putExtra(MainActivity.bundle, bundle);
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT, intent);
//                        } else {
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT);
//                        }
//                        getActivity().finish();
//                    } else {
//                        getFragmentManager()
//                                .beginTransaction()
//                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//                                .replace(R.id.fragment, new UpdateProfileLuong(email))
//                                .addToBackStack(null).commit();
//                    }
//                } catch (Exception e) {
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
//
//    private void getUserProfileFB(final String accessToken, final String token, final PhoneNumber phoneNumber) {
//        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).getUserProfile(accessToken, "true");
//        call.enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                try {
//                    if (response.body().getData().getHas_info() == 1) {
//                        GlobalApp.getInstance().acessToken = accessToken;
//                        Debug.e(GlobalApp.getInstance().acessToken);
//                        SharedPreferencesUtils.putString(getActivity(), API.Token, accessToken);
//                        String user_name = "";
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        UserProfile userProfile = response.body();
//                        SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, true);// Save user token
//                        user_name = userProfile.getData().getProfile().getFirstName();
//                        SharedPreferencesUtils.putString(getContext(), API.Avatar, response.body().getData().getProfile().getAvatarUrl());
//                        SharedPreferencesUtils.putString(getContext(), API.Name, user_name);
//                        SharedPreferencesUtils.putString(getContext(), API.Phone_User, userProfile.getData().getProfile().getPhone());
//                        ActionClick actionClick = new ActionClick(1, "bookmark", 0, 0);
//                        MenuItem menuItem = new MenuItem(null, "Yêu thích", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", actionClick, null, null, null, null, null, null, null);
//                        GlobalApp.getInstance().ListNavMenu.add(2, menuItem);
//                        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//
//
//                        Intent intent = getActivity().getIntent();
//                        Bundle bundle = intent.getBundleExtra(MainActivity.bundle);
//                        if (bundle != null) {
//                            intent.putExtra(MainActivity.bundle, bundle);
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT, intent);
//                        } else {
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT);
//                        }
//                        getActivity().finish();
//                    } else {
//                        GlobalApp.getInstance().token = token;
//                        getFragmentManager()
//                                .beginTransaction()
//                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//                                .replace(R.id.fragment, new UpdateProfileLuong(phoneNumber.getPhoneNumber()))
//                                .addToBackStack(null).commit();
//                    }
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
//
//    private void getUserProfileFB(final String accessToken) {
//        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).getUserProfile(accessToken, "true");
//        call.enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                try {
//                    if (response.body().getData().getProfile().getPhone() == null || response.body().getData().getProfile().getPhone().equals("")) {
//                        phoneLogin();
//                    } else {
//                        GlobalApp.getInstance().acessToken = accessToken;
//                        Debug.e(GlobalApp.getInstance().acessToken);
//                        SharedPreferencesUtils.putString(getActivity(), API.Token, accessToken);
//                        String user_name;
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        UserProfile userProfile = response.body();
//                        SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, true);// Save user token
//                        user_name = userProfile.getData().getProfile().getFirstName();
//                        SharedPreferencesUtils.putString(getContext(), API.Avatar, response.body().getData().getProfile().getAvatarUrl());
//                        SharedPreferencesUtils.putString(getContext(), API.Name, user_name);
//                        SharedPreferencesUtils.putString(getContext(), API.Phone_User, userProfile.getData().getProfile().getPhone());
//                        ActionClick actionClick = new ActionClick(1, "bookmark", 0, 0);
//                        MenuItem menuItem = new MenuItem(null, "Yêu thích", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", actionClick, null, null, null, null, null, null, null);
//                        GlobalApp.getInstance().ListNavMenu.add(2, menuItem);
//                        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = getActivity().getIntent();
//                        Bundle bundle = intent.getBundleExtra(MainActivity.bundle);
//                        if (bundle != null) {
//                            intent.putExtra(MainActivity.bundle, bundle);
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT, intent);
//                        } else {
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT);
//                        }
//                        getActivity().finish();
//                    }
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
//    private void getUserProfileFBToken( final String accessToken) {
//        Call<UserProfile> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).getUserProfile(accessToken, "true");
//        call.enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                try {
//                    if (response.body().getData().getProfile().getPhone() == null || response.body().getData().getProfile().getPhone().equals("")) {
//                        phoneLoginToken();
//                    } else {
//                        GlobalApp.getInstance().acessToken = accessToken;
//                        Debug.e(GlobalApp.getInstance().acessToken);
//                        SharedPreferencesUtils.putString(getActivity(), API.Token, accessToken);
//                        String user_name;
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        UserProfile userProfile = response.body();
//                        SharedPreferencesUtils.putBoolean(getContext(), API.checkLogin, true);// Save user token
//                        user_name = userProfile.getData().getProfile().getFirstName();
//                        SharedPreferencesUtils.putString(getContext(), API.Avatar, response.body().getData().getProfile().getAvatarUrl());
//                        SharedPreferencesUtils.putString(getContext(), API.Name, user_name);
//                        SharedPreferencesUtils.putString(getContext(), API.Phone_User, userProfile.getData().getProfile().getPhone());
//                        ActionClick actionClick = new ActionClick(1, "bookmark", 0, 0);
//                        MenuItem menuItem = new MenuItem(null, "Yêu thích", "http://www.freeiconspng.com/uploads/favorites-star-icon-png-0.png", actionClick, null, null, null, null, null, null, null);
//                        GlobalApp.getInstance().ListNavMenu.add(2, menuItem);
//                        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = getActivity().getIntent();
//                        Bundle bundle = intent.getBundleExtra(MainActivity.bundle);
//                        if (bundle != null) {
//                            intent.putExtra(MainActivity.bundle, bundle);
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT, intent);
//                        } else {
//                            getActivity().setResult(MainActivity.RESULTCODE_LOGIN_ACCOUNT);
//                        }
//                        getActivity().finish();
//                    }
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
//
//
//
//    private void onActionLoginFacebook() {
//        LoginFacebook.onActionLoginFacebook(getActivity(), ((AccountActivity) getActivity()).callbackManager,
//                new FacebookCallback<LoginResult>() {
//
//                    @Override
//                    public void onSuccess(LoginResult result) {
//                        String AccessToken = result.getAccessToken().getToken();
//                        Debug.e(AccessToken);
//                        if (AccessToken != null) {
//                            Debug.e(AccessToken);
//                            actionLogintokenFacebook(AccessToken, "facebook");
//                        }
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        Debug.showAlert(getActivity(), error.getMessage());
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//    }
//
//    private void actionLogintokenFacebook(String accessToken, String provider) {
//        pDialog.setMessage(getString(R.string.text_dialog));
//        pDialog.setCancelable(true);
//        pDialog.show();
//        Call<LoginFacebookToken> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).loginFacebook(provider, accessToken, API.client_id,
//                API.client_secret);
//        call.enqueue(new Callback<LoginFacebookToken>() {
//
//            @Override
//            public void onResponse(Call<LoginFacebookToken> call, Response<LoginFacebookToken> response) {
//                try {
//                    if (response.body().getCode() == 0) {
//                        final LoginFacebookToken value = response.body();
//                        token_phet = value.getData().getAccessToken();
//                        getUserProfileFBToken("Bearer " + token_phet);
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                    } else {
//                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
//                    Debug.e("Lỗi: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginFacebookToken> call, Throwable t) {
//                Debug.e("Lỗi: " + t.getMessage());
//            }
//        });
//    }
//
//    private void actionLogintokenFacebookCode(String accessToken, String provider) {
//        pDialog.setMessage(getString(R.string.text_dialog));
//        pDialog.setCancelable(true);
//        pDialog.show();
//        Call<LoginFacebookToken> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).loginFacebook(provider, accessToken, API.client_id,
//                API.client_secret);
//        call.enqueue(new Callback<LoginFacebookToken>() {
//
//            @Override
//            public void onResponse(Call<LoginFacebookToken> call, Response<LoginFacebookToken> response) {
//                try {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    if (response.body().getCode() == 0) {
//                        final LoginFacebookToken value = response.body();
//                        token_phet = value.getData().getAccessToken();
//                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//                            @Override
//                            public void onSuccess(final Account account) {
//                                getUserProfileFB("Bearer " + value.getData().getAccessToken(), value.getData().getAccessToken(), account.getPhoneNumber());
//                            }
//                            @Override
//                            public void onError(final AccountKitError error) {
//                                Debug.e(error.toString());
//                            }
//                        });
//                    } else {
//                        Toast.makeText(getContext(), "Số điện thoại đã tồn tại", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
//                    Debug.e("Lỗi: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginFacebookToken> call, Throwable t) {
//                Debug.e("Lỗi: " + t.getMessage());
//            }
//        });
//    }
//    private void actionLogintokenFacebookCodeToken(String accessToken, String provider) {
//        pDialog.setMessage(getString(R.string.text_dialog));
//        pDialog.setCancelable(true);
//        pDialog.show();
//        Call<LoginFacebookToken> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).loginFacebookUserToken(provider,token_phet, accessToken, API.client_id,
//                API.client_secret);
//        call.enqueue(new Callback<LoginFacebookToken>() {
//
//            @Override
//            public void onResponse(Call<LoginFacebookToken> call, Response<LoginFacebookToken> response) {
//                try {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    if (response.body().getCode() == 0) {
//                        final LoginFacebookToken value = response.body();
//                        token_phet = value.getData().getAccessToken();
//                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//                            @Override
//                            public void onSuccess(final Account account) {
//                                getUserProfileFB("Bearer " + value.getData().getAccessToken(), value.getData().getAccessToken(), account.getPhoneNumber());
//                            }
//                            @Override
//                            public void onError(final AccountKitError error) {
//                                Debug.e(error.toString());
//                            }
//                        });
//                    } else {
//                        Toast.makeText(getContext(), "Số điện thoại đã tồn tại", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
//                    Debug.e("Lỗi: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginFacebookToken> call, Throwable t) {
//                Debug.e("Lỗi: " + t.getMessage());
//            }
//        });
//    }
//
//    private void actionLogintokenFacebookAccountkit(final String accessToken, String provider) {
//        pDialog.setMessage(getString(R.string.text_dialog));
//        pDialog.setCancelable(true);
//        pDialog.show();
//        Call<LoginFacebookToken> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Auth.class).loginFacebookUserToken(provider, token_phet, accessToken, API.client_id,
//                API.client_secret);
//        call.enqueue(new Callback<LoginFacebookToken>() {
//
//            @Override
//            public void onResponse(Call<LoginFacebookToken> call, Response<LoginFacebookToken> response) {
//                try {
//                    if (response.body().getCode() == 0) {
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        final LoginFacebookToken value = response.body();
//                        GlobalApp.getInstance().token = value.getData().getAccessToken();
//                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//                            @Override
//                            public void onSuccess(final Account account) {
//                                getUserProfileFB("Bearer " + value.getData().getAccessToken(), value.getData().getAccessToken(), account.getPhoneNumber());
//                            }
//
//                            @Override
//                            public void onError(final AccountKitError error) {
//                            }
//                        });
//                    } else {
//                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
//                    Debug.e("Lỗi: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginFacebookToken> call, Throwable t) {
//                Debug.e("Lỗi: " + t.getMessage());
//            }
//        });
//    }
//
//    private boolean checkInput(String email, String passwd) {
//        boolean flag = true;
//        if (email.equals("")) {
//            edtUserName.setError(getString(R.string.message_error_input_signup_email));
//            flag = false;
//        }
//        if (passwd.equals("")) {
//            edtPassword.setError(getString(R.string.message_error_input_signup_passwd));
//            flag = false;
//        }
//        return flag;
//    }
//
//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        if (hasFocus) {
//            v.getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
//        } else {
//            v.getBackground().setAlpha(80);
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//}
