package com.senzecit.iitiimshaadi.model.api_response_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 30/3/18.
 */
public class Message {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
