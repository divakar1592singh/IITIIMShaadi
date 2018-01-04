package com.senzecit.iitiimshaadi.api_integration;


import com.senzecit.iitiimshaadi.model.api_response_model.forgot_password.ForgotPasswordResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.new_register.NewRegistrationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.EligibilityResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.FindCollegeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_institution.QuickRegInstitutionResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream.QuickRegStreamResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.ForgotPasswordRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.NewRegistrationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegEligibilityRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegFindCollegeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegInstitutionRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegStreamRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    @POST("/partition_url_here/")
    Call<FindCollegeResponse> quickRegFindCollege(@Body QuickRegFindCollegeRequest quickRegFindCollegeRequest);

    /** NEW USER REGISTER */
    @POST("/partition_url_here/")
    Call<NewRegistrationResponse> newUserRegistration(@Body NewRegistrationRequest newRegistrationRequest);

    /** Login */
    @POST("/partition_url_here/")
    Call<LoginResponse> loginInUser(@Body LoginRequest loginRequest);

    /** Forgot Password */
    @POST("/partition_url_here/")
    Call<ForgotPasswordResponse> forgotPasswordOfUser(@Body ForgotPasswordRequest forgotPasswordRequest);


}
