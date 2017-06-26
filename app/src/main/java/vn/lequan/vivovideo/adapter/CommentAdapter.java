package vn.lequan.vivovideo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.SharedPreferencesUtils;
import com.smile.studio.network.model.API;
import com.smile.studio.network.ver2.face.Face_Comment;
import com.smile.studio.network.ver2.model.CommentItem.Child;
import com.smile.studio.network.ver2.model.CommentItem.Comment;
import com.smile.studio.network.ver2.model.CommentItem.LikeSuccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.Interface_Comment;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.AccountActivity;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.model.GlobalApp;


/**
 * Ada
 * Created by admin on 16/08/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Comment> lstData;
    private int size;
    public Interface_Comment face_comment;

    public void setList(Interface_Comment m_Comment) {
        face_comment = m_Comment;
    }


    public CommentAdapter(Context context, List<Comment> lstData) {
        this.context = context;
        this.lstData = lstData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_comment, parent, false);
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

    public void addAll(List<Comment> data) {
        this.lstData.addAll(data);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }


    public void clean() {
        lstData.clear();
        notifyDataSetChanged();
    }

    public Comment getCommentAtPosition(int position) {
        return lstData.get(position);
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private RecyclerView recyclerView;
        private GridLayoutManager layout;
        private CommentAdapter_Child commentAdapter_child;
        private TextView time, text_Name, text_Content, like, dislike, action_comment, action_like;
        private Comment data_Comment;
        private int number_like;
        private String user_name;

        public MyViewHolder(View view) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            time = (TextView) view.findViewById(R.id.time);
            text_Name = (TextView) view.findViewById(R.id.text_Name);
            text_Content = (TextView) view.findViewById(R.id.text_Content);
            action_comment = (TextView) view.findViewById(R.id.action_comment);
            action_like = (TextView) view.findViewById(R.id.action_like);
            like = (TextView) view.findViewById(R.id.like);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            commentAdapter_child = new CommentAdapter_Child(context.getApplicationContext(), new ArrayList<Child>());
            layout = new GridLayoutManager(context.getApplicationContext(), 1);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(commentAdapter_child);
            recyclerView.setHasFixedSize(true);
            action_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    face_comment.commentParent(data_Comment.getId(), data_Comment.getUser().getProfile().getPhone());
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void init(final Comment Comment) {
            try {
                data_Comment = Comment;
                String user_name = "";
                user_name = data_Comment.getUser().getProfile().getFirstName();
                TextDrawable drawable1 = TextDrawable.builder()
                        .buildRound(String.valueOf(user_name.charAt(0)).toUpperCase(), GlobalApp.getColor(String.valueOf(user_name.charAt(0))));// set avatar with random color
                if (data_Comment.getUser().getProfile().getAvatarUrl() != null) {
                    Glide.with(context).load(data_Comment.getUser().getProfile().getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(avatar) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            avatar.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                } else {
                    avatar.setImageDrawable(drawable1);
                }
                if (Comment.getChildren().size() >= 1) { // check comment child and set adapter
                    recyclerView.setVisibility(View.VISIBLE);
                    commentAdapter_child.clean();
                    commentAdapter_child.addAll(Comment.getChildren());
                } else {
                    recyclerView.setVisibility(View.GONE);
                }

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");// set date time of comment
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                try {
                    Date date = (Date) sdf.parse(Comment.getCreatedAt());
                    time.setText(converteTimestamp(String.valueOf(date.getTime())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                String phone;
//                if (data_Comment.getUser().getProfile().getPhone() != null)
//                    phone = data_Comment.getUser().getProfile().getPhone();
                text_Name.setText(user_name);
//                if (!data_Comment.getUser().getProfile().getFirstName().equals("New User")) {
//                    text_Name.setText(data_Comment.getUser().getProfile().getPhone());
//                } else {
//                    text_Name.setText(data_Comment.getUser().getProfile().getFirstName() + " " + data_Comment.getUserId());
//                }
//            text_Name.setText(user_name);
                Typeface font_bold = Typeface.createFromAsset(// set style text bold for Name
                        context.getAssets(),
                        "fonts/Roboto-Bold.ttf");
                text_Name.setTypeface(font_bold);
                text_Content.setText(Comment.getContent());
                like.setText(String.valueOf(Comment.getLikes()) + " like");
                if (Comment.getUserLike() != null) {
                    if (Comment.getUserLike() == 0) {
                        action_like.setTextColor(context.getResources().getColor(R.color.white));
                    } else {
                        action_like.setTextColor(context.getResources().getColor(R.color.green));
                    }
                }
                final int like_s = Comment.getLikes();
                number_like = like_s;
                action_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!SharedPreferencesUtils.getBoolean(context, API.checkLogin)) {
                            Intent intent = new Intent(context, AccountActivity.class);
                            ((Activity) context).startActivityForResult(intent, MainActivity.RESULTCODE_LOGIN_ACCOUNT);
                            return;
                        }

                        Call<LikeSuccess> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Comment.class).creatLike(Comment.getId(), GlobalApp.getInstance().acessToken);
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
            } catch (Exception e) {
                Debug.e("Lỗi: " + e.getMessage());
            }
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private CharSequence converteTimestamp(String mileSegundos) {
        return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSegundos), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

}

