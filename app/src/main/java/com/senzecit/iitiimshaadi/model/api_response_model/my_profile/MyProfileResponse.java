package com.senzecit.iitiimshaadi.model.api_response_model.my_profile;

/**
 * Created by senzec on 27/1/18.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class MyProfileResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("basicData")
    @Expose
    private BasicData basicData;
    @SerializedName("partnerBasicData")
    @Expose
    private PartnerBasicData partnerBasicData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public BasicData getBasicData() {
        return basicData;
    }

    public void setBasicData(BasicData basicData) {
        this.basicData = basicData;
    }

    public PartnerBasicData getPartnerBasicData() {
        return partnerBasicData;
    }

    public void setPartnerBasicData(PartnerBasicData partnerBasicData) {
        this.partnerBasicData = partnerBasicData;
    }

}
