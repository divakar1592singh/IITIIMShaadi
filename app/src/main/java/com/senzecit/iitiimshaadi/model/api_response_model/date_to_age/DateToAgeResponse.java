package com.senzecit.iitiimshaadi.model.api_response_model.date_to_age;

/**
 * Created by senzec on 21/2/18.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class DateToAgeResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("age")
    @Expose
    private String age;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
