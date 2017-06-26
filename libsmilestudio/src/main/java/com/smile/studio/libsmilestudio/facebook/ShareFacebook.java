package com.smile.studio.libsmilestudio.facebook;

import android.app.Activity;
import android.net.Uri;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.smile.studio.libsmilestudio.utils.Debug;

public class ShareFacebook {

	private static ShareDialog shareDialog = null;
	private ShareLinkContent shareLinkContent = null;
	private CallbackManager callbackManager = null;

	public ShareFacebook(Activity activity, CallbackManager callbackManager) {
		shareDialog = new ShareDialog(activity);
		this.callbackManager = callbackManager;
	}

	public void onActionShareFacebook(ObjectFacebook object, FacebookCallback<Sharer.Result> callback) {
		shareLinkContent = new ShareLinkContent.Builder().setContentTitle(object.getTitle())
				.setContentDescription(object.getDescription()).setContentUrl(Uri.parse(object.getUrl()))
				.setImageUrl(Uri.parse(object.getImage())).build();
		if (hasPublishPermission())
			ShareApi.share(shareLinkContent, callback);
		else if (ShareDialog.canShow(ShareLinkContent.class)) {
			shareDialog.registerCallback(callbackManager, callback);
			shareDialog.show(shareLinkContent);
			Debug.e("CAN showInterstitialAd");
		} else {
			Debug.e("Not showInterstitialAd");
		}
	}

	public boolean hasPublishPermission() {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		return accessToken != null && accessToken.getPermissions().contains("publish_actions");
	}

}
