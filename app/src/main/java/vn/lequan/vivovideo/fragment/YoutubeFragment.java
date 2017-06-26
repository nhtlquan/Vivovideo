package vn.lequan.vivovideo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.smile.studio.libsmilestudio.utils.Debug;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 02/09/2016.
 */
public class YoutubeFragment extends Fragment {

    private View view;
    private YouTubePlayerSupportFragment playerFragment;

    public YoutubeFragment() {
    }

    public static YoutubeFragment newInstance(String videoID) {
        Bundle args = new Bundle();
        args.putString(YoutubeFragment.class.getSimpleName(), videoID);
        YoutubeFragment fragment = new YoutubeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        playerFragment.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_youtube, container, false);
            playerFragment = (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.tubePlayerFragment);
            playerFragment.initialize(GlobalApp.KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(getArguments().getString(YoutubeFragment.class.getSimpleName()));
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Debug.e("Lỗi: " + youTubeInitializationResult.toString());
                }
            });
        }
        return view;
    }
}
