package com.khalej.mishwary.model;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface apiinterface_home {


    @GET("maishwary/api/all_slider")
    Call<List<contact_annonce>> getcontacts_annonce__();

    @GET("maishwary/api/all_categories")
    Call<List<contact_category>> getcontacts_annonce();

    @GET("maishwary/api/coupons")
    Call<List<Copoun>> getcontacts_copuons();

    @FormUrlEncoded
    @POST("maishwary/api/filter_by_cat")
    Call<List<contact_sub_category>> getsubCategory(@Field("category_id") int category_id);

    @FormUrlEncoded
    @POST("maishwary/api/search")
    Call<List<contact_sub_category>> getsubCategorySearch(@Field("key_word") String text);

    @FormUrlEncoded
    @POST("maishwary/api/update_profile")
    Call<Edit> getcontacts_updateProfileWithoutImage(@Field("name") String name, @Field("password") String password, @Field("phone") String phone
            , @Field("country_id") int country_id, @Field("user_id") int user_id);


    @FormUrlEncoded
    @POST("maishwary/api/my_notification")
    Call<List<notificationData>>getcontacts_Notification(@Field("user_id") int user_id);
    @FormUrlEncoded
    @POST("maishwary/api/login")
    Call<contact_userinfo> getcontacts_login(@Field("kayWord") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("maishwary/api/contact_us")
    Call<ResponseBody> CallUs(@Field("name") String name, @Field("email") String address,
                              @Field("subject") String subject, @Field("message") String message);

    @FormUrlEncoded
    @POST("maishwary/api/canRest")
    Call<Reset>getcontacts_ResetPassword(@Field("kayWord") String kayWord);






    @FormUrlEncoded
    @POST("maishwary/api/canceling_order")
    Call<ResponseBody> getcontacts_CancelOrder(@Field("order_id") int order_id, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("maishwary/api/rate")
    Call<ResponseBody> getcontacts_AddRate(@Field("to_id") int to_id, @Field("form_id") int form_id, @Field("rate") Float rate,
                                           @Field("des") String des);


    @FormUrlEncoded
    @POST("maishwary/api/delete_notification")
    Call<ResponseBody> getcontacts_CancelNotification(@Field("notification_id") int order_id, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("maishwary/api/accept_order")
    Call<ResponseBody> getcontacts_AcceptOrder(@Field("order_id") int order_id, @Field("user_id") int user_id);


    @FormUrlEncoded
    @POST("maishwary/api/accept_out_order")
    Call<ResponseBody> getcontacts_AcceptOutOrder(@Field("order_id") int order_id, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("maishwary/api/finshing_order")
    Call<ResponseBody> getcontacts_FinishOrder(@Field("order_id") int order_id, @Field("user_id") int user_id);




    @FormUrlEncoded
    @POST("maishwary/api/add_subscribs")
    Call<ResponseBody> getcontacts_RemoveSubScribe(@Field("product_id") int order_id, @Field("user_id") int user_id);
 @FormUrlEncoded
    @POST("maishwary/api/add_order_to_representatives")
    Call<ResponseBody> getSpecialOrder(@Field("user_id") int id, @Field("des") String des, @Field("name") String name,
                                       @Field("amount") int amount);

    @FormUrlEncoded
    @POST("maishwary/api/add_order_to_representatives")
    Call<ResponseBody> getSpecialOrderForMandop(@Field("user_id") int id, @Field("representative_id") int mandop_id, @Field("name") String name,
                                                @Field("amount") int amount, @Field("des") String des);

    @GET("maishwary/api/all_representatives")
    Call<List<contact_userinfo>> get_all_mandops();

    @GET("maishwary/api/about_en")
    Call<AboutUs> AboutUS_en();
    @GET("maishwary/api/about_ar")
    Call<AboutUs> AboutUS_ar();

    @GET("maishwary/api/condtions_en")
    Call<AboutUs> Conditoins_en();
    @GET("maishwary/api/condtions_ar")
    Call<AboutUs> Conditoins_ar();

    @FormUrlEncoded
    @POST("maishwary/api/add_order")
    Call<ResponseBody> content_addOrder(@Field("user_id") int user_id, @Field("latitude") double latitude, @Field("longitude") double longitude,
                                        @Field("category_id") int category_id, @Field("address") String address, @Field("name") String name,
                                        @Field("representative_id") int fani_id, @Field("total") double total);

    @FormUrlEncoded
    @POST("maishwary/api/register")
    Call<contact_userinfo> getcontacts_newaccount(@Field("name") String name, @Field("password") String password, @Field("email") String address,
                                                  @Field("phone") String phone, @Field("latitude") double country, @Field("longitude") double lng,
                                                  @Field("phone_code") String phone_code, @Field("is_agree") int is_agree, @Field("type") int type,
                                                  @Field("country_id") int country_id, @Field("city_id") int city_id);

    @Multipart
    @POST("maishwary/api/register")
    Call<contact_userinfo>getcontacts_newaccountFani(@Part MultipartBody.Part image, @Part("name") RequestBody name,
                                                     @Part("phone") RequestBody phone, @Part("email") RequestBody email,
                                                     @Part("password") RequestBody password, @Part("phone_code") RequestBody phone_code,
                                                     @Part("type") RequestBody type, @Part("country_id") RequestBody country_id,
                                                     @Part("city_id") RequestBody city_id
            , @Part("latitude") RequestBody lat, @Part("longitude") RequestBody lng,
                                                     @Part("category_id[0]") RequestBody categoryID
    );

    @Multipart
    @POST("maishwary/api/add_payment")
    Call<ResponseBody> getcontacts_addbankImage(@Part MultipartBody.Part image, @Part("user_id") RequestBody user_id, @Part("bank_id") RequestBody bank_id,
                                                @Part("amount") RequestBody amount
    );

    @FormUrlEncoded
    @POST("maishwary/api/my_orders")
    Call<List<Myorder>> getcontacts_MyOrders(@Field("user_id") int id, @Field("type") int type);

    @GET("enginer/api/countries")
    Call<List<contact_country>> Countrys();

//
//    @FormUrlEncoded
//    @POST("enginer/api/get_technical")
//    Call<List<technical>> getcontacts_ListFanis(@Field("category_id") int id, @Field("latitude") double latitude, @Field("longitude") double longitude);

    @Multipart
    @POST("maishwary/api/update_profile")
    Call<updateImage> getcontacts_updateProfile(@Part MultipartBody.Part image, @Part("user_id") RequestBody user_id);


    @FormUrlEncoded
    @POST("maishwary/api/add_orders")
    Call<ResponseBody> add_order(@Field("user_id")int user_id,@Field("client_longitude") double client_longitude,
                                 @Field("client_latitude") double client_latitude, @Field("client_address") String client_address
                                 ,@Field("des")String des,
                                 @Field("place_longitude") double place_longitude,@Field("place__latitude") double place__latitude,
                                 @Field("coupon")String coupon);

}

