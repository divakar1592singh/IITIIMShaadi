package com.senzecit.iitiimshaadi.model.api_response_model.socket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 23/3/18.
 */
public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("from_user")
    @Expose
    private String fromUser;
    @SerializedName("to_user")
    @Expose
    private String toUser;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sent")
    @Expose
    private String sent;
    @SerializedName("recd")
    @Expose
    private Integer recd;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public Integer getRecd() {
        return recd;
    }

    public void setRecd(Integer recd) {
        this.recd = recd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
