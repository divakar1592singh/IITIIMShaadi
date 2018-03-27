package com.senzecit.iitiimshaadi.api;

import com.senzecit.iitiimshaadi.chat.ChatUserListRequest;
import com.senzecit.iitiimshaadi.chat.SingleChatPostRequest;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.AllAlbumResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.chat_user.ChatUserListModel;
import com.senzecit.iitiimshaadi.model.api_response_model.common.city.CitiesAccCountryResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.rename_folder.RenameFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.DateToAgeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.forgot_password.ForgotPasswordResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.InvitedFriendResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.MyFriendsResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.requested_friend.RequestedFriendResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.shortlisted.ShortlistedFriendResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.general_setting.GeneralSettingResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.new_register.NewRegistrationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.notification.all.AllNotificationRespnse;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.OtherProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_dashboard.PaidDashboardResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.PaidSubscriberResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.pic_response.SetProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.EligibilityResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.find_college.FindCollegeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_institution.QuickRegInstitutionResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream.QuickRegStreamResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.socket.SingleChatHistoryModel;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.about_me.AboutMeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.basic_profile.BasicProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.contact_details.ContactDetailsResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.education_career.EducationCareerResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.email_verification.EmailVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.familty_detail.FamilyDetailResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.groom.ChoiceOfGroomResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification.IdVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.main.SubscriberMainResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_basic_profile.ParnerBasicProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_education.PtrEduCareerResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.ptr_religious_country.PtrReligionCountryResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.religious_background.ReligiousBackgroundResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscription_retrieve.SubsRetrieveResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.about_me.AboutMeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.custom_folder.RenameFolderRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.general_setting.GeneralSettingRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.groom.ChoiceOfGroomRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.ForgotPasswordRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.NewRegistrationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegEligibilityRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegFindCollegeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegInstitutionRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegStreamRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.search_partner_subs.PaidSubsAdvanceSearchRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.search_partner_subs.SubsAdvanceSearchRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.contact_details.ContactDetailsRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.education_career.EducationCareerRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.email_verification.EmailVerificationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.family_detail.FamilyDetailRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.id_verification.IdVerificationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.profile.BasicProfileRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.pt_education.PtrEduCareerRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.ptr_basic_profile.ParnerBasicProfileRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.ptr_religious_country.PtrReligionCountryRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.religious.ReligiousBackgroundRequest;
import com.senzecit.iitiimshaadi.model.common.contact_us.ContactUsRequest;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ravi on 13/12/17.
 */

public interface APIInterface {

/** QUICK REGISTER */
    @POST("api/checkEligibilty.json")
    Call<QuickRegStreamResponse> fetchStreamData(@Body QuickRegStreamRequest quickRegStreamRequest);

    @POST("api/find_educational_institution.json")
    Call<FindCollegeResponse> quickRegFindCollege(@Body QuickRegFindCollegeRequest quickRegFindCollegeRequest);

/** NEW USER REGISTER */
    @POST("api/user_registration.json")
    Call<NewRegistrationResponse> newUserRegistration(@Body NewRegistrationRequest newRegistrationRequest);

/** Login */
    @POST("api/login.json")
    Call<LoginResponse> loginInUser(@Body LoginRequest loginRequest);

/** Forgot Password */
    @POST("api/forgot_password.json")
    Call<ForgotPasswordResponse> forgotPasswordOfUser(@Body ForgotPasswordRequest forgotPasswordRequest);

/** Subscriber */
    /* EMAIL VERIFICATION*/
    @POST("api/email_verification.json")
    Call<AddFolderResponse> emailVerification(@Body EmailVerificationRequest emailVerificationRequest);

/** File Upload*/
    /*Profile*/
    @Multipart
    @POST("api/set_profile_picture.json")
    Call<AddFolderResponse> profileImageUpload(@Part MultipartBody.Part file, @Part("file[]") RequestBody id_proof, @Query("token") String token);

    /*Profile*/
    @Multipart
    @POST("api/set_profile_picture.json")
    Call<SetProfileResponse> setProfileFromURI(@Part MultipartBody.Part file, @Part("file[]") RequestBody id_proof, @Query("token") String token);

