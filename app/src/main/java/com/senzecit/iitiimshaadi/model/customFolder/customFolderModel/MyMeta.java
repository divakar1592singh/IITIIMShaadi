package com.senzecit.iitiimshaadi.model.customFolder.customFolderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 31/1/18.
 */
public class MyMeta {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("meta_key")
    @Expose
    private String metaKey;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("meta_value")
    @Expose
    private String metaValue;
    @SerializedName("friends_ids")
    @Expose
    private String friendsIds;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("userDetails")
    @Expose
    private List<UserDetail> userDetails = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public String getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(String friendsIds) {
        this.friendsIds = friendsIds;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<UserDetail> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserDetail> userDetails) {
        this.userDetails = userDetails;
    }

}
