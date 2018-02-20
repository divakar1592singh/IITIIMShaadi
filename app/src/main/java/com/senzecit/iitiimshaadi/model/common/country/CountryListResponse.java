package com.senzecit.iitiimshaadi.model.common.country;

/**
 * Created by senzec on 27/1/18.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class CountryListResponse {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("allCountries")
    @Expose
    private List<AllCountry> allCountries = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<AllCountry> getAllCountries() {
        return allCountries;
    }

    public void setAllCountries(List<AllCountry> allCountries) {
        this.allCountries = allCountries;
    }

}
