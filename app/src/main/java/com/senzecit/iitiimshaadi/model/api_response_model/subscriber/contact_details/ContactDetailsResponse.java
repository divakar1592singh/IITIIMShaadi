package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.contact_details;

/**
 * Created by senzec on 7/2/18.
 */

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class ContactDetailsResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("contactData")
    @Expose
    private ContactData contactData;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

}
