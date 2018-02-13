package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_basic_profile;

/**
 * Created by senzec on 7/2/18.
 */


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ParnerBasicProfileResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("partnerBasicData")
    @Expose
    private PartnerBasicData partnerBasicData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public PartnerBasicData getPartnerBasicData() {
        return partnerBasicData;
    }

    public void setPartnerBasicData(PartnerBasicData partnerBasicData) {
        this.partnerBasicData = partnerBasicData;
    }

}
