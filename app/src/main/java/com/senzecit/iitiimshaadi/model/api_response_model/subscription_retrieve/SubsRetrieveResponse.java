package com.senzecit.iitiimshaadi.model.api_response_model.subscription_retrieve;

/**
 * Created by senzec on 7/2/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SubsRetrieveResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allSubscriptions")
    @Expose
    private List<AllSubscription> allSubscriptions = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<AllSubscription> getAllSubscriptions() {
        return allSubscriptions;
    }

    public void setAllSubscriptions(List<AllSubscription> allSubscriptions) {
        this.allSubscriptions = allSubscriptions;
    }

}