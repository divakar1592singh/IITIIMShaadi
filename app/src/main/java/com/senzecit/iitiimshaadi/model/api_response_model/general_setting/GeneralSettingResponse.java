package com.senzecit.iitiimshaadi.model.api_response_model.general_setting;

/**
 * Created by senzec on 7/2/18.
 */


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class GeneralSettingResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allSettings")
    @Expose
    private AllSettings allSettings;
    @SerializedName("name")
    @Expose
    private String name;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public AllSettings getAllSettings() {
        return allSettings;
    }

    public void setAllSettings(AllSettings allSettings) {
        this.allSettings = allSettings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}