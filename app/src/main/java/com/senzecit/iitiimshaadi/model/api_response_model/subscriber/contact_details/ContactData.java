package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.contact_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 7/2/18.
 */
public class ContactData {

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("alternate_no")
    @Expose
    private String alternateNo;
    @SerializedName("permanent_address")
    @Expose
    private String permanentAddress;
    @SerializedName("permanent_country")
    @Expose
    private String permanentCountry;
    @SerializedName("permanent_state")
    @Expose
    private String permanentState;
    @SerializedName("permanent_city")
    @Expose
    private String permanentCity;
    @SerializedName("permanent_zipcode")
    @Expose
    private String permanentZipcode;
    @SerializedName("current_state")
    @Expose
    private String currentState;
    @SerializedName("current_address")
    @Expose
    private String currentAddress;
    @SerializedName("current_city")
    @Expose
    private String currentCity;
    @SerializedName("current_country")
    @Expose
    private String currentCountry;
    @SerializedName("current_zipcode")
    @Expose
    private String currentZipcode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternateNo() {
        return alternateNo;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }

    public String getPermanentState() {
        return permanentState;
    }

    public void setPermanentState(String permanentState) {
        this.permanentState = permanentState;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getPermanentZipcode() {
        return permanentZipcode;
    }

    public void setPermanentZipcode(String permanentZipcode) {
        this.permanentZipcode = permanentZipcode;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getCurrentZipcode() {
        return currentZipcode;
    }

    public void setCurrentZipcode(String currentZipcode) {
        this.currentZipcode = currentZipcode;
    }

}
