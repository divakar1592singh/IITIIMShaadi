package com.senzecit.iitiimshaadi.utils;

/**
 * Created by senzec on 31/3/18.
 */

public interface AppMessage {

    // HEADER
    String ALERT = "Alert";
    String INFO = "Info";
    String WARNING = "Warning";

    // Common Message
    String DATA_SUBMIT_INFO = "Data Submited Successfully";
    String SOME_ERROR_INFO = "Something went wrong!\n Please, Try Again";
    String TRY_AGAIN_INFO = "Please, Try Again!";
    String IMAGE_UPLOAD_INFO = "Image Uploaded Successfully";
    String PROFILE_CHANGED_INFO = "Image Uploaded Successfully";
    String CROP_ERROR_INFO = "Unable to crop";
    String PAGE_REFRESH_INFO = "Page Refreshed";

    String AGE_DIFF_ERROR_INFO = "Max age should be greater than min age !";
    String AGE_DIFF_5_INFO = "Max age and min age difference is more than 5 years!";
    String AGE_ERROR_INFO = "Age should be valid.";

    String HEIGHT_DIFF_ERROR_INFO = "Max height should be greater than min height !";
    String HEIGHT_ERROR_INFO = "Height should be valid !";

    /*LOGIN*/
    String USERNAME_EMPTY = "Username/Email can't be empty";
    String USER_VALID = "Check Username/Email is valid";
    String USERPWD_INVALID = "Check Username/Email or Password is valid";
    String PASSWORD_EMPTY = "Password can't be empty";
    String AGE_LIMIT = "Age should be more than 20";
    String MAX_AGE_LIMIT = "Max Age should be more than 20";

    String NO_CHAT_USER = "No User is available for chat";




}
