package com.smile.studio.customplayer.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.smile.studio.customplayer.R;

/**
 * Created by admin on 30/11/2016.
 */

public class ControlViewHelper implements Runnable, ExoPlayer.EventListener {

    private static final int REFRESH_INTERVAL_MS = 5000;
    private RelativeLayout player_loading_view;
    private final SimpleExoPlayer player;
    private final RelativeLayout view;
    private ImageView player_control_play_pause_replay_button;
    private boolean started;
    private long playerPosition;
    public SeekBar time_bar;
    private TextView timer_label;
    private int position, duration;

    public static String formatMillis(int millis) {
        String result = "";
        int hr = millis / 3600000;
        millis %= 3600000;
        int min = millis / 60000;
        millis %= 60000;
        int sec = millis / 1000;
        if (hr > 0) {
            result += hr + ":";
        }
        if (min >= 0) {
            if (min > 9) {
                result += min + ":";
            } else {
                result += "0" + min + ":";
            }
        }
        if (sec > 9) {
            result += sec;
        } else {
            result += "0" + sec;
        }
        return result;
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (player != null) {
                position = (int) player.getCurrentPosition();
                duration = (int) player.getDuration();
                try {
                    timer_label.setText(view.getContext().getString(R.string.text_timer, ControlViewHelper.formatMillis(position), ControlViewHelper.formatMillis(duration)));
                    int buffer = 0;
                    if (duration > 0) {
                        long pos = 1000L * position / duration;
                        buffer = (int) (1000L * player.getBufferedPosition() / duration);
                        time_bar.setProgress((int) pos);
                    }
                    time_bar.setSecondaryProgress(buffer);
                } catch (Exception e) {
                    DebugPlayer.e("Lỗi: " + e.getMessage());
                }
            }
            timer_label.removeCallbacks(mUpdateTimeTask);
            timer_label.postDelayed(mUpdateTimeTask, 1000);
        }
    };


    public ControlViewHelper(final SimpleExoPlayer player, RelativeLayout view) {
        this.player = player;
        this.view = view;
        timer_label = (TextView) view.findViewById(R.id.timer_label);
        time_bar = (SeekBar) view.findViewById(R.id.time_bar);
        time_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean flag) {
                if (flag) {
                    time_bar.removeCallbacks(mUpdateTimeTask);
                    long newposition = (player.getDuration() * progress) / 1000L;
                    player.seekTo(newposition);
                    player.setPlayWhenReady(true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        player_loading_view = (RelativeLayout) view.findViewById(R.id.player_loading_view);
        player_control_play_pause_replay_button = (ImageView) view.findViewById(R.id.player_control_play_pause_replay_button);
    }

    /**
     * Starts periodic updates of the {@link TextView}. Must be called from the application's main
     * thread.
     */
    public void start() {
        if (started) {
            return;
        }
        started = true;
        player.addListener(this);
        updateAndPost();
    }

    /**
     * Stops periodic updates of the {@link TextView}. Must be called from the application's main
     * thread.
     */
    public void stop() {
        if (!started) {
            return;
        }
        started = false;
        player.removeListener(this);
        view.removeCallbacks(this);
        timer_label.removeCallbacks(mUpdateTimeTask);
    }

    // ExoPlayer.EventListener implementation.

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        updateAndPost();
    }

    @Override
    public void onPositionDiscontinuity() {
        updateAndPost();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing.
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        // Do nothing.
    }

    // Runnable implementation.

    @Override
    public void run() {
        updateAndPost();
    }

    // Private methods.

    private void updateAndPost() {
        getPlayerStateString();
//        textView.setText(getPlayerStateString() + getPlayerWindowIndexString() + getVideoString() + getAudioString());
        view.removeCallbacks(this);
        view.postDelayed(this, REFRESH_INTERVAL_MS);
        timer_label.removeCallbacks(mUpdateTimeTask);
        timer_label.postDelayed(mUpdateTimeTask, 1000);
    }

    private String getPlayerStateString() {
        String text = "playWhenReady:" + player.getPlayWhenReady() + " playbackState:";
        switch (player.getPlaybackState()) {
            case ExoPlayer.STATE_BUFFERING:
                text += "buffering";
                player_loading_view.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_ENDED:
                text += "ended";
                view.setVisibility(View.VISIBLE);
                player_loading_view.setVisibility(View.GONE);
                player_control_play_pause_replay_button.setImageResource(R.drawable.ic_vidcontrol_pause_play_11);
                timer_label.removeCallbacks(mUpdateTimeTask);
                view.removeCallbacks(this);
                player.removeListener(this);
                player.setPlayWhenReady(false);
                break;
            case ExoPlayer.STATE_IDLE:
                text += "idle";
                view.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_READY:
                text += "ready";
//                player_control_play_pause_replay_button.setImageResource(R.drawable.ic_vidcontrol_pause_play_00);
                player_loading_view.setVisibility(View.GONE);
                view.setVisibility(View.INVISIBLE);
                break;
            default:
                text += "unknown";
                view.setVisibility(View.VISIBLE);
                break;
        }
        return text;
    }

    private String getPlayerWindowIndexString() {
        return " window:" + player.getCurrentWindowIndex();
    }

    private String getVideoString() {
        Format format = player.getVideoFormat();
        if (format == null) {
            return "";
        }
        return "\n" + format.sampleMimeType + "(id:" + format.id + " r:" + format.width + "x"
                + format.height + getDecoderCountersBufferCountString(player.getVideoDecoderCounters())
                + ")";
    }

    private String getAudioString() {
        Format format = player.getAudioFormat();
        if (format == null) {
            return "";
        }
        return "\n" + format.sampleMimeType + "(id:" + format.id + " hz:" + format.sampleRate + " ch:"
                + format.channelCount
                + getDecoderCountersBufferCountString(player.getAudioDecoderCounters()) + ")";
    }

    private static String getDecoderCountersBufferCountString(DecoderCounters counters) {
        if (counters == null) {
            return "";
        }
        counters.ensureUpdated();
        return " rb:" + counters.renderedOutputBufferCount
                + " sb:" + counters.skippedOutputBufferCount
                + " db:" + counters.droppedOutputBufferCount
                + " mcdb:" + counters.maxConsecutiveDroppedOutputBufferCount;
    }

}
