package com.senzecit.iitiimshaadi.model.api_response_model.paid_dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 21/2/18.
 */import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartnerBasicData {

    @SerializedName("prefered_partner_education")
    @Expose
    private List<String> preferedPartnerEducation = null;
    @SerializedName("prefered_partner_religion")
    @Expose
    private String preferedPartnerReligion;
    @SerializedName("prefered_partner_caste")
    @Expose
    private String preferedPartnerCaste;
    @SerializedName("prefered_partner_country")
    @Expose
    private Object preferedPartnerCountry;
    @SerializedName("prefered_partner_min_age")
    @Expose
    private Integer preferedPartnerMinAge;
    @SerializedName("prefered_partner_max_age")
    @Expose
    private Integer preferedPartnerMaxAge;
    @SerializedName("prefered_partner_height_max")
    @Expose
    private String preferedPartnerHeightMax;
    @SerializedName("prefered_partner_height_min")
    @Expose
    private String preferedPartnerHeightMin;
    @SerializedName("prefered_partner_marital_status")
    @Expose
    private List<String> preferedPartnerMaritalStatus = null;
    @SerializedName("choice_of_partner")
    @Expose
    private String choiceOfPartner;

    public List<String> getPreferedPartnerEducation() {
        return preferedPartnerEducation;
    }

    public void setPreferedPartnerEducation(List<String> preferedPartnerEducation) {
        this.preferedPartnerEducation = preferedPartnerEducation;
    }

    public String getPreferedPartnerReligion() {
        return preferedPartnerReligion;
    }

    public void setPreferedPartnerReligion(String preferedPartnerReligion) {
        this.preferedPartnerReligion = preferedPartnerReligion;
    }

    public String getPreferedPartnerCaste() {
        return preferedPartnerCaste;
    }

    public void setPreferedPartnerCaste(String preferedPartnerCaste) {
        this.preferedPartnerCaste = preferedPartnerCaste;
    }

    public Object getPreferedPartnerCountry() {
        return preferedPartnerCountry;
    }

    public void setPreferedPartnerCountry(Object preferedPartnerCountry) {
        this.preferedPartnerCountry = preferedPartnerCountry;
    }

    public Integer getPreferedPartnerMinAge() {
        return preferedPartnerMinAge;
    }

    public void setPreferedPartnerMinAge(Integer preferedPartnerMinAge) {
        this.preferedPartnerMinAge = preferedPartnerMinAge;
    }

    public Integer getPreferedPartnerMaxAge() {
        return preferedPartnerMaxAge;
    }

    public void setPreferedPartnerMaxAge(Integer preferedPartnerMaxAge) {
        this.preferedPartnerMaxAge = preferedPartnerMaxAge;
    }

    public String getPreferedPartnerHeightMax() {
        return preferedPartnerHeightMax;
    }

    public void setPreferedPartnerHeightMax(String preferedPartnerHeightMax) {
        this.preferedPartnerHeightMax = preferedPartnerHeightMax;
    }

    public String getPreferedPartnerHeightMin() {
        return preferedPartnerHeightMin;
    }

    public void setPreferedPartnerHeightMin(String preferedPartnerHeightMin) {
        this.preferedPartnerHeightMin = preferedPartnerHeightMin;
    }

    public List<String> getPreferedPartnerMaritalStatus() {
        return preferedPartnerMaritalStatus;
    }

    public void setPreferedPartnerMaritalStatus(List<String> preferedPartnerMaritalStatus) {
        this.preferedPartnerMaritalStatus = preferedPartnerMaritalStatus;
    }

    public String getChoiceOfPartner() {
        return choiceOfPartner;
    }

    public void setChoiceOfPartner(String choiceOfPartner) {
        this.choiceOfPartner = choiceOfPartner;
    }

}