package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.religious_background;

/**
 * Created by senzec on 7/2/18.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class ReligiousBackgroundResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("religionData")
    @Expose
    private ReligionData religionData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ReligionData getReligionData() {
        return religionData;
    }

    public void setReligionData(ReligionData religionData) {
        this.religionData = religionData;
    }

}