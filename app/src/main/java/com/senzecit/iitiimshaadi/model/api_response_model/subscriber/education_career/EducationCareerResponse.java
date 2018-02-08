package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.education_career;

/**
 * Created by senzec on 7/2/18.
 */

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class EducationCareerResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("educationCData")
    @Expose
    private EducationCData educationCData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public EducationCData getEducationCData() {
        return educationCData;
    }

    public void setEducationCData(EducationCData educationCData) {
        this.educationCData = educationCData;
    }

}
