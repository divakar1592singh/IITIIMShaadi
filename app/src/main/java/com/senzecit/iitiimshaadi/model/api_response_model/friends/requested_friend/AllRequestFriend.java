package com.senzecit.iitiimshaadi.model.api_response_model.friends.requested_friend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 13/2/18.
 */
public class AllRequestFriend {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

}
