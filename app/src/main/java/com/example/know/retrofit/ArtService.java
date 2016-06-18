package com.example.know.retrofit;

import com.example.know.model.ArtCard;
import com.example.know.model.TwoCard;


import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by know on 2016/2/24.
 */
public interface ArtService {

    @Multipart
    @POST("saver.php")
    //Call<ResponseBody> postImage(@Part(value = "file", encoding = "8-bit")RequestBody image);
    Call<ResponseBody> postImage(@Part("file\"; filename=\"文件名.jpg")RequestBody image,
                                 @Part("userId") int userId ,@Part("selfId") int selfId);
    //Call<ResponseBody> postImage(@Part("file\";")RequestBody image);
    //Observable<TwoCard> getImooc(int num);

    @GET("geTwoCard.php")
    Observable<List<TwoCard>> getTwoCard(@Query("userId")int userId);

    @GET("geTwoCard.php")
    Observable<List<ArtCard>> getArts(@Query("userId")int userId,@Query(("selfId")) int selfId);

    @Multipart
    @POST("register.php")
    Observable<Result> Register(@Part("name")String name,@Part("password")String password);

}
