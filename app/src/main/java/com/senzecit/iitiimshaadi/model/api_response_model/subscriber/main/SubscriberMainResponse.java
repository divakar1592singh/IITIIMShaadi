package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.main;

/**
 * Created by senzec on 12/2/18.
 */


 import java.util.List;
 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class SubscriberMainResponse {

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
    private List<Object> allInterestReceived = null;
    @SerializedName("chatUserCount")
    @Expose
    private Integer chatUserCount;
    @SerializedName("document_verified")
    @Expose
    private Integer documentVerified;
    @SerializedName("identity_proof_verified")
    @Expose
    private Integer identityProofVerified;
    @SerializedName("biodata_status")
    @Expose
    private Integer biodataStatus;
    @SerializedName("emailStatus")
    @Expose
    private String emailStatus;
    @SerializedName("mobileStatus")
    @Expose
    private Integer mobileStatus;

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

    public List<Object> getAllInterestReceived() {
        return allInterestReceived;
    }

    public void setAllInterestReceived(List<Object> allInterestReceived) {
        this.allInterestReceived = allInterestReceived;
    }

    public Integer getChatUserCount() {
        return chatUserCount;
    }

    public void setChatUserCount(Integer chatUserCount) {
        this.chatUserCount = chatUserCount;
    }

    public Integer getDocumentVerified() {
        return documentVerified;
    }

    public void setDocumentVerified(Integer documentVerified) {
        this.documentVerified = documentVerified;
    }

    public Integer getIdentityProofVerified() {
        return identityProofVerified;
    }

    public void setIdentityProofVerified(Integer identityProofVerified) {
        this.identityProofVerified = identityProofVerified;
    }

    public Integer getBiodataStatus() {
        return biodataStatus;
    }

    public void setBiodataStatus(Integer biodataStatus) {
        this.biodataStatus = biodataStatus;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public Integer getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(Integer mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

}