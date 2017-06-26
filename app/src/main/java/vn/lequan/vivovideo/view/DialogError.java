package vn.lequan.vivovideo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smile.studio.libsmilestudio.utils.AndroidUtils;

import vn.lequan.vivovideo.R;

@SuppressLint("ValidFragment")
public class DialogError extends DialogFragment {
    private View view;
    private TextView btn_signin;
    private TextView txt_message;
    private String message;
    private String action;
    private String action_click;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    public DialogError(String message, String action, String action_click) {
        this.message = message;
        this.action = action;
        this.action_click = action_click;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_popup_thongbao, container, false);
            btn_signin = (TextView) view.findViewById(R.id.btn_signin);
            txt_message = (TextView) view.findViewById(R.id.txt_message);
            txt_message.setText(message);
            btn_signin.setText(action);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            btn_signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (action_click.equals("update")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AndroidUtils.gotoMaketStore(getActivity().getPackageName())));
                        startActivity(intent);
                    } else {
                        getActivity().finish();
                    }

                }
            });
        }
        return view;
    }
}
