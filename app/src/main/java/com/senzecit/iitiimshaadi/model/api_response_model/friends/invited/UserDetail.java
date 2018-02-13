package com.senzecit.iitiimshaadi.model.api_response_model.friends.invited;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 12/2/18.
 */
public class UserDetail {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name1")
    @Expose
    private String name1;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("caste")
    @Expose
    private String caste;
    @SerializedName("post_graduation")
    @Expose
    private String postGraduation;
    @SerializedName("post_graduation_college")
    @Expose
    private String postGraduationCollege;
    @SerializedName("graduation")
    @Expose
    private String graduation;
    @SerializedName("graduation_college")
    @Expose
    private String graduationCollege;
    @SerializedName("name_of_company")
    @Expose
    private String nameOfCompany;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getPostGraduation() {
        return postGraduation;
    }

    public void setPostGraduation(String postGraduation) {
        this.postGraduation = postGraduation;
    }

    public String getPostGraduationCollege() {
        return postGraduationCollege;
    }

    public void setPostGraduationCollege(String postGraduationCollege) {
        this.postGraduationCollege = postGraduationCollege;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getGraduationCollege() {
        return graduationCollege;
    }

    public void setGraduationCollege(String graduationCollege) {
        this.graduationCollege = graduationCollege;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

}
