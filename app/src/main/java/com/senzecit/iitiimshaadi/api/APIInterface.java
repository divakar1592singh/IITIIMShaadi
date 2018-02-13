package com.senzecit.iitiimshaadi.api;

import com.senzecit.iitiimshaadi.model.api_response_model.all_album.AllAlbumResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.rename_folder.RenameFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.forgot_password.ForgotPasswordResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.InvitedFriendResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.MyFriendsResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.requested_friend.RequestedFriendResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.shortlisted.ShortlistedFriendResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.general_setting.GeneralSettingResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.new_register.NewRegistrationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.EligibilityResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.find_college.FindCollegeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_institution.QuickRegInstitutionResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream.QuickRegStreamResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
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
import com.senzecit.iitiimshaadi.model.common.country.CountryListResponse;
import com.senzecit.iitiimshaadi.model.common.state.StateListResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ravi on 13/12/17.
 */

public interface APIInterface {
/*

    @POST("/user/registration_user")
    Call<UserRegistrationResponse> getUserRegisterResponse(@Body UserRegistrationRequest userRegistrationRequest);

    @GET("user/profile/{user_id}")
    Call<EditInfoDataFromServerModel> getEditInfoProfile(@Path("user_id") String user_id);

    @POST("user/update_user")
    Call<EditInfoUpdateResponse> updateUserProfile(@Body EditInfoUpdateRequest editInfoUpdateRequest);

    @POST("user/search_people")
    Call<PeopleResponse> searchPeopleList(@Body PeopleRequest peopleRequest);

    @POST("user/all_posts")
    Call<AllPostResponseModel> allPostList(@Body PeopleRequest peopleRequest);


    @POST("user/add_post")
    Call<AddPostResponse> addPost(@Body AddPostRequest addPostRequest);

    @GET("user/topiclist")
    Call<TopicListModelResponse> getTopicList();

    @GET("user/all_list/Condition")
    Call<ConditionListModelResponse> getConditionList();

    @GET("user/all_list/Symptoms")
    Call<SymptomsListModelResponse> getSymptomsList();

    @GET("user/all_list/Treatment")
    Call<TreatmentListModelResponse> getTreatmentList();

    @POST("user/view_particular_post")
    Call<CommentListClass> commentList(@Body CommentListRequest commentListRequest);

    @POST("user/comment_post")
    Call<CommentResponse> addComment(@Body ParticularCommentRequest particularCommentRequest);

    @Multipart
    @POST("media/uploadFile/{user_id}")
    Call<ImageUploadModelClass> uploadImageFile(@Part MultipartBody.Part file, @Part("imgFile") RequestBody name, @Path("user_id") String user_id);

    @POST("user/delete_account")
    Call<DeleteAccountResponse> deleteUserAccount(@Body DeleteAccountRequest deleteAccountRequest);

*/
    /** QUICK REGISTER */
    @POST("api/checkEligibilty.json")
    Call<QuickRegStreamResponse> fetchStreamData(@Body QuickRegStreamRequest quickRegStreamRequest);
    @POST("/partition_url_here/")
    Call<QuickRegInstitutionResponse> fetchInstitutionData(@Body QuickRegInstitutionRequest quickRegInstitutionRequest);
    @POST("/partition_url_here/")
    Call<EligibilityResponse> quickRegisterUser(@Body QuickRegEligibilityRequest quickRegEligibilityRequest);

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
    Call<EmailVerificationResponse> emailVerification(@Body EmailVerificationRequest emailVerificationRequest);

/*
    @POST("api/email_verification.json")
    Call<ForgotPasswordResponse> emailVerification(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("api/email_verification.json")
    Call<ForgotPasswordResponse> emailVerification(@Body ForgotPasswordRequest forgotPasswordRequest);
*/


    @Multipart
    @POST("api/id_upload.json")
    Call<IdVerificationResponse> idVerification(@Query("token") String token, @Part MultipartBody.Part file, @Part("id_proof") RequestBody id_proof);

    @FormUrlEncoded
    @POST("api/country.json")
    Call<CountryListResponse> countryList(@Field("token")String token);

    @FormUrlEncoded
    @POST("api/state.json")
    Call<StateListResponse> stateList(@Field("token")String token);

    @FormUrlEncoded
    @POST("api/my_profile.json")
    Call<MyProfileResponse> myProfileData(@Field("token")String token);

    @FormUrlEncoded
    @POST("api/other_profile.json")
    Call<MyProfileResponse> otherProfileData(@Field("token")String token, @Field("user_id")String userId);

    /** Advance Search - Subs */
    @POST("api/advance_search.json")
    Call<SubsAdvanceSearchResponse> advanceSearch(@Body SubsAdvanceSearchRequest advanceSearchRequest);

    /** ID Search - Paid Subs */
    @FormUrlEncoded
    @POST("api/search_by_id.json")
    Call<SubsAdvanceSearchResponse> idSearchPaid(@Field("token")String token, @Field("user_id")String userId);

    /** Keyword Search - Paid Subs */
    @FormUrlEncoded
    @POST("api/search_by_keyword.json")
    Call<SubsAdvanceSearchResponse> keywordSearchPaid(@Field("token")String token, @Field("keyword")String keyword);

    /** Advance Search - Paid Subs */
    @POST("api/advance_search.json")
    Call<SubsAdvanceSearchResponse> advanceSearchPaid(@Body PaidSubsAdvanceSearchRequest advanceSearchRequest);

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
    Call<BasicProfileResponse> sendBasicProfile(@Body BasicProfileRequest request);
    /*2*/
    @POST("api/religious_background.json")
    Call<ReligiousBackgroundResponse> sendReligiousBackground(@Body ReligiousBackgroundRequest request);
    /*3*/
    @POST("api/contact_details.json")
    Call<ContactDetailsResponse> sendContactDetails(@Body ContactDetailsRequest request);
    /*4*/
    @POST("api/family_details.json")
    Call<FamilyDetailResponse> sendFamilyDetails(@Body FamilyDetailRequest request);
    /*5*/
    @POST("api/education_career.json")
    Call<EducationCareerResponse> sendEducationCareer(@Body EducationCareerRequest request);
    /*6*/
    @POST("api/about_me.json")
    Call<AboutMeResponse> sendAboutMe(@Body AboutMeRequest request);
    /**Partner*/
    /*7*/
    @POST("api/partner_education_career.json")
    Call<ParnerBasicProfileResponse> sendPartnerBasicProfile(@Body ParnerBasicProfileRequest request);
    /*8*/
    @POST("api/partner_religion_country.json")
    Call<PtrReligionCountryResponse> sendPartnerReligionCountry(@Body PtrReligionCountryRequest request);
    /*9*/
    @POST("api/partner_education_career.json")
    Call<PtrEduCareerResponse> sendPartnerEduCareer(@Body PtrEduCareerRequest request);
    /*10*/
    @POST("api/choiceof_partner.json")
    Call<ChoiceOfGroomResponse> sendPartnerGroom(@Body ChoiceOfGroomRequest request);

    /** General Setting */
    @POST("api/general_settings.json")
    Call<GeneralSettingResponse> changeGeneralSetting(@Body GeneralSettingRequest request);
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

    /** NEW USER REGISTER */
    @POST("api/contact_us.json")
    Call<AddFolderResponse> contactUs(@Body ContactUsRequest request);


}
