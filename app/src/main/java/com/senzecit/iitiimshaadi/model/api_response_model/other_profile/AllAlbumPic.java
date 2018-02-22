package com.senzecit.iitiimshaadi.model.api_response_model.other_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 22/2/18.
 */
public class AllAlbumPic {

    @SerializedName("pic_org_url")
    @Expose
    private String picOrgUrl;

    public String getPicOrgUrl() {
        return picOrgUrl;
    }

    public void setPicOrgUrl(String picOrgUrl) {
        this.picOrgUrl = picOrgUrl;
    }

}
