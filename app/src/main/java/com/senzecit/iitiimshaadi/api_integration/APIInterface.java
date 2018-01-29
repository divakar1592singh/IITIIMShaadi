package com.senzecit.iitiimshaadi.api_integration;


import com.senzecit.iitiimshaadi.model.api_response_model.forgot_password.ForgotPasswordResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.new_register.NewRegistrationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.EligibilityResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.find_college.FindCollegeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_institution.QuickRegInstitutionResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream.QuickRegStreamResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.email_verification.EmailVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification.IdVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.ForgotPasswordRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.NewRegistrationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegEligibilityRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegFindCollegeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegInstitutionRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegStreamRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.email_verification.EmailVerificationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.id_verification.IdVerificationRequest;
import com.senzecit.iitiimshaadi.model.common.country.CountryListResponse;
import com.senzecit.iitiimshaadi.model.common.state.StateListResponse;
import com.senzecit.iitiimshaadi.utils.Constants;

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
    @POST("/partition_url_here/")
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

    @POST("api/id_upload.json")
    Call<IdVerificationResponse> idVerificationss(@Body IdVerificationRequest idVerificationRequest);

    @Multipart
    @POST("api/id_upload.json")
    Call<IdVerificationResponse> idVerification(@Part("token")String token, @Part MultipartBody.Part file, @Part("id_proof") RequestBody name);

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


}
