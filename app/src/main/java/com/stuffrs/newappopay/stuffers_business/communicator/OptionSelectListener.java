package com.stuffrs.newappopay.stuffers_business.communicator;

import org.json.JSONObject;

public interface OptionSelectListener {
    public void onSelectConfirm(String param, JSONObject userData);
}
