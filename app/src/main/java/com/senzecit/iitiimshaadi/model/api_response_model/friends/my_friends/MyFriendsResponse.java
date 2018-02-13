package com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends;

/**
 * Created by senzec on 12/2/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class MyFriendsResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allFriend")
    @Expose
    private List<AllFriend> allFriend = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<AllFriend> getAllFriend() {
        return allFriend;
    }

    public void setAllFriend(List<AllFriend> allFriend) {
        this.allFriend = allFriend;
    }

}
