package com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs;

 import java.util.List;
 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class SubsAdvanceSearchResponse {

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