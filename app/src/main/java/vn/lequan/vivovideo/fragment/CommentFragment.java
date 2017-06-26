package vn.lequan.vivovideo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.studio.libsmilestudio.recyclerviewer.EndlessRecyclerOnScrollListener;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Comment;
import com.smile.studio.network.ver2.model.CommentItem.Comment;
import com.smile.studio.network.ver2.model.CommentItem.CommentRequest;
import com.smile.studio.network.ver2.model.CommentItem.ItemComment;
import com.smile.studio.network.ver2.model.CommentRespone.CommentSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.Interface_Comment;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.AccountActivity;
import vn.lequan.vivovideo.adapter.CommentAdapter;
import vn.lequan.vivovideo.model.GlobalApp;
import vn.lequan.vivovideo.model.TYPEITEM;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by admin on 20/08/2016.
 */
public class CommentFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager layout;
    public static String String_ID = "ID";
    public static String String_typeitem = "typeitem";
    private CommentAdapter commentAdapter;
    private ImageView btn_send;
    private String[] keywordArray;
    private EditText edt_comment;
    private String commentText;
    private int page = 1, pagesize = 10;
    private String parent_ID;
    private TextView reply_To;
    private ProgressBar marker_progress;
    private TextView txt_error;
    private boolean check_resendSMS =true;

    public CommentFragment() {
    }

    public static CommentFragment newInstance(int ID, TYPEITEM typeitem) {
        Bundle args = new Bundle();
        args.putInt(String_ID, ID);
        args.putString(String_typeitem, typeitem.getValue());
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CommentFragment newInstance() {
        Bundle args = new Bundle();
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_comment, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            commentAdapter = new CommentAdapter(getActivity(), new ArrayList<Comment>());
            btn_send = (ImageView) view.findViewById(R.id.btn_send);
            txt_error = (TextView) view.findViewById(R.id.txt_error);
            edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            reply_To = (TextView) view.findViewById(R.id.reply_To);
            layout = new GridLayoutManager(getActivity(), 1, VERTICAL, true);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(commentAdapter);
//            recyclerView.setHasFixedSize(true);
            getListComment(getArguments().getInt(String_ID));
            recyclerView.scrollToPosition(0);
            loadMore();
            reply_To.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parent_ID = "";
                    reply_To.setVisibility(View.GONE);
                }
            });
            commentAdapter.setList(new Interface_Comment() {
                @Override
                public void commentParent(int parentId, String name) {
                    edt_comment.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                    String sourceString = "Đang trả lời cho " + "<b>" + name + "</b> ";
                    reply_To.setText(Html.fromHtml(sourceString));
                    reply_To.setVisibility(View.VISIBLE);
                    parent_ID = String.valueOf(parentId);
                }
            });
            keywordArray = getResources().getStringArray(R.array.keyword);
            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    if (!SharedPreferencesUtils.getBoolean(getActivity(), API.checkLogin)) {
                        Intent intent = new Intent(getActivity(), AccountActivity.class);
                        getActivity().startActivity(intent);
                        return;
                    }
                    if (!check_resendSMS) {
                        Toast.makeText(getActivity(), "Mỗi bình luận cách nhau 5 giây", Toast.LENGTH_LONG).show();
                        return;
                    }
                    countTimer();
                    commentText = edt_comment.getText().toString();
                    if (Arrays.asList(keywordArray).contains(commentText)) {
                        commentText = "********";
                    }
                    Debug.e(GlobalApp.getInstance().acessToken);
                    CommentRequest commentRequest = new CommentRequest(commentText, parent_ID);
                    Call<CommentSuccess> call = GlobalApp.getInstance().retrofit_ver2.create(com.smile.studio.network.ver2.face.Face_Comment.class).creatComment(getArguments().getString(String_typeitem), getArguments().getInt(String_ID), commentRequest, GlobalApp.getInstance().acessToken);
                    call.enqueue(new Callback<CommentSuccess>() {
                        @Override
                        public void onResponse(Call<CommentSuccess> call, Response<CommentSuccess> response) {
                            try {
                                page = 1;
                                commentAdapter.clean();
                                getListComment(getArguments().getInt(String_ID));
                                recyclerView.scrollToPosition(0);
                                edt_comment.getText().clear();
                                parent_ID = "";
                                reply_To.setVisibility(View.GONE);
                                View view = getActivity().getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                            } catch (Exception e) {
                                Debug.e("Lỗi: " + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<CommentSuccess> call, Throwable t) {
                            Debug.e("Lỗi: " + t.getMessage());
                        }
                    });

                }
            });
        }
        return view;
    }

    private void countTimer() {
        check_resendSMS = false;
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                check_resendSMS = true;
            }

        }.start();
    }

    private void loadMore() {
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout, page, pagesize) {
            @Override
            public void onLoadMore(int current_page, int totalItemsCount, RecyclerView view) {
                page = current_page;
                getListComment(getArguments().getInt(String_ID));
            }
        });
    }

    public void getListComment(int ID) {
        marker_progress.setVisibility(View.VISIBLE);
        Call<ItemComment> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Comment.class).getListComment(getArguments().getString(String_typeitem), ID, page, pagesize, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<ItemComment>() {
            @Override
            public void onResponse(Call<ItemComment> call, Response<ItemComment> response) {
                try {
                    if (response.body().getData().getComments().size() == 0) {
                        marker_progress.setVisibility(View.GONE);
                        return;
                    }
                    ItemComment value = response.body();
                    if (value.getData().getComments().size() > 0) {
                        commentAdapter.addAll(value.getData().getComments());
                    }
                    marker_progress.setVisibility(View.GONE);

                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ItemComment> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

}
