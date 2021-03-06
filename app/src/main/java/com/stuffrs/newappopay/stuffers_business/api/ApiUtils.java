package com.stuffrs.newappopay.stuffers_business.api;

/**
 * Created by Sandeep on 28-12-2017.
 */

public class ApiUtils {


    private ApiUtils() {

    }


    // public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String BASE_URL = Constants.APPOPAY_BASE_URL;
    public static final String BASE_URL_NODE = Constants.APPOPAY_BASE_NODE_URL;
    public static final String BASE_URL_CERCA24 = Constants.CERCA24;
    public static final String BASE_UNION = Constants.BASE_UNION_PAY;
    public static final String BASE_UNION_PAY = Constants.UNION_PAY;
    public static final String BASE_OCR=Constants.BASE_OCR;

    public static MainUAPIInterface getApiServiceUNIONPay() {
        return RetrofitClient.getClientUnionPayCard(BASE_UNION_PAY).create(MainUAPIInterface.class);
    }

    public static MainAPIInterface getApiServiceOCR(){
        return RetrofitClient.getClientOcr(BASE_OCR).create(MainAPIInterface.class);
    }
    public static MainAPIInterface getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(MainAPIInterface.class);
    }

    public static MainAPIInterface getAPIServiceNode() {

        return RetrofitClient.getClientNode(BASE_URL_NODE).create(MainAPIInterface.class);
    }



    public static MainAPIInterface getUnionPayService() {
        return RetrofitClient.getClientUnionPay(BASE_UNION).create(MainAPIInterface.class);
    }

    /*private static Retrofit retrofit = null;
    public static Retrofit getClient(String url){
        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/

    /**
     * @param notificationURLs below code for sent notification service
     * @return Retrofite Instance
     */

    public static MainAPIInterface getApiServiceForNotification(String notificationURLs) {
        return RetrofitClient.getClientForNotification(notificationURLs).create(MainAPIInterface.class);
    }


}
