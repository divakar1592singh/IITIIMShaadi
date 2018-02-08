package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_education;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 7/2/18.
 */
public class PartnerEducationCData {

    @SerializedName("prefered_partner_education")
    @Expose
    private String preferedPartnerEducation;

    public String getPreferedPartnerEducation() {
        return preferedPartnerEducation;
    }

    public void setPreferedPartnerEducation(String preferedPartnerEducation) {
        this.preferedPartnerEducation = preferedPartnerEducation;
    }

}
