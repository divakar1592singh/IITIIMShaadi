package com.senzecit.iitiimshaadi.model.customFolder.customFolderModel;

 import java.util.List;
 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class FolderListModelResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("myMetas")
    @Expose
    private List<MyMeta> myMetas = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<MyMeta> getMyMetas() {
        return myMetas;
    }

    public void setMyMetas(List<MyMeta> myMetas) {
        this.myMetas = myMetas;
    }

}