package vn.lequan.vivovideo.view;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by admin on 01/09/2016.
 */
public class CustomSlidingPaneLayout extends SlidingPaneLayout {
    public CustomSlidingPaneLayout(Context context) {
        super(context);
    }

    public CustomSlidingPaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!isOpen()) return false;
        return super.onInterceptTouchEvent(arg0);
    }
}
