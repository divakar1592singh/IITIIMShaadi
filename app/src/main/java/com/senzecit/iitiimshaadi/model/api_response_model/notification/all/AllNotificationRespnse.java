package com.senzecit.iitiimshaadi.model.api_response_model.notification.all;

/**
 * Created by senzec on 22/2/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class AllNotificationRespnse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("getAllNotificaitons")
    @Expose
    private List<GetAllNotificaiton> getAllNotificaitons = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<GetAllNotificaiton> getGetAllNotificaitons() {
        return getAllNotificaitons;
    }

    public void setGetAllNotificaitons(List<GetAllNotificaiton> getAllNotificaitons) {
        this.getAllNotificaitons = getAllNotificaitons;
    }

}
