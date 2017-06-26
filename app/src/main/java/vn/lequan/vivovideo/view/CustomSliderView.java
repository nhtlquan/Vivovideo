package vn.lequan.vivovideo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import vn.lequan.vivovideo.R;


/**
 * Created by admin on 13/10/2016.
 */

public class CustomSliderView extends BaseSliderView {
    private ImageView target;
    private Context context;

    public CustomSliderView(Context context) {
        super(context);
        this.context = context;
    }

    public View getView() {
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.render_type_text, null);
        target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
        LinearLayout frame = (LinearLayout) v.findViewById(R.id.description_layout);
        frame.setVisibility(View.INVISIBLE);
        frame.setBackgroundColor(Color.TRANSPARENT);
        int i = v.getResources().getColor(R.color.background_signon);
        Object localObject2 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{i, 0});
        this.bindEventAndShow(v, target);
        target.setBackground((Drawable) localObject2);
        target.setBackgroundDrawable((Drawable) localObject2);
        v.setBackground((Drawable) localObject2);
        v.setBackgroundDrawable((Drawable) localObject2);
        return v;
    }

}