    /*Album*/
    @Multipart
    @POST("api/profile_image_upload.json")
    Call<AddFolderResponse> imageUpload(@Part MultipartBody.Part file, @Part("file[]") RequestBody id_proof, @Query("token") String token);

    /*ID*/
    @Multipart
    @POST("api/id_upload.json")
    Call<IdVerificationResponse> idVerification(@Part MultipartBody.Part file, @Part("id_proof") RequestBody id_proof, @Query("token") String token);

    /* BioData */
    @Multipart
    @POST("api/upload_document.json")
    Call<IdVerificationResponse> biodataUpload(@Part MultipartBody.Part file, @Part("bioData") RequestBody requestBody, @Query("token") String token);
    /*Highest Education*/
    @Multipart
    @POST("api/upload_document.json")
    Call<IdVerificationResponse> highestEduUpload(@Part MultipartBody.Part file, @Part("higher_document") RequestBody requestBody, @Query("token") String token);
    /* Under Graduate */
    @Multipart
    @POST("api/upload_document.json")
    Call<IdVerificationResponse> underGradCertUpload(@Part MultipartBody.Part file, @Part("under_graduate") RequestBody requestBody, @Query("token") String token);
    /* Post Graduate */
    @Multipart
    @POST("api/upload_document.json")
    Call<IdVerificationResponse> postGradCertUpload(@Part MultipartBody.Part file, @Part("post_graduate") RequestBody requestBody, @Query("token") String token);

//    ========COMMON========
 /*   @FormUrlEncoded
    @POST("api/country.json")
    Call<CountryListResponse> countryList(@Field("token")String token);

    @FormUrlEncoded
    @POST("api/states_acc_country.json")
    Call<StateListResponse> stateList(@Field("token")String token, @Field("country")String country );
*/
    @FormUrlEncoded
    @POST("api/cities.json")
    Call<CitiesAccCountryResponse> cityList(@Field("country_id")String country_id);

/*
    @FormUrlEncoded
    @POST("api/caste.json")
    Call<CasteAccReligionResponse> casteList(@Field("token")String token, @Field("religion")String religion );
*/

//========================
    /** Own Profile */
    @FormUrlEncoded
    @POST("api/my_profile.json")
    Call<MyProfileResponse> myProfileData(@Field("token")String token);

    @FormUrlEncoded
    @POST("api/other_profile.json")
    Call<OtherProfileResponse> otherProfileData(@Field("token")String token, @Field("other_user")String other_user);

    /** Advance Search - Subs */
    @POST("api/advance_search.json")
    Call<SubsAdvanceSearchResponse> advanceSearch(@Body SubsAdvanceSearchRequest advanceSearchRequest);

    /*Move To*/
    @FormUrlEncoded
    @POST("api/add_in_group.json")
    Call<AddFolderResponse> serviceMoveTo(@Field("token")String token, @Field("friend_id")String friend_id, @Field("folder_id")String folder_id);




    /** ID Search - Paid Subs */
    @GET("api/search_by_id.json?")
    Call<PaidSubscriberResponse> idSearchPaid(@Query("token") String token, @Query("username") String username);

    /** Keyword Search - Paid Subs */
    @GET("api/search_by_keyword.json")
    Call<SubsAdvanceSearchResponse> keywordSearchPaid(@Query("token") String token, @Query("page")String page, @Query("keyword") String keyword);

