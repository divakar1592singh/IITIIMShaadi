package com.senzecit.iitiimshaadi.model.api_response_model.all_album;

/**
 * Created by senzec on 9/2/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class AllAlbumResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("albums")
    @Expose
    private List<Album> albums = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

}
