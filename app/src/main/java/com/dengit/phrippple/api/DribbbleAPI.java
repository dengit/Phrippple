package com.dengit.phrippple.api;

import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.data.LikeShot;
import com.dengit.phrippple.data.LikeShotResponse;
import com.dengit.phrippple.data.RequestTokenBody;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.data.User;

import java.util.List;

import retrofit.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;
import rx.Observable;

/**
 * Created by dengit on 15/12/7.
 */
public interface DribbbleAPI {
    String DEFAULT_HEADER_IMAGE = "https://d13yacurqjgara.cloudfront.net/users/995516/avatars/normal/195f0357fb7fca71c46f4d3e1a733a5f.jpg?1447156390";
    String API_BASE_URL = "https://api.dribbble.com/v1/";
    String CLIENT_ID = "c5c94fd5886dbfddacd4bc3e62cd14b3456ebe17d8de208b27ce91224d951aec";
    String CLIENT_SECRET = "f6dba6bd84efd90a1fe29728e06f83d0bb8ad7841bd7ef9333f7271bc7f3d863";
    String OAUTH_SCOPE = "write%20comment%20upload";
    String AUTHORIZE_URL = String.format(
            "https://dribbble.com/oauth/authorize?client_id=%s&scope=%s",
            CLIENT_ID, OAUTH_SCOPE);
    String TOKEN_URL = "https://dribbble.com/oauth/token";
    String CALLBACK_SCHEME = "phrippple";
    String CALLBACK_HOST = "phone-callback";
    int LIMIT_PER_PAGE = 30;

    @GET("shots")
    Observable<List<Shot>>
    getShots(@Query("sort") String sort,
             @Query("list") String list,
             @Query("timeframe") String timeFrame,
             @Query("page") int page,
             @Query("per_page") int perPage,
             @Query("access_token") String accessToken);

    @GET("users/{userId}/likes")
    Observable<List<LikeShot>>
    getLikeShots(@Path("userId")int userId,
                 @Query("page") int page,
                 @Query("per_page") int perPage,
                 @Query("access_token") String accessToken);

    @POST(TOKEN_URL)
    Observable<TokenInfo>
    getToken(@Body RequestTokenBody body);

    @GET("shots/{shotId}/comments")
    Observable<List<Comment>>
    getComments(@Path("shotId") int shotId,
                @Query("page") int page,
                @Query("per_page") int perPage,
                @Query("access_token") String accessToken);

    @GET("shots/{shotId}/likes")
    Observable<List<Fan>>
    getFans(@Path("shotId") int shotId,
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("access_token") String accessToken);

    @GET("users/{userId}/buckets")
    Observable<List<Bucket>>
    getMineBuckets(@Path("userId")int userId,
                   @Query("page") int page,
                   @Query("per_page") int perPage,
                   @Query("access_token") String accessToken);

    @GET("shots/{shotId}/buckets")
    Observable<List<Bucket>>
    getOthersBuckets(@Path("shotId")int shotId,
                     @Query("page") int page,
                     @Query("per_page") int perPage,
                     @Query("access_token") String accessToken);

    @GET("shots/{shotId}/like")
    Observable<LikeShotResponse>
    checkLikeShot(@Path("shotId") int shotId,
                  @Query("access_token") String accessToken);

    @POST("shots/{shotId}/like")
    Observable<LikeShotResponse>
    likeShot(@Path("shotId") int shotId,
             @Query("access_token") String accessToken);

    @DELETE("shots/{shotId}/like")
    Observable<Void>
    unlikeShot(@Path("shotId") int shotId,
               @Query("access_token") String accessToken);

    @GET("buckets/{bucketId}/shots")
    Observable<List<Shot>>
    getBucketShots(@Path("bucketId")int bucketId,
                   @Query("page") int page,
                   @Query("per_page") int perPage,
                   @Query("access_token") String accessToken);

    @GET("users/{userId}/shots")
    Observable<List<Shot>>
    getSelfShots(@Path("userId")int userId,
                 @Query("page") int page,
                 @Query("per_page") int perPage,
                 @Query("access_token") String accessToken);

    @GET("user")
    Observable<User>
    getUserInfo(@Query("access_token") String accessToken);

    @GET("user/following/shots")
    Observable<List<Shot>>
    getUserFollowingShots(@Query("page") int page,
                          @Query("per_page") int perPage,
                          @Query("access_token") String accessToken);


}