    /** Advance Search - Paid Subs */

@GET("api/advance_search.json")
Call<SubsAdvanceSearchResponse> advanceSearchPaid(@Query("token") String token, @Query("page")String page, @Query("minage")String minage,
                                                      @Query("maxage")String maxage, @Query("country")String country, @Query("city[]")String[] city,
                                                      @Query("location[]")String[] location, @Query("religion")String religion, @Query("caste[]")String[] caste,
                                                      @Query("mother_tounge[]")String[] mother_tounge, @Query("marital_status[]")String[] marital_status, @Query("min_height")String min_height,
                                                      @Query("max_height")String max_height, @Query("course[]")String[] course, @Query("annual_income[]")String[] annual_income);


/*
    */
/** ID Search - Paid Subs *//*

    @FormUrlEncoded
    @POST("api/search_by_id.json")
    Call<PaidSubscriberResponse> idSearchPaid1(@Field("token")String token, @Field("user_id")String userId);
*/

/*
    */
/** Keyword Search - Paid Subs */
/*
    @FormUrlEncoded
    @POST("api/search_by_keyword.json")
    Call<SubsAdvanceSearchResponse> keywordSearchPaid(@Field("token")String token, @Field("keyword")String keyword);
*/

/*
    */
/** Advance Search - Paid Subs *//*

    @POST("api/advance_search.json")
    Call<SubsAdvanceSearchResponse> advanceSearchPaid(@Body PaidSubsAdvanceSearchRequest advanceSearchRequest);
*/

    /** Custom Folder List */
    @FormUrlEncoded
    @POST("api/custom_folder.json")
    Call<FolderListModelResponse> customFolderList(@Field("token")String token);
    /*Add*/
    @FormUrlEncoded
    @POST("api/add_folder.json")
    Call<AddFolderResponse> addFolder(@Field("token")String token, @Field("name")String name);
    /*Rename*/
    @POST("api/rename_folder.json")
    Call<RenameFolderResponse> renameFolder(@Body RenameFolderRequest request);
    /*Delete*/
    @FormUrlEncoded
    @POST("api/delete_folder.json")
    Call<AddFolderResponse> deleteFolder(@Field("token")String token, @Field("folder_id")Integer folder_id);

    /**Subscriber Dashboard*/
    /* Subscriber Dashboard */
    @FormUrlEncoded
    @POST("api/subscriber_dashboard.json")
    Call<SubscriberMainResponse> subscribeDashoard(@Field("token")String token);
    /* Subscriber Dashboard */
    @FormUrlEncoded
    @POST("api/paid_subscriber.json")
    Call<PaidDashboardResponse> subscribeDashoardPaid(@Field("token")String token);

    /* Resend OTP */
    @FormUrlEncoded
    @POST("api/send_otp_again.json")
    Call<AddFolderResponse> resendOTP(@Field("token")String token);
    /* OTP Verification */
    @FormUrlEncoded
    @POST("api/otp_verification.json")
    Call<AddFolderResponse> verifiyOTP(@Field("token")String token, @Field("otp")String otp);

    /*1*/
    @POST("api/basic_lifestyle.json")
    Call<AddFolderResponse> sendBasicProfile(@Body BasicProfileRequest request);
    /*2*/
    @POST("api/religious_background.json")
    Call<AddFolderResponse> sendReligiousBackground(@Body ReligiousBackgroundRequest request);
    /*3*/
    @POST("api/contact_details.json")
    Call<AddFolderResponse> sendContactDetails(@Body ContactDetailsRequest request);
    /*4*/
    @POST("api/family_details.json")
    Call<AddFolderResponse> sendFamilyDetails(@Body FamilyDetailRequest request);
    /*5*/
    @POST("api/education_career.json")
    Call<AddFolderResponse> sendEducationCareer(@Body EducationCareerRequest request);
    /*6*/
    @POST("api/about_me.json")
    Call<AddFolderResponse> sendAboutMe(@Body AboutMeRequest request);
    /**Partner*/
    /*7*/
    @POST("api/partner_basic_lifestyle.json")
    Call<AddFolderResponse> sendPartnerBasicProfile(@Body ParnerBasicProfileRequest request);
    /*8*/
    @POST("api/partner_religion_country.json")
    Call<AddFolderResponse> sendPartnerReligionCountry(@Body PtrReligionCountryRequest request);
    /*9*/
    @POST("api/partner_education_career.json")
    Call<AddFolderResponse> sendPartnerEduCareer(@Body PtrEduCareerRequest request);
    /*10*/
    @POST("api/choiceof_partner.json")
    Call<AddFolderResponse> sendPartnerGroom(@Body ChoiceOfGroomRequest request);

    /** General Setting */
    @POST("api/general_settings.json")
    Call<GeneralSettingResponse> changeGeneralSetting(@Body GeneralSettingRequest request);
    /** Deactivate Account */
    @FormUrlEncoded
    @POST("api/deactivate_profile.json")
    Call<AddFolderResponse> deactivateAccount(@Field("token")String token, @Field("deactivation_reason")String deactivation_reason);

