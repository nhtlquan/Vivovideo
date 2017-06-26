package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.CategoryById.CategoryByID;
import com.smile.studio.network.ver2.model.SeriesCategory.SeriesCategory;
import com.smile.studio.network.ver2.model.SeriesCategory.SeriesCategoryItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_CategoryID {
    @GET("{type}")
    Call<CategoryByID> getCategoryById( @Path("type") String type,@Query("page") int page, @Query("pageSize") int pageSize, @Query("platform") String platform, @Query("category_ids[]") int category_ids, @Header("Authorization") String authorization);

    @GET("category/getCategoryById/{ID}")
    Call<SeriesCategory> getListCategory(@Path("ID") int id, @Query("platform") String platform, @Header("Authorization") String authorization);
}