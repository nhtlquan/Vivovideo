package vn.lequan.vivovideo.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.network.ver2.face.Face_Ratting;
import com.smile.studio.network.ver2.model.Ratting.Ratting;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

@SuppressLint("ValidFragment")
public class DialogRating extends DialogFragment {
    private View view;
    private MaterialRatingBar ratingbar;
    private TextView edt_rating;
    private Button img_Huy, img_DanhGia;
    private String type;
    private int ID;


    public DialogRating(String type, int ID) {
        super();
        this.type = type;
        this.ID = ID;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_popup_rating, container, false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ratingbar = (MaterialRatingBar) view.findViewById(R.id.ratingbar);
            edt_rating = (TextView) view.findViewById(R.id.edt_rating);
            img_Huy = (Button) view.findViewById(R.id.img_Huy);
            img_DanhGia = (Button) view.findViewById(R.id.img_DanhGia);
            ratingbar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    if (rating == 0.5 || rating == 1.0) {
                        edt_rating.setText(R.string.channel_rating_description_1);
                    } else if (rating == 1.5 || rating == 2.0) {
                        edt_rating.setText(R.string.channel_rating_description_2);
                    } else if (rating == 2.5 || rating == 3.0) {
                        edt_rating.setText(R.string.channel_rating_description_3);
                    } else if (rating == 3.5 || rating == 4.0) {
                        edt_rating.setText(R.string.channel_rating_description_4);
                    } else if (rating == 4.5 || rating == 5.0) {
                        edt_rating.setText(R.string.channel_rating_description_5);
                    }
                    Debug.e(String.valueOf(rating));
                }
            });
            img_DanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Ratting> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Ratting.class).setRatting(type, ID, (int) ratingbar.getRating(), GlobalApp.getInstance().acessToken);
                    call.enqueue(new Callback<Ratting>() {
                        @Override
                        public void onResponse(Call<Ratting> call, Response<Ratting> response) {
                            try {
                                getDialog().dismiss();
                                Toast.makeText(getActivity(), "Bạn đã đánh giá " + String.valueOf(ratingbar.getRating()) + " sao", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Debug.e("Lỗi: " + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<Ratting> call, Throwable t) {
                            Debug.e("Lỗi: " + t.getMessage());
                        }
                    });

                }
            });
            img_Huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });
        }
        return view;
    }
}
