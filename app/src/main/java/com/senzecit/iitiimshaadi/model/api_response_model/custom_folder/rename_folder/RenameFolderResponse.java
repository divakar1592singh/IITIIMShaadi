package com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.rename_folder;

/**
 * Created by senzec on 9/2/18.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class RenameFolderResponse {

    @SerializedName("message")
    @Expose
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}