package com.senzecit.iitiimshaadi.model.api_response_model.new_register;

/**
 * Created by senzec on 30/12/17.
 */

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class NewRegistrationResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

}
