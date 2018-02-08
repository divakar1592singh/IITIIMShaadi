package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_education;

/**
 * Created by senzec on 7/2/18.
 */

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class PtrEduCareerResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("partnerEducationCData")
    @Expose
    private PartnerEducationCData partnerEducationCData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public PartnerEducationCData getPartnerEducationCData() {
        return partnerEducationCData;
    }

    public void setPartnerEducationCData(PartnerEducationCData partnerEducationCData) {
        this.partnerEducationCData = partnerEducationCData;
    }

}
