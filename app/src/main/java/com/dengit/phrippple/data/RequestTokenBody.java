package com.dengit.phrippple.data;

import com.dengit.phrippple.api.DribbbleAPI;

/**
 * Created by dengit on 15/12/7.
 */
public class RequestTokenBody {
    String client_id;
    String client_secret;
    String code;

    public RequestTokenBody(String code) {
        this.client_id = DribbbleAPI.CLIENT_ID;
        this.client_secret = DribbbleAPI.CLIENT_SECRET;
        this.code = code;
    }
}
