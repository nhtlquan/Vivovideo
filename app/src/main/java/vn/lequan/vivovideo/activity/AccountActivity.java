package vn.lequan.vivovideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.fragment.InfoAccountFragment;
import vn.lequan.vivovideo.google.analytics.AnalyticsApplication;
import vn.lequan.vivovideo.model.GlobalApp;

public class AccountActivity extends AppCompatActivity {

    public CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        callbackManager = CallbackManager.Factory.create();
        if (SharedPreferencesUtils.getBoolean(this, API.checkLogin) == null || SharedPreferencesUtils.getBoolean(this, API.checkLogin)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, new InfoAccountFragment())
                    .commit();
        } else {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment, new LoginFragment())
//                    .commit();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
