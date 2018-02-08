package com.senzecit.iitiimshaadi.model.api_response_model.subscriber.education_career;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 7/2/18.
 */
public class EducationCData {

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

}
