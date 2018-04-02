package com.senzecit.iitiimshaadi.utils;

/**
 * Created by senzec on 30/12/17.
 */

public interface CONSTANTS {

    int splshTime = 200;
    String BASE_URL = "https://www.iitiimshaadi.com/";
    String IMAGE_BASE_URL = "https://iitiimshaadi.com/img/";
    String IMAGE_BASE_URL_SLASHLESS = "https://iitiimshaadi.com/img";
    String IMAGE_AVATAR_URL = "https://iitiimshaadi.com/img/uploads/avatars/";
//    CHAT
    String CHAT_SERVER_URL = "http://35.154.217.225:1109";
    String CHAT_HISTORY_URL = "http://35.154.217.225:1110/";

//    RELATIVE URL
    String USER_REG = "api/user_registration.json";
    String LOGIN_PART_URL = "api/login.json";
    String FORGOT_PASSWORD = "api/forgot_password.json";
    String CHECK_ELIGIBILITY = "api/checkEligibilty.json";
    String FIND_COLLEGE = "api/find_educational_institution.json";

    String SUBS_DASHBOARD_PATH = "api/subscriber_dashboard.json";
    String MY_PROFILE = "api/my_profile.json";

    String BASIC_LIFESTYLE = "api/basic_lifestyle.json";
    String RELIGIOUS_BACKGROUND_POST_URL = "api/religious_background.json";
    String CONTACT_DETAILS = "api/contact_details.json";
    String FAMILY_DETAILS = "api/family_details.json";
    String EDUCATION_CAREER = "api/education_career.json";
    String ABOUT_ME = "api/about_me.json";
    String BASIC_LIFESTYLE_PT_PATH = "api/partner_basic_lifestyle.json";
    String RELIGIOUS_BACKGROUND_PT_PATH = "api/partner_religion_country.json";
    String EDUCATION_CAREER_PT_PATH = "api/partner_education_career.json";
    String CHOICE_OF_PARTNER_PATH = "api/choiceof_partner.json";

    String CONTACT_US_PATH = "api/contact_us.json";
    String CHECK_DUPLICATE = "api/check_duplicate.json";


    String Own_Token = "8848b0bfc931a3fc45e4462c19f06c85";
    String Temp_Token = "85cf4ac699215d12fe4e47bf0f4caa6f";
    String Token_Paid = "7974cd4db32ddea76fe9cfa1f397d9f3";

    //LOGIN
    String LOGGED_TOKEN = "token";
    String LOGGED_USERNAME = "username";
    String LOGGED_USERID = "userid";
    String LOGGED_USER_TYPE = "user_type";
    String GENDER_TYPE = "gender_type";
    String LOGGED_USER_PIC = "user_pic";
    String LOGGED_EMAIL = "email";
    String LOGGED_MOB = "mobile";
    String OTHER_USERID = "other_userid";
    String OTHER_USERNAME = "other_username";

//    Viewer Type
    String VIEWER_TYPE_1 = "paid_subscriber_viewer";
    String VIEWER_TYPE_2 = "subscriber_viewer";
    String VIEWER_TYPE_3 = "subscriber";


//    RESULT PAID
    String SEARCH_TYPE = "search_type";

    String SEARCH_ID = "search_id";
    String SEARCH_KEYWORD = "search_keyword";

    String MIN_AGE = "min_age";
    String MAX_AGE = "max_age";
    String COUNTRY = "country";
    String CITY = "city";
    String LOCATIONS = "locations";
    String DETAILS = "details";
    String RELIGION = "religion";
    String CASTE = "caste";
    String MOTHER_TONGUE = "mother_tongue";
    String MARITAL_STATUS = "marital_status";
    String COURSE = "course";
    String ANNUAL_INCOME = "annual_income";

    String PARTNER_LOC = "partner_loc";
    String MIN_HEIGHT = "min_height";
    String MAX_HEIGHT = "max_height";

//    Plan
    String ONE_MONTH_RS = "7,000";
    String SIX_MONTH_RS = "11,000";
    String TWELVE_MONTH_RS = "16,000";
    String LIFETIME_SUBS_RS = "24,000";

    String ONE_MONTH_DOL = "90";
    String SIX_MONTH_DOL = "160";
    String TWELVE_MONTH_DOL = "250";
    String LIFETIME_SUBS_DOL = "350";

    String AMOUNT_PAY = "amount_payble";
    String PLAN_STATUS = "subscription plan status";


//    Message
    String edu_error_msg = "Select Education";
    String search_ptnr_err_msg = "No Match found";
    String country_error_msg = "Select Country";
    String religion_error_msg = "Select Religion";
    String cast_not_found = "No Cast Found";
    String country_not_found = "Country Not Found";
    String state_not_found = "State Not Found";
    String city_not_found = "City Not Found";
    String album_not_found = "No Image Available";
    String unknown_err = "Something went wrong! \n Try again";
    String no_data = "No data found";
    String tap_message = "Tap one more time";
    String tap_twice_message = "Please, Tap on pic";

    //TYPE
    String BASIC_TYPE = "basic_type";
    String PARTNER_TYPE = "partner_type";

    //
    String METHOD_1 = "method_1";
    String METHOD_2 = "method_2";
    String METHOD_3 = "method_3";
    String METHOD_4 = "method_4";
    String METHOD_5 = "method_5";


}
