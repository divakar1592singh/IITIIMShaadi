package com.senzecit.iitiimshaadi.model.exp_listview;

/**
 * Created by senzec on 3/1/18.
 */

public class ExpOwnProfileModel {

    private static ExpOwnProfileModel instance = null;
    private ExpOwnProfileModel(){

    }

    public static ExpOwnProfileModel getInstance(){

        if (instance == null){
            instance = new ExpOwnProfileModel();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getDiet() {
        return Diet;
    }

    public void setDiet(String diet) {
        Diet = diet;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }

    public String getMarital_Status() {
        return Marital_Status;
    }

    public void setMarital_Status(String marital_Status) {
        Marital_Status = marital_Status;
    }

    public String getDrink() {
        return Drink;
    }

    public void setDrink(String drink) {
        Drink = drink;
    }

    public String getSmoke() {
        return Smoke;
    }

    public void setSmoke(String smoke) {
        Smoke = smoke;
    }

    public String getHealth_Issue() {
        return Health_Issue;
    }

    public void setHealth_Issue(String health_Issue) {
        Health_Issue = health_Issue;
    }
    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getInterests() {
        return Interests;
    }

    public void setInterests(String interests) {
        Interests = interests;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getCaste() {
        return Caste;
    }

    public void setCaste(String caste) {
        Caste = caste;
    }

    public String getMother_Tongue() {
        return Mother_Tongue;
    }

    public void setMother_Tongue(String mother_Tongue) {
        Mother_Tongue = mother_Tongue;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getAlternate_Number() {
        return Alternate_Number;
    }

    public void setAlternate_Number(String alternate_Number) {
        Alternate_Number = alternate_Number;
    }

    public String getPermanent_Address() {
        return Permanent_Address;
    }

    public void setPermanent_Address(String permanent_Address) {
        Permanent_Address = permanent_Address;
    }

    public String getPermanent_Country() {
        return Permanent_Country;
    }

    public void setPermanent_Country(String permanent_Country) {
        Permanent_Country = permanent_Country;
    }

    public String getPermanent_State() {
        return Permanent_State;
    }

    public void setPermanent_State(String permanent_State) {
        Permanent_State = permanent_State;
    }

    public String getPermanent_City() {
        return Permanent_City;
    }

    public void setPermanent_City(String permanent_City) {
        Permanent_City = permanent_City;
    }

    public String getZip_Code_Perm() {
        return Zip_Code_Perm;
    }

    public void setZip_Code_Perm(String zip_Code_Perm) {
        Zip_Code_Perm = zip_Code_Perm;
    }

    public String getCurrent_Address() {
        return Current_Address;
    }

    public void setCurrent_Address(String current_Address) {
        Current_Address = current_Address;
    }

    public String getCurrent_Country() {
        return Current_Country;
    }

    public void setCurrent_Country(String current_Country) {
        Current_Country = current_Country;
    }

    public String getCurrent_State() {
        return Current_State;
    }

    public void setCurrent_State(String current_State) {
        Current_State = current_State;
    }

    public String getCurrent_City() {
        return Current_City;
    }

    public void setCurrent_City(String current_City) {
        Current_City = current_City;
    }

    public String getZip_Code_Cur() {
        return Zip_Code_Cur;
    }

    public void setZip_Code_Cur(String zip_Code_Cur) {
        Zip_Code_Cur = zip_Code_Cur;
    }

    public String getFather_Name() {
        return Father_Name;
    }

    public void setFather_Name(String father_Name) {
        Father_Name = father_Name;
    }

    public String getFather_Occupation() {
        return Father_Occupation;
    }

    public void setFather_Occupation(String father_Occupation) {
        Father_Occupation = father_Occupation;
    }

    public String getMother_Name() {
        return Mother_Name;
    }

    public void setMother_Name(String mother_Name) {
        Mother_Name = mother_Name;
    }

    public String getMother_Occupation() {
        return Mother_Occupation;
    }

    public void setMother_Occupation(String mother_Occupation) {
        Mother_Occupation = mother_Occupation;
    }

    public String getDetails_Sisters() {
        return Details_Sisters;
    }

    public void setDetails_Sisters(String details_Sisters) {
        Details_Sisters = details_Sisters;
    }

    public String getDetails_Brothers() {
        return Details_Brothers;
    }

    public void setDetails_Brothers(String details_Brothers) {
        Details_Brothers = details_Brothers;
    }

    public String getSchooling() {
        return Schooling;
    }

    public void setSchooling(String schooling) {
        Schooling = schooling;
    }

    public String getSchooling_Year() {
        return Schooling_Year;
    }

    public void setSchooling_Year(String schooling_Year) {
        Schooling_Year = schooling_Year;
    }

    public String getGraduation() {
        return Graduation;
    }

    public void setGraduation(String graduation) {
        Graduation = graduation;
    }

    public String getGraduation_College() {
        return Graduation_College;
    }

    public void setGraduation_College(String graduation_College) {
        Graduation_College = graduation_College;
    }

    public String getGraduation_Year() {
        return Graduation_Year;
    }

    public void setGraduation_Year(String graduation_Year) {
        Graduation_Year = graduation_Year;
    }

    public String getPost_Graduation() {
        return Post_Graduation;
    }

    public void setPost_Graduation(String post_Graduation) {
        Post_Graduation = post_Graduation;
    }

    public String getPost_Graduation_College() {
        return Post_Graduation_College;
    }

    public void setPost_Graduation_College(String post_Graduation_College) {
        Post_Graduation_College = post_Graduation_College;
    }

    public String getPost_Graduation_Year() {
        return Post_Graduation_Year;
    }

    public void setPost_Graduation_Year(String post_Graduation_Year) {
        Post_Graduation_Year = post_Graduation_Year;
    }

    public String getHighest_Education() {
        return Highest_Education;
    }

    public void setHighest_Education(String highest_Education) {
        Highest_Education = highest_Education;
    }

    public String getWorking_With() {
        return Working_With;
    }

    public void setWorking_With(String working_With) {
        Working_With = working_With;
    }

    public String getWorking_As() {
        return Working_As;
    }

    public void setWorking_As(String working_As) {
        Working_As = working_As;
    }

    public String getWork_Location() {
        return Work_Location;
    }

    public void setWork_Location(String work_Location) {
        Work_Location = work_Location;
    }

    public String getAnnual_Income() {
        return Annual_Income;
    }

    public void setAnnual_Income(String annual_Income) {
        Annual_Income = annual_Income;
    }

    public String getLinkdIn_Url() {
        return LinkdIn_Url;
    }

    public void setLinkdIn_Url(String linkdIn_Url) {
        LinkdIn_Url = linkdIn_Url;
    }

    public String getAbout_you() {
        return About_you;
    }

    public void setAbout_you(String about_you) {
        About_you = about_you;
    }

    /** Other Operation */

    public String name;
    public String Profile;
    public String Age;
    public String Diet;
    public String Date_Of_Birth;
    public String Marital_Status;
    public String Drink;
    public String Smoke;

    public String Health_Issue;
    public String Height;
    public String Interests;

    public String Religion;
    public String Caste;
    public String Mother_Tongue;

    public String Phone_Number;
    public String Alternate_Number;
    public String Permanent_Address;
    public String Permanent_Country;
    public String Permanent_State;
    public String Permanent_City;
    public String Zip_Code_Perm;
    public String Current_Address;
    public String Current_Country;
    public String Current_State;
    public String Current_City;
    public String Zip_Code_Cur;

    public String Father_Name;
    public String Father_Occupation;
    public String Mother_Name;
    public String Mother_Occupation;
    public String Details_Sisters;
    public String Details_Brothers;

    public String Schooling;
    public String Schooling_Year;
    public String Graduation;
    public String Graduation_College;
    public String Graduation_Year;
    public String Post_Graduation;
    public String Post_Graduation_College;
    public String Post_Graduation_Year;
    public String Highest_Education;
    public String Working_With;
    public String Working_As;
    public String Work_Location;
    public String Annual_Income;
    public String LinkdIn_Url;

    public String About_you;

    public void resetModel(){

        setName("");
        setProfile("");
        setAge("");
        setDiet("");
        setDate_Of_Birth("");
        setMarital_Status("");
        setDrink("");
        setSmoke("");

        setHealth_Issue("");
        setHeight("");
        setInterests("");

        setReligion("");
        setCaste("");
        setMother_Tongue("");

        setPhone_Number("");
        setAlternate_Number("");
        setPermanent_Address("");
        setPermanent_Country("");
        setPermanent_State("");
        setPermanent_City("");
        setZip_Code_Perm("");
        setCurrent_Address("");
        setCurrent_Country("");
        setCurrent_State("");
        setCurrent_City("");
        setZip_Code_Cur("");

        setFather_Name("");
        setFather_Occupation("");
        setMother_Name("");
        setMother_Occupation("");
        setDetails_Sisters("");
        setDetails_Brothers("");

        setSchooling("");
        setSchooling_Year("");
        setGraduation("");
        setGraduation_College("");
        setGraduation_Year("");
        setPost_Graduation("");
        setPost_Graduation_College("");
        setPost_Graduation_Year("");
        setHighest_Education("");
        setWorking_With("");
        setWorking_As("");
        setWork_Location("");
        setAnnual_Income("");
        setLinkdIn_Url("");

        setAbout_you("");

    }


}
