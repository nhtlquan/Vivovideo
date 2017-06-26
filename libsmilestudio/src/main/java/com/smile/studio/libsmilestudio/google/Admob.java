//package com.smile.studio.libsmilestudio.google;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import com.google.ads.mediation.admob.AdMobAdapter;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.NativeExpressAdView;
//import com.google.android.gms.ads.reward.RewardedVideoAd;
//import com.google.android.gms.ads.reward.RewardedVideoAdListener;
//import com.smile.studio.libsmilestudio.BuildConfig;
//import com.smile.studio.libsmilestudio.security.MD5;
//import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
//
//import java.util.Locale;
//
//public class Admob {
//
//    private String deviceID = "8436CEFBDDF0723ED6956BFDFC06EB10";
//    protected AdRequest request = null;
//    protected InterstitialAd interstitialAd = null;
//    private AdView adView = null;
//    public RewardedVideoAd mAd;
//
//    @Deprecated
//    public Admob() {
//        AdRequest.Builder Builder = new AdRequest.Builder();
//        if (BuildConfig.DEBUG) {
//            Builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
//            Builder.addTestDevice(deviceID);
//        }
//        request = Builder.build();
//    }
//
//    public Admob(Context context) {
//        AdRequest.Builder Builder = new AdRequest.Builder();
//        if (BuildConfig.DEBUG) {
//            deviceID = MD5.encrypt(AndroidDeviceInfo.getAndroidID(context)).toUpperCase(Locale.getDefault());
//            Builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
//            Builder.addTestDevice(deviceID);
//        }
//        request = Builder.build();
//    }
//
//    public void BannerAdmob(Context context, ViewGroup view, String adUnitId, AdListener adListener) {
//        this.adView = new AdView(context);
//        this.adView.setAdUnitId(adUnitId);
//        this.adView.setAdSize(AdSize.SMART_BANNER);
//        this.adView.loadAd(request);
//        this.adView.setAdListener(adListener);
//        final FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
//                Gravity.RIGHT | Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//        adView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        view.addView(adView, adViewLayoutParams);
//    }
//
//    public void InterstitialAdmob(Context context, String adUnitId, AdListener adListener) {
//        interstitialAd = new InterstitialAd(context);
//        interstitialAd.setAdUnitId(adUnitId);
//        interstitialAd.setAdListener(adListener);
//        interstitialAd.loadAd(request);
//    }
//
//    public void NativeAdmob(Context context, ViewGroup view, String adUnitId, AdListener adListener) {
//        NativeExpressAdView adView = new NativeExpressAdView(context);
//        adView.setAdUnitId(adUnitId);
//        adView.setAdSize(new AdSize(360, 100));
//        adView.setAdListener(adListener);
//        adView.loadAd(request);
//        final FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
//                Gravity.RIGHT | Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//        adView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        view.addView(adView, adViewLayoutParams);
//    }
//
//    public void RewardedVideoAdmob(Context context, ViewGroup view, String adUnitId, RewardedVideoAdListener adListener) {
//        mAd = MobileAds.getRewardedVideoAdInstance(context);
//        mAd.setRewardedVideoAdListener(adListener);
//        Bundle extras = new Bundle();
//        extras.putBoolean("_noRefresh", true);
//        AdRequest adRequest = new AdRequest.Builder()
//                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
//                .build();
//        mAd.loadAd(adUnitId, adRequest);
//    }
//
//    public void reload() {
//        interstitialAd.loadAd(request);
//    }
//
//    public void InterstitialShow() {
//        if (interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        }
//    }
//
//    public void showRewardedVideoAdmob() {
//        if (mAd.isLoaded())
//            mAd.show();
//    }
//
//    public void destroyRewardedVideoAdmob(Context context) {
//        mAd.destroy(context);
//    }
//}
