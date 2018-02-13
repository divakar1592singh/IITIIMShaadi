package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.groom;

/**
 * Created by senzec on 13/2/18.
 */

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ChoiceOfGroomResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("partnerChoiceData")
    @Expose
    private PartnerChoiceData partnerChoiceData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public PartnerChoiceData getPartnerChoiceData() {
        return partnerChoiceData;
    }

    public void setPartnerChoiceData(PartnerChoiceData partnerChoiceData) {
        this.partnerChoiceData = partnerChoiceData;
    }

}