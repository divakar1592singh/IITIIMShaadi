package com.senzecit.iitiimshaadi.model.api_response_model.paid_dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by senzec on 21/2/18.
 */
public class BasicData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("health_issue")
    @Expose
    private String healthIssue;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("diet")
    @Expose
    private String diet;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("drink")
    @Expose
    private String drink;
    @SerializedName("smoke")
    @Expose
    private String smoke;
    @SerializedName("interest")
    @Expose
    private List<String> interest = null;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("caste")
    @Expose
    private String caste;
    @SerializedName("mother_tounge")
    @Expose
    private String motherTounge;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("alternate_no")
    @Expose
    private String alternateNo;
    @SerializedName("permanent_address")
    @Expose
    private String permanentAddress;
    @SerializedName("permanent_country")
    @Expose
    private String permanentCountry;
    @SerializedName("permanent_state")
    @Expose
    private String permanentState;
    @SerializedName("permanent_city")
    @Expose
    private String permanentCity;
    @SerializedName("permanent_zipcode")
    @Expose
    private String permanentZipcode;
    @SerializedName("current_state")
    @Expose
    private String currentState;
    @SerializedName("current_address")
    @Expose
    private String currentAddress;
    @SerializedName("current_city")
    @Expose
    private String currentCity;
    @SerializedName("current_country")
    @Expose
    private String currentCountry;
    @SerializedName("current_zipcode")
    @Expose
    private String currentZipcode;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("father_occupation")
    @Expose
    private String fatherOccupation;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("mother_occupation")
    @Expose
    private String motherOccupation;
    @SerializedName("brother")
    @Expose
    private String brother;
    @SerializedName("sister")
    @Expose
    private String sister;
    @SerializedName("schooling")
    @Expose
    private String schooling;
    @SerializedName("schooling_year")
    @Expose
    private Integer schoolingYear;
    @SerializedName("graduation")
    @Expose
    private String graduation;
    @SerializedName("graduation_year")
    @Expose
    private Integer graduationYear;
    @SerializedName("graduation_college")
    @Expose
    private String graduationCollege;
    @SerializedName("post_graduation")
    @Expose
    private String postGraduation;
    @SerializedName("post_graduation_year")
    @Expose
    private Integer postGraduationYear;
    @SerializedName("post_graduation_college")
    @Expose
    private String postGraduationCollege;
    @SerializedName("highest_education")
    @Expose
    private String highestEducation;
    @SerializedName("working_as")
    @Expose
    private String workingAs;
    @SerializedName("job_location")
    @Expose
    private String jobLocation;
    @SerializedName("name_of_company")
    @Expose
    private String nameOfCompany;
    @SerializedName("linked_in")
    @Expose
    private String linkedIn;
    @SerializedName("annual_income")
    @Expose
    private String annualIncome;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("profile_complition")
    @Expose
    private Integer profileComplition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealthIssue() {
        return healthIssue;
    }

    public void setHealthIssue(String healthIssue) {
        this.healthIssue = healthIssue;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getMotherTounge() {
        return motherTounge;
    }

    public void setMotherTounge(String motherTounge) {
        this.motherTounge = motherTounge;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateNo() {
        return alternateNo;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }

    public String getPermanentState() {
        return permanentState;
    }

    public void setPermanentState(String permanentState) {
        this.permanentState = permanentState;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getPermanentZipcode() {
        return permanentZipcode;
    }

    public void setPermanentZipcode(String permanentZipcode) {
        this.permanentZipcode = permanentZipcode;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getCurrentZipcode() {
        return currentZipcode;
    }

    public void setCurrentZipcode(String currentZipcode) {
        this.currentZipcode = currentZipcode;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherOccupation() {
        return fatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation = fatherOccupation;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherOccupation() {
        return motherOccupation;
    }

    public void setMotherOccupation(String motherOccupation) {
        this.motherOccupation = motherOccupation;
    }

    public String getBrother() {
        return brother;
    }

    public void setBrother(String brother) {
        this.brother = brother;
    }

    public String getSister() {
        return sister;
    }

    public void setSister(String sister) {
        this.sister = sister;
    }

    public String getSchooling() {
        return schooling;
    }

    public void setSchooling(String schooling) {
        this.schooling = schooling;
    }

    public Integer getSchoolingYear() {
        return schoolingYear;
    }

    public void setSchoolingYear(Integer schoolingYear) {
        this.schoolingYear = schoolingYear;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getGraduationCollege() {
        return graduationCollege;
    }

    public void setGraduationCollege(String graduationCollege) {
        this.graduationCollege = graduationCollege;
    }

    public String getPostGraduation() {
        return postGraduation;
    }

    public void setPostGraduation(String postGraduation) {
        this.postGraduation = postGraduation;
    }

    public Integer getPostGraduationYear() {
        return postGraduationYear;
    }

    public void setPostGraduationYear(Integer postGraduationYear) {
        this.postGraduationYear = postGraduationYear;
    }

    public String getPostGraduationCollege() {
        return postGraduationCollege;
    }

    public void setPostGraduationCollege(String postGraduationCollege) {
        this.postGraduationCollege = postGraduationCollege;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getWorkingAs() {
        return workingAs;
    }

    public void setWorkingAs(String workingAs) {
        this.workingAs = workingAs;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Integer getProfileComplition() {
        return profileComplition;
    }

    public void setProfileComplition(Integer profileComplition) {
        this.profileComplition = profileComplition;
    }

}
