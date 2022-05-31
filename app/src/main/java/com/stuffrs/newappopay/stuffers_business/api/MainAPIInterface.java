package com.stuffrs.newappopay.stuffers_business.api;

import com.google.gson.JsonObject;
import com.stuffrs.newappopay.stuffers_business.fragments.bottom.chatnotification.MyResponse;
import com.stuffrs.newappopay.stuffers_business.fragments.bottom.chatnotification.Sender;
import com.stuffrs.newappopay.stuffers_business.models.Country.CountryCodeResponse;
import com.stuffrs.newappopay.stuffers_business.models.Product.ProductResponse;
import com.stuffrs.newappopay.stuffers_business.models.bank.BankCurrencyResponse;
import com.stuffrs.newappopay.stuffers_business.models.bank.BankNameResponse;
import com.stuffrs.newappopay.stuffers_business.models.bank.account.BankAccResponse;
import com.stuffrs.newappopay.stuffers_business.models.lunex_giftcard.GiftProductList;
import com.stuffrs.newappopay.stuffers_business.models.output.AuthorizationResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.CurrencyResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.MappingResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.SaveCardResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.TransactionListResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.YourOrderOutputModel;

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
    @POST(com.stuffrs.newappopay.stuffers_business.api.Constants.GET_ALL_YOUR_ORDERS)
    Call<YourOrderOutputModel> getAllYourOrders(
            @Header("X-API-KEY") String xAccessToken,
            @Part MultipartBody.Part customer_id
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


