package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 21/2/18.
 */
public class PartnerBasicData {

    @SerializedName("prefered_partner_education")
    @Expose
    private String preferedPartnerEducation;
    @SerializedName("prefered_partner_caste")
    @Expose
    private String preferedPartnerCaste;
    @SerializedName("prefered_partner_country")
    @Expose
    private String preferedPartnerCountry;
    @SerializedName("prefered_partner_religion")
    @Expose
    private String preferedPartnerReligion;
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
    @SerializedName("choice_of_groom")
    @Expose
    private String choiceOfGroom;

    public String getPreferedPartnerEducation() {
        return preferedPartnerEducation;
    }

    public void setPreferedPartnerEducation(String preferedPartnerEducation) {
        this.preferedPartnerEducation = preferedPartnerEducation;
    }

    public String getPreferedPartnerCaste() {
        return preferedPartnerCaste;
    }

    public void setPreferedPartnerCaste(String preferedPartnerCaste) {
        this.preferedPartnerCaste = preferedPartnerCaste;
    }

    public String getPreferedPartnerCountry() {
        return preferedPartnerCountry;
    }

    public void setPreferedPartnerCountry(String preferedPartnerCountry) {
        this.preferedPartnerCountry = preferedPartnerCountry;
    }

    public String getPreferedPartnerReligion() {
        return preferedPartnerReligion;
    }

    public void setPreferedPartnerReligion(String preferedPartnerReligion) {
        this.preferedPartnerReligion = preferedPartnerReligion;
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

    public String getChoiceOfGroom() {
        return choiceOfGroom;
    }

    public void setChoiceOfGroom(String choiceOfGroom) {
        this.choiceOfGroom = choiceOfGroom;
    }

}
