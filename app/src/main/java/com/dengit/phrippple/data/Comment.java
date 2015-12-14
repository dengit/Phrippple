package com.dengit.phrippple.data;

import java.io.Serializable;

/**
 * Created by dengit on 15/12/14.
 */
public class Comment implements Serializable{

    /**
     * id : 4879997
     * body : <p>looks purdy </p>
     * likes_count : 2
     * likes_url : https://api.dribbble.com/v1/shots/2397328/comments/4879997/likes
     * created_at : 2015-12-09T19:00:17Z
     * updated_at : 2015-12-09T19:00:17Z
     */

    public int id;
    public String body;
    public int likes_count;
    public String likes_url;
    public String created_at;
    public String updated_at;
    public User user;
}
