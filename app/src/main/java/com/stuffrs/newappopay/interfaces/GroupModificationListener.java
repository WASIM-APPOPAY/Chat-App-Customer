package com.stuffrs.newappopay.interfaces;

import java.util.ArrayList;

public interface GroupModificationListener {
    void groupModified(String message, ArrayList<String> newUserIds);
    String fetchNameById(String userId);
}
