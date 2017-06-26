package vn.lequan.vivovideo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.AndroidUtils;

import vn.lequan.vivovideo.R;

import static com.facebook.FacebookSdk.getApplicationContext;


@SuppressLint("ValidFragment")
public class DialogExit extends DialogFragment {
    private View view;
    private Point point;
    private TextView txtxemsau, txtyeuthich, txtshare,textView23;
    public static SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "DANHGIA";
    int PRIVATE_MODE = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_dialog_exit, container, false);
            point = AndroidDeviceInfo.getScreenSize(getContext());
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
            txtshare = (TextView) view.findViewById(R.id.txt_share);
            txtyeuthich = (TextView) view.findViewById(R.id.txt_yeuthich);
            txtxemsau = (TextView) view.findViewById(R.id.txt_xemsau);
            textView23 = (TextView) view.findViewById(R.id.textView23);
            String text = "<font color=#ffffff>Bạn vui lòng bớt chút thời gian để đánh giá sản phẩm của chúng tôi!</font>";
            textView23.setText(Html.fromHtml(text));
            txtxemsau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.putBoolean("check", false);
                    edit.apply();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AndroidUtils.gotoMaketStore(getActivity().getPackageName())));
                    startActivity(intent);
                }
            });
            txtyeuthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
            txtshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.putBoolean("check", false);
                    edit.apply();
                    getActivity().finish();
                }
            });
        }
        return view;
    }
}