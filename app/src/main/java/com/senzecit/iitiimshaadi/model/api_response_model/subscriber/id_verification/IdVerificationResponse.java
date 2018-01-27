package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification;

/**
 * Created by senzec on 27/1/18.
 */

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class IdVerificationResponse {

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