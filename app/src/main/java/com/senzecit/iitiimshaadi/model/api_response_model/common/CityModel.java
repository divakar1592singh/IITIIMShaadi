package com.senzecit.iitiimshaadi.model.api_response_model.common;

/**
 * Created by senzec on 22/3/18.
 */

public class CityModel {

    public CityModel(String cityName, String cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    String cityName;
    String cityId;

}
