package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.ptr_religious_country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 7/2/18.
 */
public class PartnerReligionData {

    @SerializedName("prefered_partner_religion")
    @Expose
    private String preferedPartnerReligion;
    @SerializedName("prefered_partner_caste")
    @Expose
    private String preferedPartnerCaste;
    @SerializedName("prefered_partner_country")
    @Expose
    private String preferedPartnerCountry;

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

    public String getPreferedPartnerCountry() {
        return preferedPartnerCountry;
    }

    public void setPreferedPartnerCountry(String preferedPartnerCountry) {
        this.preferedPartnerCountry = preferedPartnerCountry;
    }

}
