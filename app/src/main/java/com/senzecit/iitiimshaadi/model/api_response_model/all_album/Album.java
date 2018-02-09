package com.senzecit.iitiimshaadi.model.api_response_model.all_album;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 9/2/18.
 */
public class Album {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner_type")
    @Expose
    private String ownerType;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("date_uploaded")
    @Expose
    private String dateUploaded;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("privacy")
    @Expose
    private Integer privacy;
    @SerializedName("pic_org_url")
    @Expose
    private String picOrgUrl;
    @SerializedName("pic_org_path")
    @Expose
    private String picOrgPath;
    @SerializedName("pic_mid_url")
    @Expose
    private String picMidUrl;
    @SerializedName("pic_mid_path")
    @Expose
    private String picMidPath;
    @SerializedName("pic_thumb_url")
    @Expose
    private String picThumbUrl;
    @SerializedName("pic_thumb_path")
    @Expose
    private String picThumbPath;
    @SerializedName("import_status")
    @Expose
    private Integer importStatus;
    @SerializedName("old_activity_id")
    @Expose
    private Integer oldActivityId;
    @SerializedName("new_activity_id")
    @Expose
    private Integer newActivityId;
    @SerializedName("favorites")
    @Expose
    private Boolean favorites;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Integer privacy) {
        this.privacy = privacy;
    }

    public String getPicOrgUrl() {
        return picOrgUrl;
    }

    public void setPicOrgUrl(String picOrgUrl) {
        this.picOrgUrl = picOrgUrl;
    }

    public String getPicOrgPath() {
        return picOrgPath;
    }

    public void setPicOrgPath(String picOrgPath) {
        this.picOrgPath = picOrgPath;
    }

    public String getPicMidUrl() {
        return picMidUrl;
    }

    public void setPicMidUrl(String picMidUrl) {
        this.picMidUrl = picMidUrl;
    }

    public String getPicMidPath() {
        return picMidPath;
    }

    public void setPicMidPath(String picMidPath) {
        this.picMidPath = picMidPath;
    }

    public String getPicThumbUrl() {
        return picThumbUrl;
    }

    public void setPicThumbUrl(String picThumbUrl) {
        this.picThumbUrl = picThumbUrl;
    }

    public String getPicThumbPath() {
        return picThumbPath;
    }

    public void setPicThumbPath(String picThumbPath) {
        this.picThumbPath = picThumbPath;
    }

    public Integer getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(Integer importStatus) {
        this.importStatus = importStatus;
    }

    public Integer getOldActivityId() {
        return oldActivityId;
    }

    public void setOldActivityId(Integer oldActivityId) {
        this.oldActivityId = oldActivityId;
    }

    public Integer getNewActivityId() {
        return newActivityId;
    }

    public void setNewActivityId(Integer newActivityId) {
        this.newActivityId = newActivityId;
    }

    public Boolean getFavorites() {
        return favorites;
    }

    public void setFavorites(Boolean favorites) {
        this.favorites = favorites;
    }

}
