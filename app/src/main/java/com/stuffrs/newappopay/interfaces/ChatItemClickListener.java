package com.stuffrs.newappopay.interfaces;

import android.view.View;

import com.stuffrs.newappopay.model.Chat;
import com.stuffrs.newappopay.model.User;

public interface ChatItemClickListener {
    void onChatItemClick(Chat chat, int position, View userImage);

    void placeCall(boolean callIsVideo, User user);
}
