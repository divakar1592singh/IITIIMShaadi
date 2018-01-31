package com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senzec on 29/1/18.
 */
public class Query {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name1")
    @Expose
    private String name1;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("permanent_country")
    @Expose
    private String permanentCountry;
    @SerializedName("permanent_city")
    @Expose
    private String permanentCity;
    @SerializedName("mother_tounge")
    @Expose
    private String motherTounge;
    @SerializedName("admin_search_education")
    @Expose
    private String adminSearchEducation;
    @SerializedName("name_of_company")
    @Expose
    private String nameOfCompany;
    @SerializedName("graduation")
    @Expose
    private String graduation;
    @SerializedName("graduation_college")
    @Expose
    private String graduationCollege;
    @SerializedName("graduation_year")
    @Expose
    private Integer graduationYear;
    @SerializedName("height_type")
    @Expose
    private String heightType;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("caste")
    @Expose
    private String caste;
    @SerializedName("working_as")
    @Expose
    private String workingAs;
    @SerializedName("post_graduation")
    @Expose
    private String postGraduation;
    @SerializedName("post_graduation_college")
    @Expose
    private String postGraduationCollege;
    @SerializedName("post_graduation_year")
    @Expose
    private Integer postGraduationYear;
    @SerializedName("highest_education")
    @Expose
    private String highestEducation;
    @SerializedName("smoke")
    @Expose
    private String smoke;
    @SerializedName("drink")
    @Expose
    private String drink;
    @SerializedName("diet")
    @Expose
    private String diet;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("user")
    @Expose
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getMotherTounge() {
        return motherTounge;
    }

    public void setMotherTounge(String motherTounge) {
        this.motherTounge = motherTounge;
    }

    public String getAdminSearchEducation() {
        return adminSearchEducation;
    }

    public void setAdminSearchEducation(String adminSearchEducation) {
        this.adminSearchEducation = adminSearchEducation;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
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

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getHeightType() {
        return heightType;
    }

    public void setHeightType(String heightType) {
        this.heightType = heightType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getWorkingAs() {
        return workingAs;
    }

    public void setWorkingAs(String workingAs) {
        this.workingAs = workingAs;
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

    public Integer getPostGraduationYear() {
        return postGraduationYear;
    }

    public void setPostGraduationYear(Integer postGraduationYear) {
        this.postGraduationYear = postGraduationYear;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
