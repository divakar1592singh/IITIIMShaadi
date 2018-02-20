package com.senzecit.iitiimshaadi.model.api_response_model.common;

/**
 * Created by senzec on 20/2/18.
 */

public class CountryModel {

    public CountryModel(String countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    String countryId;
    String countryName;
}
