package com.senzecit.iitiimshaadi.model.exp_listview;

/**
 * Created by senzec on 3/1/18.
 */

public class ExpPartnerProfileModel {


    private static ExpPartnerProfileModel instance = null;
    private ExpPartnerProfileModel(){

    }

    public static ExpPartnerProfileModel getInstance(){

        if (instance == null){
            instance = new ExpPartnerProfileModel();
        }
        return instance;
    }

    /** Other Operation */

    public String getMinimum_Age() {
        return Minimum_Age;
    }

    public void setMinimum_Age(String minimum_Age) {
        Minimum_Age = minimum_Age;
    }

    public String getMaximum_Age() {
        return Maximum_Age;
    }

    public void setMaximum_Age(String maximum_Age) {
        Maximum_Age = maximum_Age;
    }

    public String getMin_Height() {
        return Min_Height;
    }

    public void setMin_Height(String min_Height) {
        Min_Height = min_Height;
    }

    public String getMax_Height() {
        return Max_Height;
    }

    public void setMax_Height(String max_Height) {
        Max_Height = max_Height;
    }

    public String getMarital_Status() {
        return Marital_Status;
    }

    public void setMarital_Status(String marital_Status) {
        Marital_Status = marital_Status;
    }

    public String getPreferred_Religion() {
        return Preferred_Religion;
    }

    public void setPreferred_Religion(String preferred_Religion) {
        Preferred_Religion = preferred_Religion;
    }

    public String getPreferred_Caste() {
        return Preferred_Caste;
    }

    public void setPreferred_Caste(String preferred_Caste) {
        Preferred_Caste = preferred_Caste;
    }

    public String getPreferred_Country() {
        return Preferred_Country;
    }

    public void setPreferred_Country(String preferred_Country) {
        Preferred_Country = preferred_Country;
    }

    public String getPreferred_Education() {
        return Preferred_Education;
    }

    public void setPreferred_Education(String preferred_Education) {
        Preferred_Education = preferred_Education;
    }

    public String getChoice_of_Groom() {
        return Choice_of_Groom;
    }

    public void setChoice_of_Groom(String choice_of_Groom) {
        Choice_of_Groom = choice_of_Groom;
    }



    public String Minimum_Age;
    public String Maximum_Age;
    public String Min_Height;
    public String Max_Height;
    public String Marital_Status;

    public String Preferred_Religion;
    public String Preferred_Caste;
    public String Preferred_Country;

    public String Preferred_Education;

    public String Choice_of_Groom;

}

