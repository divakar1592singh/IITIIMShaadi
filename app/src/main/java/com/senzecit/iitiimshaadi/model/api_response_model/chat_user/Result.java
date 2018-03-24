package com.senzecit.iitiimshaadi.model.api_response_model.chat_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 23/3/18.
 */
public class Result {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
