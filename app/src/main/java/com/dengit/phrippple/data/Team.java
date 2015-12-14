package com.dengit.phrippple.data;

import java.io.Serializable;

/**
 * Created by dengit on 15/12/14.
 */
public class Team implements Serializable {
    /**
     * id : 39
     * name : Dribbble
     * username : dribbble
     * html_url : https://dribbble.com/dribbble
     * avatar_url : https://d13yacurqjgara.cloudfront.net/users/39/avatars/normal/apple-flat-precomposed.png?1388527574
     * bio : Show and tell for designers. This is Dribbble on Dribbble.
     * location : Salem, MA
     * links : {"web":"http://dribbble.com","twitter":"https://twitter.com/dribbble"}
     * buckets_count : 1
     * comments_received_count : 2037
     * followers_count : 25011
     * followings_count : 6120
     * likes_count : 44
     * likes_received_count : 15811
     * members_count : 7
     * projects_count : 4
     * rebounds_received_count : 416
     * shots_count : 91
     * can_upload_shot : true
     * type : Team
     * pro : false
     * buckets_url : https://dribbble.com/v1/users/39/buckets
     * followers_url : https://dribbble.com/v1/users/39/followers
     * following_url : https://dribbble.com/v1/users/39/following
     * likes_url : https://dribbble.com/v1/users/39/likes
     * members_url : https://dribbble.com/v1/teams/39/members
     * shots_url : https://dribbble.com/v1/users/39/shots
     * team_shots_url : https://dribbble.com/v1/users/39/teams
     * created_at : 2009-08-18T18:34:31Z
     * updated_at : 2014-02-14T22:32:11Z
     */
    public int id;
    public String name;
    public String username;
    public String html_url;
    public String avatar_url;
    public String bio;
    public String location;
    /**
     * web : http://dribbble.com
     * twitter : https://twitter.com/dribbble
     */

    public LinksEntity links;
    public int buckets_count;
    public int comments_received_count;
    public int followers_count;
    public int followings_count;
    public int likes_count;
    public int likes_received_count;
    public int members_count;
    public int projects_count;
    public int rebounds_received_count;
    public int shots_count;
    public boolean can_upload_shot;
    public String type;
    public boolean pro;
    public String buckets_url;
    public String followers_url;
    public String following_url;
    public String likes_url;
    public String members_url;
    public String shots_url;
    public String team_shots_url;
    public String created_at;
    public String updated_at;

    public static class LinksEntity implements Serializable {
        public String web;
        public String twitter;
    }
}
