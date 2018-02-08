package com.senzecit.iitiimshaadi.model.api_response_model.general_setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 7/2/18.
 */
public class AllSettings {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_nickname")
    @Expose
    private String userNickname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_sent_time")
    @Expose
    private String emailSentTime;
    @SerializedName("document_verified")
    @Expose
    private Integer documentVerified;
    @SerializedName("document_name")
    @Expose
    private String documentName;
    @SerializedName("user_url")
    @Expose
    private String userUrl;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("facebook_profileid")
    @Expose
    private String facebookProfileid;
    @SerializedName("facebook_status")
    @Expose
    private Integer facebookStatus;
    @SerializedName("google_profileid")
    @Expose
    private String googleProfileid;
    @SerializedName("google_status")
    @Expose
    private Integer googleStatus;
    @SerializedName("user_activation_key")
    @Expose
    private String userActivationKey;
    @SerializedName("account_status")
    @Expose
    private Integer accountStatus;
    @SerializedName("send_message")
    @Expose
    private Integer sendMessage;
    @SerializedName("send_request")
    @Expose
    private Integer sendRequest;
    @SerializedName("shortlisted")
    @Expose
    private Integer shortlisted;
    @SerializedName("favourate")
    @Expose
    private Integer favourate;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("profile_email_sent")
    @Expose
    private Integer profileEmailSent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailSentTime() {
        return emailSentTime;
    }

    public void setEmailSentTime(String emailSentTime) {
        this.emailSentTime = emailSentTime;
    }

    public Integer getDocumentVerified() {
        return documentVerified;
    }

    public void setDocumentVerified(Integer documentVerified) {
        this.documentVerified = documentVerified;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFacebookProfileid() {
        return facebookProfileid;
    }

    public void setFacebookProfileid(String facebookProfileid) {
        this.facebookProfileid = facebookProfileid;
    }

    public Integer getFacebookStatus() {
        return facebookStatus;
    }

    public void setFacebookStatus(Integer facebookStatus) {
        this.facebookStatus = facebookStatus;
    }

    public String getGoogleProfileid() {
        return googleProfileid;
    }

    public void setGoogleProfileid(String googleProfileid) {
        this.googleProfileid = googleProfileid;
    }

    public Integer getGoogleStatus() {
        return googleStatus;
    }

    public void setGoogleStatus(Integer googleStatus) {
        this.googleStatus = googleStatus;
    }

    public String getUserActivationKey() {
        return userActivationKey;
    }

    public void setUserActivationKey(String userActivationKey) {
        this.userActivationKey = userActivationKey;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(Integer sendMessage) {
        this.sendMessage = sendMessage;
    }

    public Integer getSendRequest() {
        return sendRequest;
    }

    public void setSendRequest(Integer sendRequest) {
        this.sendRequest = sendRequest;
    }

    public Integer getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(Integer shortlisted) {
        this.shortlisted = shortlisted;
    }

    public Integer getFavourate() {
        return favourate;
    }

    public void setFavourate(Integer favourate) {
        this.favourate = favourate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getProfileEmailSent() {
        return profileEmailSent;
    }

    public void setProfileEmailSent(Integer profileEmailSent) {
        this.profileEmailSent = profileEmailSent;
    }

}
