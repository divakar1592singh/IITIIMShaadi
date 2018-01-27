package com.senzecit.iitiimshaadi.model.api_response_model.quick_register.find_college;

/**
 * Created by senzec on 30/12/17.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class FindCollegeResponse {

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
