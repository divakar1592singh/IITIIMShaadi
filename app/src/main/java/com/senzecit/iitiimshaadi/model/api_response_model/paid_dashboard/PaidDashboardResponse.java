package com.senzecit.iitiimshaadi.model.api_response_model.paid_dashboard;

/**
 * Created by senzec on 21/2/18.
 */


 import java.util.List;
 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class PaidDashboardResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("basicData")
    @Expose
    private BasicData basicData;
    @SerializedName("partnerBasicData")
    @Expose
    private PartnerBasicData partnerBasicData;
    @SerializedName("allInterestReceived")
    @Expose
    private List<AllInterestReceived> allInterestReceived = null;

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

    public List<AllInterestReceived> getAllInterestReceived() {
        return allInterestReceived;
    }

    public void setAllInterestReceived(List<AllInterestReceived> allInterestReceived) {
        this.allInterestReceived = allInterestReceived;
    }

}
