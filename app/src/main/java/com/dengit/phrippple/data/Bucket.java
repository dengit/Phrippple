package com.dengit.phrippple.data;

import java.io.Serializable;

/**
 * Created by dengit on 15/12/15.
 */
public class Bucket implements Serializable {

    /**
     * id : 326802
     * name : illustrations
     * description : null
     * shots_count : 1
     * created_at : 2015-11-12T08:20:12Z
     * updated_at : 2015-11-12T08:20:14Z
     */

    public int id;
    public String name;
    public String description;
    public int shots_count;
    public String created_at;
    public String updated_at;
    public User user;
}
