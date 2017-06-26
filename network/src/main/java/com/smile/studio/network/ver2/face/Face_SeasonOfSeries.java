package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.CommentItem.CommentRequest;
import com.smile.studio.network.ver2.model.CommentItem.ItemComment;
import com.smile.studio.network.ver2.model.CommentItem.LikeSuccess;
import com.smile.studio.network.ver2.model.CommentRespone.CommentSuccess;
import com.smile.studio.network.ver2.model.SeasonOfSeries.SeasonOfSeries;

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
public interface Face_SeasonOfSeries {
    @GET("series/{ID}/season")
    Call<SeasonOfSeries> getListSeason(@Path("ID") int ID, @Query("platform") String platform);

}
