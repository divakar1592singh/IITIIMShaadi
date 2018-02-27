package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.ptr_religious_country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 7/2/18.
 */import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartnerReligionData {

    @SerializedName("prefered_partner_religion")
    @Expose
    private String preferedPartnerReligion;
    @SerializedName("prefered_partner_caste")
    @Expose
    private List<String> preferedPartnerCaste = null;
    @SerializedName("prefered_partner_country")
    @Expose
    private List<String> preferedPartnerCountry = null;

    public String getPreferedPartnerReligion() {
        return preferedPartnerReligion;
    }

    public void setPreferedPartnerReligion(String preferedPartnerReligion) {
        this.preferedPartnerReligion = preferedPartnerReligion;
    }

    public List<String> getPreferedPartnerCaste() {
        return preferedPartnerCaste;
    }

    public void setPreferedPartnerCaste(List<String> preferedPartnerCaste) {
        this.preferedPartnerCaste = preferedPartnerCaste;
    }

    public List<String> getPreferedPartnerCountry() {
        return preferedPartnerCountry;
    }

    public void setPreferedPartnerCountry(List<String> preferedPartnerCountry) {
        this.preferedPartnerCountry = preferedPartnerCountry;
    }

}