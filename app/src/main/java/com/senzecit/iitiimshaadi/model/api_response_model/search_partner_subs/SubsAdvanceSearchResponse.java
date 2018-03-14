package com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs;


  import java.util.List;
  import com.google.gson.annotations.Expose;
  import com.google.gson.annotations.SerializedName;

public class SubsAdvanceSearchResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}