package com.smile.studio.libsmilestudio.facebook;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.GameRequestDialog;
import com.smile.studio.libsmilestudio.utils.Debug;

public class InviteFacebook {

	private Activity activity = null;
	private GameRequestDialog requestDialog = null;
	CallbackManager callbackManager = null;
	AppInviteDialog inviteDialog = null;

	public InviteFacebook(Activity activity, CallbackManager callbackManager) {
		this.activity = activity;
		this.callbackManager = callbackManager;
		requestDialog = new GameRequestDialog(activity);
		Debug.e("https://developers.facebook.com/quickstarts/?platform=app-links-host");
	}

	public void onActionInviteFacebookGameCanvas(ObjectFacebook object,
			FacebookCallback<GameRequestDialog.Result> callback) {
		requestDialog = new GameRequestDialog(activity);
		GameRequestContent content = new GameRequestContent.Builder().setTitle(object.getTitle())
				.setMessage(object.getDescription()).build();
		requestDialog.registerCallback(callbackManager, callback);
		if (AppInviteDialog.canShow())
			requestDialog.show(content);
	}

	public void onActionInviteFacebookApplication(ObjectFacebook object,
			FacebookCallback<AppInviteDialog.Result> callback) {
		inviteDialog = new AppInviteDialog(activity);
		AppInviteContent content = new AppInviteContent.Builder().setApplinkUrl(object.getUrl())
				.setPreviewImageUrl(object.getImage()).build();
		inviteDialog.registerCallback(callbackManager, callback);
		if (AppInviteDialog.canShow())
			inviteDialog.show(content);
	}

	// public void onActionInviteFacebook(ObjectFacebook object, boolean
	// isGameCanvas) {
	// if (isGameCanvas) {
	// GameRequestContent content = new
	// GameRequestContent.Builder().setTitle(object.getTitle()).setMessage(object.getDescription())
	// .build();
	// if (requestDialog.canShow(content))
	// requestDialog.showInterstitialAd(content);
	// } else {
	// Debug.e("https://developers.facebook.com/quickstarts/?platform=app-links-host");
	// if (AppInviteDialog.canShow()) {
	// AppInviteContent content = new
	// AppInviteContent.Builder().setApplinkUrl(object.getUrl()).build();
	// AppInviteDialog.showInterstitialAd(activity, content);
	// }
	// }
	// }

}
