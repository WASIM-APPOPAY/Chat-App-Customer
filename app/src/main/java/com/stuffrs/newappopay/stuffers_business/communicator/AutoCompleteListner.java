package com.stuffrs.newappopay.stuffers_business.communicator;


import com.stuffrs.newappopay.stuffers_business.models.PredictionModel;

public interface AutoCompleteListner {
    void onSuccess(PredictionModel prediction);
    void onError(String err);
}
