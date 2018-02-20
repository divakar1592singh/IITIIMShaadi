package com.senzecit.iitiimshaadi.model.common.country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 20/2/18.
 */
public class AllCountry {

    @SerializedName("old_value")
    @Expose
    private String oldValue;
    @SerializedName("name")
    @Expose
    private String name;

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
