package com.senzecit.iitiimshaadi.model.api_response_model.common.city;

/**
 * Created by senzec on 20/2/18.
 */


        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class CitiesAccCountryResponse {

    @SerializedName("allCities")
    @Expose
    private List<AllCity> allCities = null;

    public List<AllCity> getAllCities() {
        return allCities;
    }

    public void setAllCities(List<AllCity> allCities) {
        this.allCities = allCities;
    }

}