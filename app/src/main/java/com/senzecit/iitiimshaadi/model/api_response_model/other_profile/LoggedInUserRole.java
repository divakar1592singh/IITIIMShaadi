package com.senzecit.iitiimshaadi.model.api_response_model.other_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 22/2/18.
 */
public class LoggedInUserRole {

    @SerializedName("role")
    @Expose
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
