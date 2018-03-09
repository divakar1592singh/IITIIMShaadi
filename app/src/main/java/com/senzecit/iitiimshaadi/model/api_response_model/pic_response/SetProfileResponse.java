package com.senzecit.iitiimshaadi.model.api_response_model.pic_response;

/**
 * Created by senzec on 9/3/18.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class SetProfileResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("imageFullPath")
    @Expose
    private String imageFullPath;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getImageFullPath() {
        return imageFullPath;
    }

    public void setImageFullPath(String imageFullPath) {
        this.imageFullPath = imageFullPath;
    }

}