    /** Subscription */
    @FormUrlEncoded
    @POST("api/subscription.json")
    Call<SubsRetrieveResponse> retrieveSubscription(@Field("token")String token);

    /** Album */
    @FormUrlEncoded
    @POST("api/albums.json")
    Call<AllAlbumResponse> allAlbumist(@Field("token")String token);

    @Multipart
    @POST("api/profile_image_upload.json")
    Call<AddFolderResponse> uploadImageFile(@Part MultipartBody.Part file, @Part("file[]") RequestBody name, @Path("user_id") String user_id);

    /** Setting Album */
    @FormUrlEncoded
    @POST("api/change_permission.json")
    Call<AddFolderResponse> settingAlbum(@Field("token")String token, @Field("album_id")String album_id, @Field("privacy")String privacy);
    /** Delete Album */
    @FormUrlEncoded
    @POST("api/delete_album.json")
    Call<AddFolderResponse> deleteAlbum(@Field("token")String token, @Field("album_id")String album_id);
    /** Set Profile Album */
    @FormUrlEncoded
    @POST("api/set_profile_picture.json")
    Call<AddFolderResponse> setProfileAlbum(@Field("token")String token, @Field("profile")String profile);


    /** Friend */
    /*My Friends*/
    @FormUrlEncoded
    @POST("api/my_friends.json")
    Call<MyFriendsResponse> myFriends(@Field("token")String token);
    /*Invited Friends*/
    @FormUrlEncoded
    @POST("api/invited_friends.json")
    Call<InvitedFriendResponse> invitedFriends(@Field("token")String token);
    /*Requested Friends*/
    @FormUrlEncoded
    @POST("api/request_received.json")
    Call<RequestedFriendResponse> requestedFriends(@Field("token")String token);
    /*Shortlisted Friends*/
    @FormUrlEncoded
    @POST("api/shortlisted.json")
    Call<ShortlistedFriendResponse> shortlistedFriends(@Field("token")String token);

    /*Add Friend*/
    @FormUrlEncoded
    @POST("api/add_friend.json")
    Call<AddFolderResponse> serviceAddAsFriend(@Field("token")String token, @Field("friend_user")String friend_user);
    /*Remove Friend*/
    @FormUrlEncoded
    @POST("api/remove_friend.json")
    Call<AddFolderResponse> serviceRemoveFriend(@Field("token")String token, @Field("friend_user")String friend_user);
    /*Cancel Friend*/
    @FormUrlEncoded
    @POST("api/cancel_friend_request.json")
    Call<AddFolderResponse> serviceCancelFriend(@Field("token")String token, @Field("friend_user")String friend_user);
    /*Shortlist Friend*/
    @FormUrlEncoded
    @POST("api/shortlisted_friend.json")
    Call<AddFolderResponse> serviceShortlistFriend(@Field("token")String token, @Field("friend_user")String friend_user);
    /*UnShortlist Friend*/
    @FormUrlEncoded
    @POST("api/unshortlisted_friend.json")
    Call<AddFolderResponse> serviceUnShortlistFriend(@Field("token")String token, @Field("friend_user")String friend_user);

    /** Contact Us */
    @POST("api/contact_us.json")
    Call<AddFolderResponse> contactUs(@Body ContactUsRequest request);

    /** Notifications*/
    @FormUrlEncoded
    @POST("api/all_notifications.json")
    Call<AllNotificationRespnse> allNotificationService(@Field("token") String token);

    /** Date To AGe*/
    @FormUrlEncoded
    @POST("api/date_to_age.json")
    Call<DateToAgeResponse> dateToAge(@Field("birth_date")String birth_date);

    //CHAT
    @POST("viewChatHistory")
    Call<SingleChatHistoryModel> singleChatPreviousHistory(@Body SingleChatPostRequest chatPostRequest);

    @POST("viewUserList")
    Call<ChatUserListModel> singleChatUserList(@Body SingleChatPostRequest chatPostRequest);


}
