package vn.lequan.vivovideo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.smile.studio.libsmilestudio.utils.Debug;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 19/08/2016.
 */
public class AboutFragment extends Fragment {

    private View view;
    private TextView editText;

    public AboutFragment() {
    }

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //        GlobalApp.getInstance().fragment = AboutFragment.newInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_browser, container, false);
            editText = (TextView) view.findViewById(R.id.editText);
            editText.setText(getString(R.string.text_abount));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");
    }
}
