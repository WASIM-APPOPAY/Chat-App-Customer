package com.stuffrs.newappopay.stuffers_business.api;

import com.stuffrs.newappopay.stuffers_business.fragments.bottom.chatnotification.MyResponse;
import com.stuffrs.newappopay.stuffers_business.fragments.bottom.chatnotification.Sender;
import com.stuffrs.newappopay.stuffers_business.models.Country.CountryCodeResponse;
import com.stuffrs.newappopay.stuffers_business.models.Product.ProductResponse;
import com.stuffrs.newappopay.stuffers_business.models.bank.BankCurrencyResponse;
import com.stuffrs.newappopay.stuffers_business.models.bank.BankNameResponse;
import com.stuffrs.newappopay.stuffers_business.models.bank.account.BankAccResponse;
import com.stuffrs.newappopay.stuffers_business.models.input.MobileRechargeModel;
import com.stuffrs.newappopay.stuffers_business.models.lunex_giftcard.GiftProductList;
import com.stuffrs.newappopay.stuffers_business.models.output.AddProductToCartModel;
import com.stuffrs.newappopay.stuffers_business.models.output.AllChildCategoryOutput;
import com.stuffrs.newappopay.stuffers_business.models.output.AppointmentBookingInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.AppointmentBookingOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.AuthorizationResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.BookOrderInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.BookOrderModel;
import com.stuffrs.newappopay.stuffers_business.models.output.CategoryListModel;
import com.stuffrs.newappopay.stuffers_business.models.output.CommonOutput;
import com.stuffrs.newappopay.stuffers_business.models.output.CurrencyResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.DetailProductInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.EditUserAccountInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.FlashDealsProductsOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.ForgotPasswordOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetAccountDetailsOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetAllFashionOffersModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetAllFlashDealsModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetAppSettings;
import com.stuffrs.newappopay.stuffers_business.models.output.GetBrandsOutput;
import com.stuffrs.newappopay.stuffers_business.models.output.GetCategoryDetailOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetHomepageSliderOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetOnlineCartOutput;
import com.stuffrs.newappopay.stuffers_business.models.output.GetSellerModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetShipmentDetailsOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetTopOfTheDayOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetTopShoppingOffersModel;
import com.stuffrs.newappopay.stuffers_business.models.output.GetWalletTransactionsOutput;
import com.stuffrs.newappopay.stuffers_business.models.output.IsSellerExist;
import com.stuffrs.newappopay.stuffers_business.models.output.MappingResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.NewServiceListModel;
import com.stuffrs.newappopay.stuffers_business.models.output.NewServiceListOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.NormalResponseBody;
import com.stuffrs.newappopay.stuffers_business.models.output.PostSellerReviewInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.PostSellerReviewOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.ProductOrderNewModel;
import com.stuffrs.newappopay.stuffers_business.models.output.ProductOrderNewOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.ProductReviewListInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.ProductReviewListOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.ProductsListModel;
import com.stuffrs.newappopay.stuffers_business.models.output.RateProductInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.SaveCardResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.SearchProductOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.SellerReviewListOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.ServiceListModel;
import com.stuffrs.newappopay.stuffers_business.models.output.TrackDeliveryInputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.TrackDeliveryOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.TransactionListResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.UpdateCartOutput;
import com.stuffrs.newappopay.stuffers_business.models.output.UserProfileOutput;
import com.stuffrs.newappopay.stuffers_business.models.output.YourAppointmentOutputModel;
import com.stuffrs.newappopay.stuffers_business.models.output.YourOrderOutputModel;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface MainAPIInterface<R extends Retrofit> {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.UNION_PAY_JWS)
    Call<JsonObject> getJWSToken(@Body JsonObject param);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.UNION_PAY_JWS)
    Call<JsonObject> getJWSToken(@Body JsonObject param, @Header("UPI-JWS") String xAccessToken);


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_UPLOAD_FILE)
    Call<JsonObject> uploadFileForOpenAccount(
            @Part MultipartBody.Part files,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part("fileName") RequestBody fileName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_GIFTCARD_LIST)
    Call<GiftProductList> getAllGiftCardList(@Body JsonObject param);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_SENT_LUNEX_GIFT_CARD)
    Call<JsonObject> postSentLunexGiftCard(@Body JsonObject body, @Header("Authorization") String xAccessToken);


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_FORGOT_PASSWORD)
    Call<JsonObject> postForgotPassword(@Body JsonObject body);

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_FINDUSER)
    Call<JsonObject> getFindUserSecurityQuestion(@Path("mobilenumber") String mobile, @Path("areacode") String areacode1);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_ADD_WALLET_BY_CARD)
    Call<JsonObject> postAddFundToWallet(@Body JsonObject body, @Header("Authorization") String xAccessToken);


    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_CARD_FUND_COMMISSION)
    Call<JsonObject> getCardFundCommission(@Header("Authorization") String xAccessToken);


    /*@Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAwXpGhFM:APA91bG3nRW1fJKnmOdwfd2jlFhCDoYVbH9XMQ_pmFpBlohXsNYK3cnSea_nj_5ns3W81Eb6Fjo5OW1QlvJYfyDGOEGMXz2bYUd-Xk4jYVeZeKOjwZWFdLZU5VTEUBkNtv8DwHpDZwtn"
            }
    )*/
    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_BANK_FUND_COMMISSION)
    Call<JsonObject> getBankFundCommission(@Header("Authorization") String xAccessToken);


    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAwXpGhFM:APA91bG3nRW1fJKnmOdwfd2jlFhCDoYVbH9XMQ_pmFpBlohXsNYK3cnSea_nj_5ns3W81Eb6Fjo5OW1QlvJYfyDGOEGMXz2bYUd-Xk4jYVeZeKOjwZWFdLZU5VTEUBkNtv8DwHpDZwtn"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_UPDATE_PASSWORD)
    Call<JsonObject> postUpdatePassword(@Body JsonObject requestParameter, @Header("Authorization") String xAccessToken);

    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ACCOUNT_TYPE)
    Call<BankAccResponse> getBankAccountsTypes(@Header("Authorization") String xAccessToken);

    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_BANK_CURRENCY)
    Call<BankCurrencyResponse> getBankCurrency(@Path("id") int bankid,
                                               @Header("Authorization") String xAccessToken);

    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_BANK_NAMES)
    Call<BankNameResponse> getBankNameById(@Path("id") int id,
                                           @Header("Authorization") String xAccessToken);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_TRANSACTION_DETAILS)
    Call<TransactionListResponse> postUserTransactionList(@Body JsonObject body,
                                                          @Header("Authorization") String xAccessToken);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Access-Control-Allow-Origin: appopay.com",

    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_PROFILE_MERCHANT)
    Call<JsonObject> getProfileMerchantDetails(
            @Path("mobileno") String mobileNumber,
            @Path("areacode") String areacode,
            @Header("Authorization") String xAccessToken

    );

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_RECHARGE_TOPUP)
    Call<JsonObject> postRechargeTopup(@Body JsonObject body, @Header("Authorization") String xAccessToken);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_TRANSFER_FUND)
    Call<JsonObject> postTransferFund(@Body JsonObject body, @Header("Authorization") String xAccessToken);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_MERCHANT_PAYMENT)
    Call<JsonObject> postMerchantPayment(@Body JsonObject body, @Header("Authorization") String xAccessToken);


    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_COMMISSIONS)
    Call<JsonObject> getAllTypeCommissions(@Header("Authorization") String xAccessToken);

    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_COMMISSIONS_MERCHANT)
    Call<JsonObject> getAllTypeMerchantCommissions(@Header("Authorization") String xAccessToken);

    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_COMMISSION_TRANSFER)
    Call<JsonObject> getAllTypeTransferCommissions(@Header("Authorization") String xAccessToken);


    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_CONVERSIONS)
    Call<JsonObject> getCurrencyConversions(@Path("destinationCurrency") String desCurrency, @Header("Authorization") String xAccessToken);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_PRODUCT)
    Call<ProductResponse> getProductResponse(@Body JsonObject body);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_VAULTBYID)
    Call<JsonObject> getVaultById(@Body JsonObject body);

    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_CARDS)
    Call<SaveCardResponse> getUserSaveCards(@Path("userid") String userid,
                                            @Header("Authorization") String xAccessToken);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_CHECK_VERSATEDCARD)
    Call<JsonObject> postCheckVersateCard(@Body JsonObject body);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_SAVE_CARD_TYPE)
    Call<JsonObject> postSaveCardType(@Body JsonObject body, @Header("Authorization") String xAccessToken);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Access-Control-Allow-Origin: appopay.com"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_CREATE_ACCOUNT)
    Call<JsonObject> postCreateAccount(@Body JsonObject body, @Header("Authorization") String xAccessToken);


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_CURRENCY)
    Call<CurrencyResponse> getCurrencyResponse();


    /*@Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })*/
    @Headers({
            "Accept: application/json",
            "Access-Control-Allow-Origin: appopay.com"


    })

    @PUT(com.stuffrs.newappopay.stuffers_business.api.Constants.PUT_UPDATE_PROFILE)
    Call<JsonObject> putUpdateUserProfile(@Body JsonObject body, @Header("Authorization") String xAccessToken);


   @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_UPDATE_PROFILE)
    Call<JsonObject> postUpdateUserProfile(@Body JsonObject body, @Header("Authorization") String xAccessToken);



    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Access-Control-Allow-Origin: appopay.com"
    })

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_PROFILE)
    Call<JsonObject> getProfileDetails(
            @Path("mobileno") long mobileNumber,
            @Path("areacode") int areacode,
            @Header("Authorization") String xAccessToken
    );

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.CREATE_USER)
    Call<JsonObject> postCreateUserAccount(@Body JsonObject body);

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.CHECK_EMIAL_ID)
    Call<JsonObject> getEmailIdStatus(@Path("user_email") String userEmail);

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.CHECK_EMIAL_ID_NEW)
    Call<JsonObject> getEmailStatusNew(@Body RequestBody body);


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.CHECK_MOBILE_NUMBER)
    Call<JsonObject> getMobileNUmberStatus(@Path("phone_number") String phNoWithCode);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_VERIFY_OTP)
    Call<String> getVerificationStatus(@Body JsonObject body);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.POST_GENERATE_OTP)
    Call<String> getOtpforUserVerificaiton(@Body JsonObject body);


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_COUNTRY_CODE)
    Call<CountryCodeResponse> getCountryCode();

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_MAPPING)
    Call<MappingResponse> getMapping(
            @Path("newnumber") String newnumber
    );

    @Headers({
            "Access-Control-Allow-Origin: appopay.com"
    })
    @FormUrlEncoded
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_TOKEN)
    Call<AuthorizationResponse> getAuthorization(
            @Header("Authorization") String xAccessToken,
            @Field("username") String email,
            @Field("password") String name,
            @Field("grant_type") String school
    );


    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Access-Control-Allow-Origin: appopay.com"

    })
    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_LOGIN_DETAILS)
    Call<JsonObject> getLoginDetails(
            @Path("mobileno") long mobileNumber,
            @Path("areacode") long areacode,
            @Header("Authorization") String xAccessToken

    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.USER_MOBILE_REGISTRATION)
    Call<UserProfileOutput> registerUserMobile(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part country_code,
            @Part MultipartBody.Part mobile
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.VERIFY_USER_MOBILE)
    Call<NormalResponseBody> verifyUserMobile(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part mobile,
            @Part MultipartBody.Part otp
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.USER_SIGNUP)
    Call<UserProfileOutput> userSignup(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part email,
            @Part MultipartBody.Part phone_no,
            @Part MultipartBody.Part username,
            @Part MultipartBody.Part password
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.USER_LOGIN)
    Call<UserProfileOutput> userLogin(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part mobile,
            @Part MultipartBody.Part password
    );


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_CATEGORIES)
    Call<CategoryListModel> getCategories(@Header("X-API-KEY") String xAccessToken);

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_SUB_CAT)
    Call<CategoryListModel> getSubCategories(
            @Header("X-API-KEY") String xAccessToken,
            @Path("cat_id") String cat_id
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_SUBCAT_PRODUCTS)
    Call<ProductsListModel> getAllProducts(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part subcat_id
    );


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_SINGLE_PRODUCT_DETAIL)
    Call<ProductsListModel> getProductDetail(
            @Header("X-API-KEY") String xAccessToken,
            @Body DetailProductInputModel detailProductInputModel
    );


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_SERVICE_CATEGORIES)
    Call<ServiceListModel> getServiceCategories(@Header("X-API-KEY") String xAccessToken);


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_NEARBY_SELLERS)
    Call<GetSellerModel> getAllNearbySellers(
            @Header("X-API-KEY") String xAccessToken
    );


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_TRENDING_PRODUCTS)
    Call<ProductsListModel> getAllTrendingProducts(
            @Header("X-API-KEY") String xAccessToken
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_SELLER_SERVICES)
    Call<NewServiceListModel> getAllServices(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part seller_id
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_YOUR_ORDERS)
    Call<YourOrderOutputModel> getAllYourOrders(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id
    );


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.BOOK_ORDER)
    Call<BookOrderModel> bookOrder(
            @Header("X-API-KEY") String xAccessToken,
            @Body BookOrderInputModel bookOrderInputModell
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_YOUR_APPOINTMENTS)
    Call<YourAppointmentOutputModel> getAllYourAppointments(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id
    );

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.BOOK_APPOINTMENT)
    Call<AppointmentBookingOutputModel> bookAppointment(
            @Header("X-API-KEY") String xAccessToken,
            @Body AppointmentBookingInputModel appointmentBookingInputModel

    );


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.ADD_PRODUCT_TO_CART)
    Call<IsSellerExist> addProductToCart(
            @Header("X-API-KEY") String xAccessToken,
            @Body AddProductToCartModel addProductToCartModel

    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ONLINE_CART_LIST)
    Call<GetOnlineCartOutput> getOnlineCartList(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.UPDATE_INCREASE_CART)
    Call<UpdateCartOutput> updateCartIncrease(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id,
            @Part MultipartBody.Part seller_id,
            @Part MultipartBody.Part product_id
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.UPDATE_DECREASE_CART)
    Call<UpdateCartOutput> updateCartDecrease(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id,
            @Part MultipartBody.Part seller_id,
            @Part MultipartBody.Part product_id
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.UPDATE_DELETE_CART)
    Call<IsSellerExist> updateCartDelete(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id,
            @Part MultipartBody.Part seller_id,
            @Part MultipartBody.Part product_id
    );

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.NEW_ORDER_BOOKING)
    Call<ProductOrderNewOutputModel> newOrderBookingRequest(
            @Header("X-API-KEY") String xAccessToken,
            @Body ProductOrderNewModel productOrderNewModel


    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_CATEGORY_DETAIL)
    Call<GetCategoryDetailOutputModel> getCategoryDetail(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part category_id
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_CHILD_CATEGORIES)
    Call<AllChildCategoryOutput> getAllChildCategories(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part category_id
    );


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_BRANDS)
    Call<GetBrandsOutput> getAllBrands(
            @Header("X-API-KEY") String xAccessToken
    );

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_TOP_BRANDS)
    Call<GetBrandsOutput> getAllTopBrands(
            @Header("X-API-KEY") String xAccessToken
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.SEARCH_ALL_PRODUCTS)
    Call<SearchProductOutputModel> searchAllProducts(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part search_query
    );

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.RATE_REVIEW_SELLER_URL)
    Call<PostSellerReviewOutputModel> postSellerReview(
            @Header("X-API-KEY") String xAccessToken,
            @Body PostSellerReviewInputModel postSellerReviewInputModel


    );


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.ADD_PRODUCT_REVIEW)
    Call<PostSellerReviewOutputModel> postProductReview(
            @Header("X-API-KEY") String xAccessToken,
            @Body RateProductInputModel rateProductInputModel
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_SELLER_REVIEW_LIST)
    Call<SellerReviewListOutputModel> getSellerReviewList(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part seller_id
    );


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_PRODUCT_REVIEW)
    Call<ProductReviewListOutputModel> getProductReviewList(
            @Header("X-API-KEY") String xAccessToken,
            @Body ProductReviewListInputModel productReviewListInputModel
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ACCOUNT_DETAILS)
    Call<GetAccountDetailsOutputModel> getUserAccountDetails(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id
    );

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.EDIT_USER_ACCOUNT)
    Call<PostSellerReviewOutputModel> postEditUserAccount(
            @Header("X-API-KEY") String xAccessToken,
            @Body EditUserAccountInputModel editUserAccountInputModel
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.FORGOT_PASSWORD_REQUEST)
    Call<ForgotPasswordOutputModel> forgotPasswordRequest(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part email
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.SEND_PASSWORD_OTP)
    Call<ForgotPasswordOutputModel> sendPasswordChangeOtp(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part user_id,
            @Part MultipartBody.Part email_id
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.CHANGE_PASSWORD_URL)
    Call<ForgotPasswordOutputModel> changePasswordRequest(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part user_id,
            @Part MultipartBody.Part user_old_password,
            @Part MultipartBody.Part user_new_password,
            @Part MultipartBody.Part otp
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_SERVICES_BY_CATEGORY)
    Call<NewServiceListOutputModel> getAllServicesByCategory(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part category_id
    );

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_HOMEPAGE_SLIDER)
    Call<GetHomepageSliderOutputModel> getHomePageSlider(
            @Header("X-API-KEY") String xAccessToken
    );

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_HOMEPAGE_SLIDER2)
    Call<GetHomepageSliderOutputModel> getHomePageSlider2(
            @Header("X-API-KEY") String xAccessToken
    );

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_SHIPMENT_DETAILS)
    Call<GetShipmentDetailsOutputModel> getShipmentDetails(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part order_id,
            @Part MultipartBody.Part product_id

    );

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_APP_SETTINGS)
    Call<GetAppSettings> getAppSettingsRequest(
            @Header("X-API-KEY") String xAccessToken
    );


    //Get All Flash Deals


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_FLASH_DEALS)
    Call<GetAllFlashDealsModel> getAllFlashDeals(
            @Header("X-API-KEY") String xAccessToken

    );

    //Get All Flash Deal Products


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_FLASH_DEAL_PRODUCTS)
    Call<FlashDealsProductsOutputModel> getAllFlashDealProducts(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part products

    );

    //Get All Fashion Offers

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_FASHION_OFFERS)
    Call<GetAllFashionOffersModel> getAllFationOffers(
            @Header("X-API-KEY") String xAccessToken

    );


    // GET TOP OF THE DAY

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_TOP_OF_THE_DAY)
    Call<GetTopOfTheDayOutputModel> getAllTopOfTheDay(
            @Header("X-API-KEY") String xAccessToken

    );


    //Get Top Shopping Offers

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_TOP_SHOPPING_OFFERS)
    Call<GetTopShoppingOffersModel> getTopShoppingOffers(
            @Header("X-API-KEY") String xAccessToken

    );

    //Get Top Shopping Offers

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_PRODUCTS_FROM_CHILD_CATEGORY)
    Call<ProductsListModel> getAllProductsFromChildCategory(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part child_category_id
    );

    //GET PRODUCTS BY BRAND

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_PRODUCTS_FROM_BRAND)
    Call<ProductsListModel> getAllProductsFromBrand(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part brand_id
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_PRODUCTS_FROM_CHILD_CATEGORY_LIMITED)
    Call<ProductsListModel> getAllProductsFromChildCategoryLimited(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part child_category_id
    );


    @POST
    Call<TrackDeliveryOutputModel> getRealTimeTrackingData(
            @Header("Content-Type") String applcationJson,
            @Header("Trackingmore-Api-Key") String trackingKey,
            @Body TrackDeliveryInputModel trackDeliveryInputModel,
            @Url String url
    );


    //Smart Pay New API

    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.ADD_MONEY_TO_WALLET)
    Call<IsSellerExist> addMoneyToWallet(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part wallet_id,
            @Part MultipartBody.Part amount
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.SEND_MONEY_TO_USERS)
    Call<IsSellerExist> sendMoneyToWallet(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part sender_wallet_id,
            @Part MultipartBody.Part phone_no,
            @Part MultipartBody.Part amount
    );


    @Multipart
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_WALLET_TRANSACTIONS)
    Call<GetWalletTransactionsOutput> getWalletTransactions(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part wallet_id

    );


    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.PLACE_MOBILE_RECHARGE_REQUEST)
    Call<CommonOutput> placeMobileRechargeRequest(
            @Header("X-API-KEY") String xAccessToken,
            @Body MobileRechargeModel mobileRechargeModel
    );


    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_CARD)
    Call<JsonObject> postGetCard(@Header("Authorization") String xAccessToken, @Path("country_code") String country_code, @Path("product_code") String product_code);
//hi bikash i have sent rupee 50 gift card

    @GET(com.stuffrs.newappopay.stuffers_business.api.Constants.SENT_APPOPAY_GIFT_CARD)
    Call<JsonObject> sentAppopayGiftCards(@Path("recMobileNumber") String recMobileNumber,
                                          @Path("revCountryCode") String revCountryCode,
                                          @Path("amount") String amount,
                                          @Path("country") String country,
                                          @Path("message") String message,
                                          @Header("Authorization") String xAccessToken);


    /**
     * Api for UnionPay Decode Qr Code
     */
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.QRCODE_DECODE)
    Call<JsonObject> getDecodeFormat(@Body RequestBody encryptedScanData, @Header("Authorization") String xAccessToken);

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.OCR_ID)
    Call<JsonObject> getExtractData(@Body JsonObject param);

    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.OCR_BANK)
    Call<JsonObject> getExtractedDataOfBank(@Body JsonObject param);


}


