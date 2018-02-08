package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.ptr_religious_country;

/**
 * Created by senzec on 7/2/18.
 */


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class PtrReligionCountryResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("partnerReligionData")
    @Expose
    private PartnerReligionData partnerReligionData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public PartnerReligionData getPartnerReligionData() {
        return partnerReligionData;
    }

    public void setPartnerReligionData(PartnerReligionData partnerReligionData) {
        this.partnerReligionData = partnerReligionData;
    }

}