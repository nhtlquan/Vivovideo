package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.CommentItem.CommentRequest;
import com.smile.studio.network.ver2.model.CommentItem.ItemComment;
import com.smile.studio.network.ver2.model.CommentItem.LikeSuccess;
import com.smile.studio.network.ver2.model.CommentRespone.CommentSuccess;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Comment {
    @GET("comment/{type}/{ID}/list")
    Call<ItemComment> getListComment(@Path("type") String type, @Path("ID") int ID, @Query("page") int page, @Query("pageSize") int pageSize, @Header("Authorization") String header);

    @POST("comment/{type}/{ID}/create")
    Call<CommentSuccess> creatComment(@Path("type") String type, @Path("ID") int ID, @Body CommentRequest commentRequest, @Header("Authorization") String header);

    @POST("comment/{ID}/like")
    Call<LikeSuccess> creatLike(@Path("ID") int ID, @Header("Authorization") String header);
}
