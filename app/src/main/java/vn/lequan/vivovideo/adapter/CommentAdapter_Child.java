package vn.lequan.vivovideo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Comment;
import com.smile.studio.network.ver2.model.CommentItem.Child;
import com.smile.studio.network.ver2.model.CommentItem.LikeSuccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.AccountActivity;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.model.GlobalApp;


/**
 * Ada
 * Created by admin on 16/08/2016.
 */
public class CommentAdapter_Child extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Child> lstData;
    private Point point;
    private RequestManager manager;
    private int numberColmns = 0;
    private boolean isVideo;
    private FragmentManager fragmentManager;
    private PopupWindow mPopupWindow;
    private ProgressDialog pDialog;
    private int size;

    public CommentAdapter_Child(Context context, List<Child> lstData) {
        this.context = context;
        this.lstData = lstData;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    public CommentAdapter_Child(FragmentManager fragmentManager, Context context, List<Child> lstData, int numberColmns) {
        this.context = context;
        this.lstData = lstData;
        this.fragmentManager = fragmentManager;
        this.numberColmns = numberColmns;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    public CommentAdapter_Child(Context context, List<Child> lstData, int numberColmns, boolean isVideo) {
        this.context = context;
        this.lstData = lstData;
        this.numberColmns = numberColmns;
        this.isVideo = isVideo;
        point = AndroidDeviceInfo.getScreenSize(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_comment_child, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(lstData.get(position));
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<Child> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
//        notifyItemRangeInserted(size, getItemCount());
    }

    public void add(Child data) {
        this.lstData.add(data);
        notifyDataSetChanged();
    }

    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public Child getChildAtPosition(int position) {
        return lstData.get(position);
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private String user_name;
        private int number_like;
        private TextView time, text_Name, text_Content, like, action_like;

        public MyViewHolder(View view) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            time = (TextView) view.findViewById(R.id.time);
            text_Name = (TextView) view.findViewById(R.id.text_Name);
            action_like = (TextView) view.findViewById(R.id.action_like);
            text_Content = (TextView) view.findViewById(R.id.text_Content);
            like = (TextView) view.findViewById(R.id.like);
        }

        @SuppressLint("SetTextI18n")
        public void init(final Child child) {
//            user_name = ChildCategory.getUser().getProfile().getFirstName() + " " + ChildCategory.getUser().getProfile().getLastName();
            String user_name = "";
            user_name = child.getUser().getProfile().getFirstName();
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound(String.valueOf(user_name.charAt(0)).toUpperCase(), GlobalApp.getColor(String.valueOf(user_name.charAt(0))));
            avatar.setImageDrawable(drawable1);
            if (child.getUser().getProfile().getAvatarUrl() != null) {
                Glide.with(context)
                        .load(child.getUser().getProfile().getAvatarUrl())
                        .asBitmap()
                        .error(R.drawable.com_facebook_profile_picture_blank_square)
                        .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .thumbnail(0.1f)
                        .into(avatar);
            } else {
                avatar.setImageDrawable(drawable1);
            }
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                Date date = (Date) sdf.parse(child.getCreatedAt());
                time.setText(converteTimestamp(String.valueOf(date.getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            text_Name.setText(user_name);
            Typeface font_bold = Typeface.createFromAsset(
                    context.getAssets(),
                    "fonts/Roboto-Bold.ttf");
            text_Name.setTypeface(font_bold);
            text_Content.setText(child.getContent());
            like.setText(String.valueOf(child.getLikes()) + " like");
            action_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!SharedPreferencesUtils.getBoolean(context, API.checkLogin)) {
                        Intent intent = new Intent(context, AccountActivity.class);
                        ((Activity) context).startActivityForResult(intent,  MainActivity.RESULTCODE_LOGIN_ACCOUNT);
                        return;
                    }

                    Call<LikeSuccess> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Comment.class).creatLike(child.getId(), GlobalApp.getInstance().acessToken);
                    call.enqueue(new Callback<LikeSuccess>() {
                        @Override
                        public void onResponse(Call<LikeSuccess> call, Response<LikeSuccess> response) {
                            try {
                                LikeSuccess likeSuccess = response.body();
                                if (likeSuccess.getData().getIsLike() == 1) {
                                    if (response.body().getCode() == 0) {
                                        number_like = number_like + 1;
                                        like.setText(String.valueOf(number_like) + " like");
                                        action_like.setTextColor(context.getResources().getColor(R.color.green));
                                    } else {
                                        action_like.setTextColor(context.getResources().getColor(R.color.white));
                                    }
                                } else {
                                    if (response.body().getCode() == 0) {
                                        number_like = number_like - 1;
                                        like.setText(String.valueOf(number_like) + " like");
                                        action_like.setTextColor(context.getResources().getColor(R.color.white));
                                    } else {
                                        action_like.setTextColor(context.getResources().getColor(R.color.green));
                                    }
                                }
                            } catch (Exception e) {
                                Debug.e("Lỗi: " + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<LikeSuccess> call, Throwable t) {
                            Debug.e("Lỗi: " + t.getMessage());
                        }
                    });
                }
            });
        }

    }

    private CharSequence converteTimestamp(String mileSegundos) {
        return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSegundos), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

}

