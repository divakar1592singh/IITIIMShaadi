package com.senzecit.iitiimshaadi.model.api_response_model.other_profile;

/**
 * Created by senzec on 20/2/18.
 */

 import java.util.List;
 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class OtherProfileResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("basicData")
    @Expose
    private BasicData basicData;
    @SerializedName("partnerBasicData")
    @Expose
    private PartnerBasicData partnerBasicData;
    @SerializedName("loggedInUserRole")
    @Expose
    private LoggedInUserRole loggedInUserRole;
    @SerializedName("allAlbumPics")
    @Expose
    private List<AllAlbumPic> allAlbumPics = null;

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

    public LoggedInUserRole getLoggedInUserRole() {
        return loggedInUserRole;
    }

    public void setLoggedInUserRole(LoggedInUserRole loggedInUserRole) {
        this.loggedInUserRole = loggedInUserRole;
    }

    public List<AllAlbumPic> getAllAlbumPics() {
        return allAlbumPics;
    }

    public void setAllAlbumPics(List<AllAlbumPic> allAlbumPics) {
        this.allAlbumPics = allAlbumPics;
    }

}
