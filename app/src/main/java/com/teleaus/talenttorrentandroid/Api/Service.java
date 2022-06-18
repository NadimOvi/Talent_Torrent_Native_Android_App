package com.teleaus.talenttorrentandroid.Api;

import com.google.gson.JsonObject;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.AppliedExpertModel;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.JobStatusMainModel;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.createJobPostModel;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.draftedJobPostModel;
import com.teleaus.talenttorrentandroid.Fragments.TrainingFragments.DemoClass;
import com.teleaus.talenttorrentandroid.Fragments.TrainingFragments.TokenPassResponseModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.MainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobFilterModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobMainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.StaticModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningMainModel;
import com.teleaus.talenttorrentandroid.Model.ConnectionModel.StatusModel;
import com.teleaus.talenttorrentandroid.Model.EducationModel.EducationsModel;
import com.teleaus.talenttorrentandroid.Model.EmployementModel.EmployementModel;
import com.teleaus.talenttorrentandroid.Model.Login.LoginModel;
import com.teleaus.talenttorrentandroid.Model.Profile.ProfileMainModel;
import com.teleaus.talenttorrentandroid.Fragments.NotificationFragments.NotificationMainModel;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("v1/experts")
    Call<MainModel> getData();
    @GET("v1/experts")
    Call<MainModel> getExpertSearch(@Query("search") String search);

    @GET("v1/jobs")
    Call<JobMainModel> getJobData();
    @GET("v1/jobs")
    Call<JobMainModel> getJobSearch(@Query("search") String search);

    @GET("v1/filter/jobs")
    Call<JobFilterModel> getJobFilter(
            @Query("keyword") String status,
            @Query("range") String search);

    @GET("v1/trainings")
    Call<TrainningMainModel> getTrainningData();
    @GET("v1/trainings")
    Call<TrainningMainModel> getTrainningSearch(@Query("search") String search);

    @GET("v1/static/statistics")
    Call<StaticModel> getStaticData();

    @FormUrlEncoded
    @POST("v1/registration")
    Call<LoginModel> getRegistrationData(@Field("first_name") String first_name,
                                  @Field("last_name") String last_name,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("password_confirmation") String password_confirmation,
                                  @Field("type") String type);

    @FormUrlEncoded
    @POST("v1/verify/email")
    Call<JSONObject> postToken(@Field("token") String token);

    @FormUrlEncoded
    @POST("v1/login")
    Call<LoginModel> getLoginData(@Field("login") String email,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/social/login")
    Call<LoginModel> getSocialLoginData(@Field("first_name") String first_name,
                                  @Field("last_name") String last_name,
                                  @Field("email") String email,
                                  @Field("provider") String provider,
                                  @Field("provider_id") String provider_id);
    @FormUrlEncoded
    @POST("v1/social/login")
    Call<LoginModel> getGoogleLoginData(@Field("first_name") String first_name,
                                        @Field("last_name") String last_name,
                                        @Field("email") String email,
                                        @Field("provider") String provider,
                                        @Field("provider_id") String provider_id);

    @GET("v1/profile/education/histories")
    Call<EducationsModel> demo(@Header("Authorization") String authorization);

    @GET("v1/profile")
    Call<ProfileMainModel> getProfileData(@Header("Authorization") String authorization);

    @Multipart
    @POST("v1/update/cover")
    Call<ProfileMainModel> getProfileCover(@Header("Authorization") String authorization,
                                                        @Part MultipartBody.Part cover_photo);
    @Multipart
    @POST("v1/update/avatar")
    Call<ProfileMainModel> getProfileAvatar(@Header("Authorization") String authorization,
                                           @Part MultipartBody.Part cover_photo);

    @GET("v1/profile/education/histories")
    Call<EducationsModel> getEducationData(@Header("Authorization") String authorization);

    @GET("v1/profile/employment/histories")
    Call<EmployementModel> getEmployement(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("v1/job/apply/{uuid}")
    Call<JsonObject> applyJob(@Header("Authorization") String authorization,
                              @Path("uuid") String uuid,
                              @Field("budget") Integer budget,
                              @Field("message") String message);

    @FormUrlEncoded
    @POST("v1/job/create")
    Call<createJobPostModel> createJob(@Header("Authorization") String authorization,
                                       @Field("title") String title,
                                       @Field("type") String type,
                                       @Field("category_id") Integer category_id,
                                       @Field("tags") String tags,
                                       @Field("end_date") String end_date,
                                       @Field("budget") int budget,
                                       @Field("description") String description);

    @FormUrlEncoded
    @POST("v1/job/create")
    Call<draftedJobPostModel> draftedJob(@Header("Authorization") String authorization,
                            @Field("title") String title,
                            @Field("type") String type,
                            @Field("category_id") Integer category_id,
                            @Field("tags") String tags,
                            @Field("end_date") String end_date,
                            @Field("budget") int budget,
                            @Field("description") String description,
                            @Field("post_type") String post_type);

    @FormUrlEncoded
    @POST("v1/job/update/{uuid}")
    Call<JSONObject> updateJob(@Header("Authorization") String authorization,
                                         @Path("uuid") String uuid,
                                         @Field("title") String title,
                                         @Field("type") String type,
                                         @Field("category_id") Integer category_id,
                                         @Field("tags") String tags,
                                         @Field("end_date") String end_date,
                                         @Field("budget") int budget,
                                         @Field("description") String description);



    @FormUrlEncoded
    @POST("v1/connection/request/{userId}")
    Call<String> connectionRequest(@Header("Authorization") String authorization,
                                         @Path("userId") Integer title);


    @POST("v1/connection/status/{userId}")
    Call<StatusModel> connectionStatus(@Header("Authorization") String authorization,
                                       @Path("userId") Integer title);


    @POST("v1/create/intent")
    Call<TokenPassResponseModel> tokenPass(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("v1/trainings/register/{training}")
    Call<JSONObject> applyTrainingCard(@Header("Authorization") String authorization,
                                       @Path("training") Integer trainingId,
                                       @Field("date") String date,
                                       @Field("paymentMethod") String card,
                                       @Field("payment_method") String stripeToken,
                                       @Field("participant") String participant,
                                       @Field("address") String address,
                                       @Field("phone") String phone);
    @FormUrlEncoded
    @POST("v1/trainings/register/{training}")
    Call<DemoClass> applyTrainingCardCoupon(@Header("Authorization") String authorization,
                                            @Path("training") Integer trainingId,
                                            @Field("date") String date,
                                            @Field("paymentMethod") String card,
                                            @Field("payment_method") String stripeToken,
                                            @Field("participant") String budget,
                                            @Field("address") String address,
                                            @Field("phone") String phone,
                                            @Field("coupon") String coupon);

    @FormUrlEncoded
    @POST("v1/trainings/register/{training}")
    Call<StatusModel> applyTrainingEmail(@Header("Authorization") String authorization,
                                              @Path("training") Integer trainingId,
                                              @Field("date") String date,
                                              @Field("paymentMethod") String email,
                                              @Field("participant") Integer budget,
                                              @Field("address") String address,
                                              @Field("phone") Integer phone);
    @FormUrlEncoded
    @POST("v1/trainings/register/{training}")
    Call<StatusModel> applyTrainingEmailCoupon(@Header("Authorization") String authorization,
                                         @Path("training") Integer trainingId,
                                         @Field("date") String date,
                                         @Field("paymentMethod") String email,
                                         @Field("participant") Integer budget,
                                         @Field("address") String address,
                                         @Field("phone") Integer phone,
                                         @Field("coupon") String coupon);

    @FormUrlEncoded
    @POST("v1/send/reset/password/email")
    Call<JSONObject> resetEmail( @Field("email") String email);

    @FormUrlEncoded
    @POST("v1/reset/password")
    Call<JSONObject> checkReset( @Field("email") String email,
                             @Field("password") String password,
                             @Field("password_confirmation") String confirmPassword,
                             @Field("token") String token);

    @GET("v1/jobs/all/{status}")
    Call<JobStatusMainModel> jobStatus(@Header("Authorization") String authorization,
                                       @Path("status") String status);

    @FormUrlEncoded
    @POST("v1/update/username")
    Call<JSONObject> userNamePost (@Header("Authorization") String authorization,
                                 @Field("username") String email);

    @FormUrlEncoded
    @POST("v1/update")
    Call<JSONObject> userInfoUpdatePost (@Header("Authorization") String authorization,
                                   @Field("first_name") String first_name,
                                   @Field("last_name") String last_name,
                                   @Field("phone") String phone,
                                   @Field("country") String country,
                                   @Field("location") String location);

    @FormUrlEncoded
    @POST("v1/update/company")
    Call<JSONObject> userCompanyUpdatePost (@Header("Authorization") String authorization,
                                         @Field("company_name") String company_name,
                                         @Field("company_registration_number") String company_registration_number,
                                         @Field("business_type") String business_type,
                                         @Field("company_size") String company_size);

    @FormUrlEncoded
    @POST("v1/update/password")
    Call<JSONObject> userPassUpdatePost (@Header("Authorization") String authorization,
                                            @Field("old_password") String old_password,
                                            @Field("password") String password,
                                            @Field("password_confirmation") String password_confirmation);

    @GET("v1/client/{uuid}/applied/users")
    Call<AppliedExpertModel> jobAppliedCheck(@Header("Authorization") String authorization,
                                             @Path("uuid") String status);
    @FormUrlEncoded
    @POST("v1/client/{uuid}/hire/user")
    Call<JSONObject> expertHire(@Header("Authorization") String authorization,
                                             @Path("uuid") String status,
                                            @Field("confirmed_budget") int confirmed_budget,
                                            @Field("expert_id") int expert_id);

    @FormUrlEncoded
    @POST("v1/client/{uuid}/reject/user")
    Call<JSONObject> expertReject(@Header("Authorization") String authorization,
                                @Path("uuid") String status,
                                @Field("expert_id") int expert_id);

    @POST("v1/messages")
    Call<NotificationMainModel> getNotification(@Header("Authorization") String authorization);

    @POST("v1/messages/{notificationId}/read")
    Call<JSONObject> getReadNotification(@Header("Authorization") String authorization,
                                                        @Path("notificationId") int notificationId);

    @POST("v1/messages/{notificationId}/archived")
    Call<JSONObject> getArchivedNotification(@Header("Authorization") String authorization,
                                                        @Path("notificationId") int notificationId);

    @FormUrlEncoded
    @POST("v1/update/work/experience")
    Call<JSONObject> updateJobExperience(@Header("Authorization") String authorization,
                                             @Field("qualification") String qualification,
                                             @Field("experience") String experience,
                                             @Field("skills") String skills,
                                             @Field("cover_letter") String cover_letter);
    @FormUrlEncoded
    @POST("v1/update/work/experience")
    Call<JSONObject> updateJobExperienceWithNumber(@Header("Authorization") String authorization,
                                         @Field("qualification") String qualification,
                                         @Field("experience") String experience,
                                         @Field("skills") String skills,
                                         @Field("cover_letter") String cover_letter,
                                         @Field("business_number") String business_number);

    /*@FormUrlEncoded
    @POST("v1/profile/education/create")
    Call<JSONObject> createEducation(@Header("Authorization") String authorization,
                                       @Field("institute") String institute,
                                       @Field("degree") String degree,
                                       @Field("field_of_study") String field_of_study,
                                       @Field("start_year") String start_year,
                                       @Field("end_year") String end_year,
                                       @Field("grade") String grade,
                                       @Field("description") String description);*/
    @FormUrlEncoded
    @POST("v1/profile/education/create")
    Call<JSONObject> createEducationWithActivities(@Header("Authorization") String authorization,
                                     @Field("institute") String institute,
                                     @Field("degree") String degree,
                                     @Field("field_of_study") String field_of_study,
                                     @Field("start_year") String start_year,
                                     @Field("end_year") String end_year,
                                     @Field("grade") String grade,
                                     @Field("activities") String activities,
                                     @Field("description") String description);





}
