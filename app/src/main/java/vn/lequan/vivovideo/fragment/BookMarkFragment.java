package vn.lequan.vivovideo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.ListBookMarkAdapter;
import vn.lequan.vivovideo.adapter.VideoAdapter;
import vn.lequan.vivovideo.adapter.VideoAdapterListBookMark;
import vn.lequan.vivovideo.model.TYPEITEM;

/**
 * Created by admin on 17/08/2016.
 */
public class BookMarkFragment extends Fragment {

    private View view;
    private static final String OBJECT = "object";
    private static final String OB_type = "type";
    private int index = -1, page = 1, pagesize = 10, numberColumns = 3;
    private GridLayoutManager layout;
    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private RelativeLayout layout_title;
    private String type;

    public BookMarkFragment() {
    }

    public static BookMarkFragment newInstance(ListBookMarkAdapter videoAdapterSub, String value) {
        Bundle args = new Bundle();
        BookMarkFragment fragment = new BookMarkFragment();
        args.putParcelable(OBJECT, videoAdapterSub);
        args.putString(OB_type, value);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookMarkFragment newInstance(VideoAdapterListBookMark videoAdapterSub, String value) {
        Bundle args = new Bundle();
        BookMarkFragment fragment = new BookMarkFragment();
        args.putParcelable(OBJECT, videoAdapterSub);
        args.putString(OB_type, value);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.custom_item_home_test, container, false);
            layout_title = (RelativeLayout) view.findViewById(R.id.layout_title);
            layout_title.setVisibility(View.GONE);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
            Object data = getArguments().getParcelable(OBJECT);
            type = getArguments().getString(OB_type);
            if (type.equals(TYPEITEM.VIDEO.getValue())) {
                loadDataVideo((VideoAdapterListBookMark) data);
            } else if (type.equals(TYPEITEM.MOVIE.getValue())) {
                loadDataMovie((ListBookMarkAdapter) data);
            } else if (type.equals(TYPEITEM.SERIES.getValue())) {
                loadDataVideo((VideoAdapterListBookMark) data);
            }

        }
        return view;
    }

    private void loadDataVideo(VideoAdapterListBookMark data) {
        layout = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(data);
    }

    private void loadDataMovie(ListBookMarkAdapter data) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(data);
    }


}
