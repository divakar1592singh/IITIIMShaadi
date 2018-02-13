package com.senzecit.iitiimshaadi.model.api_response_model.friends.shortlisted;

/**
 * Created by senzec on 12/2/18.
 */


        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ShortlistedFriendResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allShortlistedFriend")
    @Expose
    private List<AllShortlistedFriend> allShortlistedFriend = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<AllShortlistedFriend> getAllShortlistedFriend() {
        return allShortlistedFriend;
    }

    public void setAllShortlistedFriend(List<AllShortlistedFriend> allShortlistedFriend) {
        this.allShortlistedFriend = allShortlistedFriend;
    }

}
