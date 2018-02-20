package com.senzecit.iitiimshaadi.model.common.state;

/**
 * Created by senzec on 27/1/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class StateListResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allStates")
    @Expose
    private List<String> allStates = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<String> getAllStates() {
        return allStates;
    }

    public void setAllStates(List<String> allStates) {
        this.allStates = allStates;
    }

}