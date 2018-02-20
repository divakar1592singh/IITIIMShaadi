package com.senzecit.iitiimshaadi.model.common.caste;

/**
 * Created by senzec on 20/2/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class CasteAccReligionResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allCastes")
    @Expose
    private List<String> allCastes = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<String> getAllCastes() {
        return allCastes;
    }

    public void setAllCastes(List<String> allCastes) {
        this.allCastes = allCastes;
    }

}
