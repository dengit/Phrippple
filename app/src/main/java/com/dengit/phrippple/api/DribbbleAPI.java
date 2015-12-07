package com.dengit.phrippple.api;

import com.dengit.phrippple.model.GithubRepo;
import com.dengit.phrippple.model.GithubUser;
import com.dengit.phrippple.model.RequestTokenBody;
import com.dengit.phrippple.model.Shot;
import com.dengit.phrippple.model.TokenInfo;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by dengit on 15/12/7.
 */
public interface DribbbleAPI {

    String API_BASE_URL = "https://api.dribbble.com";
    String CLIENT_ID = "c5c94fd5886dbfddacd4bc3e62cd14b3456ebe17d8de208b27ce91224d951aec";
    String CLIENT_SECRET = "f6dba6bd84efd90a1fe29728e06f83d0bb8ad7841bd7ef9333f7271bc7f3d863";
    String AUTHORIZE_URL = "https://dribbble.com/oauth/authorize?client_id=%s";
    String TOKEN_URL = "https://dribbble.com/oauth/token";

//    String API_BASE_URL = "https://api.github.com";

//    https://api.github.com/users/dengit/repos?type=owner

    @GET("/users/{username}/repos")
    Observable<List<GithubRepo>> getRepos(@Path("username") String username, @Query("type") String type);

    @GET("/users/{username}/repos")
    Call<List<GithubRepo>> getReposNormal(@Path("username") String username, @Query("type") String type);

    @GET("/users/{username}")
    Observable<GithubUser> getUser(@Path("username") String username);

    @GET("/users/{username}")
    Call<GithubUser> getUserNormal(@Path("username") String username);

    @GET("/v1/shots")
    Observable<List<Shot>> getShots(@Query("access_token") String accessToken);

    @GET("/v1/shots")
    Call<List<Shot>> getShotsNormal(@Query("access_token") String accessToken);

    @POST(TOKEN_URL)
    Observable<TokenInfo> getToken(@Body RequestTokenBody body);
}
