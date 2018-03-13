package com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 13/3/18.
 */
public class Query {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

}
