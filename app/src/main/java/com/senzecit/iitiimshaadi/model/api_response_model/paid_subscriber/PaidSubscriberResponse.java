package com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber;

/**
 * Created by senzec on 16/2/18.
 */


        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class PaidSubscriberResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("query")
    @Expose
    private List<Query> query = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }

}
