package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.groom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 13/2/18.
 */
public class PartnerChoiceData {

    @SerializedName("choice_of_groom")
    @Expose
    private String choiceOfGroom;

    public String getChoiceOfGroom() {
        return choiceOfGroom;
    }

    public void setChoiceOfGroom(String choiceOfGroom) {
        this.choiceOfGroom = choiceOfGroom;
    }

}
