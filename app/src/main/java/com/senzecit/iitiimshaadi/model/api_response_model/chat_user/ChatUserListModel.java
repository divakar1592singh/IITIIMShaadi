package com.senzecit.iitiimshaadi.model.api_response_model.chat_user;

/**
 * Created by senzec on 23/3/18.
 */


 import java.util.List;
 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class ChatUserListModel {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
