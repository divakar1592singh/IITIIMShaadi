package com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream;

/**
 * Created by senzec on 1/1/18.
 */


        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class QuickRegStreamResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("college")
    @Expose
    private List<College> college = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<College> getCollege() {
        return college;
    }

    public void setCollege(List<College> college) {
        this.college = college;
    }

}