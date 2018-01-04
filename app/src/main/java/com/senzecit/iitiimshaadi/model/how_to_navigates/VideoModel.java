package com.senzecit.iitiimshaadi.model.how_to_navigates;

/**
 * Created by senzec on 7/12/17.
 */

public class VideoModel {


    public VideoModel(String title, String video_thumb) {
        this.title = title;
        this.video_thumb = video_thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    String title;
    String video_thumb;
}
