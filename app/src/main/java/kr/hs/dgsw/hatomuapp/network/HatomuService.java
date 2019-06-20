package kr.hs.dgsw.hatomuapp.network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.beans.ResponseBean;
import kr.hs.dgsw.hatomuapp.beans.UserBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HatomuService {

    public static final String BASE_URL = "http://hatomu.kro.kr:3000/";

    @GET("/post/load/0")
    Single<ResponseBean<ArrayList<PostBean>>> getLatestPosts(@Header("authorization") String token);

    @GET("/post/load/{lastIndex}/{id}")
    Call<ResponseBean<ArrayList<PostBean>>> getPostFromLastIndex(@Path("lastIndex") int lastIndex, @Path("id") String id);

    @PUT("/post/like/{postId}")
    Single<ResponseBean<Boolean>> likePost(@Path("postId") String postId, @Header("authorization") String token);

    @Multipart
    @POST("/post/upload")
    Single<ResponseBean<PostBean>> uploadPost(@Part List<MultipartBody.Part> postImage, @Part("content") RequestBody content, @Header("authorization") String token);

    @GET("/user/search")
    Single<ResponseBean<List<UserBean>>> searchUser(@Query("q") String q);

    @GET("/post/getTopIndex")
    Call<ResponseBean<Integer>> getTopIndex();

    @GET("/user/getInfoById/{userId}")
    Single<ResponseBean<UserBean>> getUserInfoById(@Path("userId") String userId);

    @GET("/post/loadOne/{postId}")
    Single<ResponseBean<PostBean>> getPost(@Path("postId") String postId, @Header("authorization") String token);
}
