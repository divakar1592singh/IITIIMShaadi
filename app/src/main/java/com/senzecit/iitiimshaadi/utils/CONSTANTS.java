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

    String RTGS_PAYMENT_URL = "api/send_ccd_mail.json";
    String CHAT_USER_COUNT_PATH = "http://35.154.217.225:1110/viewUserCount";
    String RECENT_SEARCH_URL = "http://35.154.217.225:1110/getRecentSearch";

    String PAYPAL_SUCCESS_URL = "http://35.154.217.225:1110/paymentSuccess";


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
    String COUNTRY_ID = "country_id";
    String CITY_ID = "city_id";
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
    String PLAN_EXP = "plan_exp";


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


    //MEDIA COVERAGE WEB
    String MEDIA_URL_1 = "https://www.economist.com/news/business/21729571-only-tenth-people-seeking-spouse-use-internet-set-rise-online";
    String MEDIA_URL_2 = "https://iitiimshaadi.com/admin/img/uploads/1488360418-BrandEquity.jpg";
    String MEDIA_URL_3 = "https://iitiimshaadi.com/images/hello.mp4";
    String MEDIA_URL_4 = "http://blogs.wsj.com/indiarealtime/2014/08/18/a-matter-of-degree-wedding-website-for-iit-and-iim-graduates/";
    String MEDIA_URL_5 = "http://economictimes.indiatimes.com/articleshow/40958879.cms";
    String MEDIA_URL_6 = "https://www.hindustantimes.com/india/matchmaking-portal-for-iit-iim-graduates/story-u2XFPJX6NXjkSeowFXLetI.html";
    String MEDIA_URL_7 = "https://timesofindia.indiatimes.com/India/Eligible-for-marriage-only-if-you-have-an-elite-education/articleshow/41399863.cms";
    String MEDIA_URL_8 = "http://www.newindianexpress.com/cities/hyderabad/2014/aug/21/Are-You-Qualified-Enough-to-Get-Married-650589.html";
    String MEDIA_URL_9 = "http://www.business-standard.com/article/beyond-business/highly-educated-and-ready-to-mingle-log-on-114091100232_1.html";
    String MEDIA_URL_10 = "https://www.cnbc.com/2014/12/17/saying-i-do-goes-high-tech-in-india.html";
    String MEDIA_URL_11 = "https://www.deccanchronicle.com/150113/nation-current-affairs/article/iits-iims-trend-matrimonial-websites";
    String MEDIA_URL_12 = "http://www.deccanherald.com/content/567584/matchmaking-made-easy-iit-iim.html";
    String MEDIA_URL_13 = "https://iitiimshaadi.com/images/sandesh.png";
    String MEDIA_URL_14 = "https://www.redfmindia.in/";
    String MEDIA_URL_15 = "http://iitiimshaadi.com/webroot/document/Doc2.docx";
    String MEDIA_URL_16 = "https://www.youthkiawaaz.com/2017/07/iitiimshaadi-matrimony-in-india/    ";
    String MEDIA_URL_17 = "https://www.buzzfeed.com/sahilrizwan/shaadi-se-pehle?utm_term=.mwDJa0pxW#.pq31DyQre";
    String MEDIA_URL_18 = "https://www.firstpost.com/business/living-business/seeking-iit-iim-graduates-as-husbands-now-a-portal-for-the-academic-elite-1985165.html";
    String MEDIA_URL_19 = "https://www.scoopwhoop.com/news/wedding-bells/?ref=search#.gwk8y32s6";
    String MEDIA_URL_20 = "http://www.rediff.com/getahead/report/specials-now-dating-sites-for-the-highly-educated/20140915.htm";


    String SEARCH_BY_ID = "id";
    String SEARCH_BY_KEYWORD = "keyword";
    String ADVANCE_SEARCH = "advance";


}
