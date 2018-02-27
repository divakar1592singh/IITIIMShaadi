package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_education;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 27/2/18.
 */
public class PartnerEducationCData {

    @SerializedName("prefered_partner_education")
    @Expose
    private List<String> preferedPartnerEducation = null;

    public List<String> getPreferedPartnerEducation() {
        return preferedPartnerEducation;
    }

    public void setPreferedPartnerEducation(List<String> preferedPartnerEducation) {
        this.preferedPartnerEducation = preferedPartnerEducation;
    }

}
