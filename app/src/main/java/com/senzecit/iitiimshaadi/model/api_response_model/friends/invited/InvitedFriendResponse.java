package com.senzecit.iitiimshaadi.model.api_response_model.friends.invited;

/**
 * Created by senzec on 12/2/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class InvitedFriendResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allInvitedFriend")
    @Expose
    private List<AllInvitedFriend> allInvitedFriend = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<AllInvitedFriend> getAllInvitedFriend() {
        return allInvitedFriend;
    }

    public void setAllInvitedFriend(List<AllInvitedFriend> allInvitedFriend) {
        this.allInvitedFriend = allInvitedFriend;
    }

}