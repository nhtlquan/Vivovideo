package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.BetMatch.BetMatch;
import com.smile.studio.network.ver2.model.QuestionByID.QuestionById;
import com.smile.studio.network.ver2.model.TopBet.TopBet;
import com.smile.studio.network.ver2.model.UserBet.UserBet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_BetMatch {
    @GET("bet/match")
    Call<BetMatch> getBetMatch(@Header("Authorization") String header, @Query("platform") String platform);

    @GET("bet/getByUser")
    Call<BetMatch> getHistoryBetMatch(@Header("Authorization") String header, @Query("sport_ids") int sport_ids, @Query("platform") String platform);

    @GET("bet/{ID}/question")
    Call<QuestionById> getQuestionById(@Path("ID") int ID, @Header("Authorization") String authorization, @Query("platform") String platform);

    @FormUrlEncoded
    @POST("bet/{ID}")
    Call<UserBet> userBet(@Path("ID") int ID, @Field("question_id") int question_id, @Field("answer_id") int answer_id, @Field("amount") int amount, @Header("Authorization") String authorization, @Query("platform") String platform);

    @FormUrlEncoded
    @POST("bet/{ID}/cancel")
    Call<UserBet> userCancelBet(@Path("ID") int ID, @Field("question_id") int question_id, @Header("Authorization") String authorization, @Query("platform") String platform);

    @GET("bet/getTopUserBet")
    Call<TopBet> getTopBet(@Query("sport_ids") int sport_ids, @Header("Authorization") String authorization, @Query("platform") String platform);

}
