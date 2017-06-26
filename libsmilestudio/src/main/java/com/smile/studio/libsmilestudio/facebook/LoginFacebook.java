package com.smile.studio.libsmilestudio.facebook;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class LoginFacebook {

	public static void onActionLoginFacebook(Activity activity, CallbackManager callbackManager, FacebookCallback<LoginResult> callback) {
		LoginManager.getInstance().registerCallback(callbackManager, callback);
		LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile", "user_friends"));
	}
}
