package com.senzecit.iitiimshaadi.model.api_response_model.friends.requested_friend;


/**
 * Created by senzec on 12/2/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class RequestedFriendResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allRequestFriend")
    @Expose
    private List<AllRequestFriend> allRequestFriend = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<AllRequestFriend> getAllRequestFriend() {
        return allRequestFriend;
    }

    public void setAllRequestFriend(List<AllRequestFriend> allRequestFriend) {
        this.allRequestFriend = allRequestFriend;
    }

}
