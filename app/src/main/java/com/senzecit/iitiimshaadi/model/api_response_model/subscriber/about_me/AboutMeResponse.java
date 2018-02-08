package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.about_me;

/**
 * Created by senzec on 7/2/18.
 */


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class AboutMeResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("aboutData")
    @Expose
    private AboutData aboutData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public AboutData getAboutData() {
        return aboutData;
    }

    public void setAboutData(AboutData aboutData) {
        this.aboutData = aboutData;
    }

}
