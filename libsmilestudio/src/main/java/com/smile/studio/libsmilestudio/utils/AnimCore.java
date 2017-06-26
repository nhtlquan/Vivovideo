package com.smile.studio.libsmilestudio.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimCore {
    public static void start(Context ctx, int anim, ImageView view) {
        Animation rotate = AnimationUtils.loadAnimation(ctx, anim);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(rotate);
    }

    public static void stop(ImageView view) {
        view.setAnimation(null);
        view.setVisibility(View.INVISIBLE);
    }

    public static void anim(final View view, final int anim,
                            final boolean hide, final long duration) {
        Animation viewAnim = AnimationUtils.loadAnimation(view.getContext(),
                anim);
        viewAnim.setDuration(duration);

        viewAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (hide)
                    view.setVisibility(View.GONE);
                else
                    view.setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(viewAnim);
    }
}
