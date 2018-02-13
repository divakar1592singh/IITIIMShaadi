package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_basic_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 12/2/18.
 */
public class PartnerBasicData {

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

}
