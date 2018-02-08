package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.familty_detail;

/**
 * Created by senzec on 7/2/18.
 */


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class FamilyDetailResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("familyData")
    @Expose
    private FamilyData familyData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public FamilyData getFamilyData() {
        return familyData;
    }

    public void setFamilyData(FamilyData familyData) {
        this.familyData = familyData;
    }

}
