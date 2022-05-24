package com.stuffrs.newappopay.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.onesignal.OSDeviceState;

import com.onesignal.OneSignal;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.Helper;
import com.stuffrs.newappopay.home_bottom.BottomCallFragment;
import com.stuffrs.newappopay.home_bottom.BottomChatFragment;
import com.stuffrs.newappopay.home_bottom.BottomServiceFragment;
import com.stuffrs.newappopay.interfaces.ChatItemClickListener;
import com.stuffrs.newappopay.activity.ChatActivity;
import com.stuffrs.newappopay.model.AttachmentTypes;
import com.stuffrs.newappopay.model.Chat;
import com.stuffrs.newappopay.model.Message;
import com.stuffrs.newappopay.model.User;
import com.stuffrs.newappopay.views.MyTextView;
import com.stuffrs.newappopay.views.MyTextViewBold;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements View.OnClickListener, ChatItemClickListener {
    private static final int REQUEST_CODE_CHAT_FORWARD = 99;
    private ImageView ivMenu;
    private LinearLayout llChat, llService, llCall;
    private Helper helper;
    User userMe;
    private MyTextViewBold tvUserName;
    private MyTextView tvMobileNumber;
    private DatabaseReference myInboxRef;
    private ArrayList<Message> messageForwardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        setContentView(R.layout.activity_home);
        userMe = helper.getLoggedInUser();
        tvUserName = (MyTextViewBold) findViewById(R.id.tvUserName);
        tvMobileNumber = (MyTextView) findViewById(R.id.tvMobileNumber);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        llChat = (LinearLayout) findViewById(R.id.layoutChat);
        llService = (LinearLayout) findViewById(R.id.layoutService);
        llCall = (LinearLayout) findViewById(R.id.layoutCalls);
        llChat.setOnClickListener(this);
        llService.setOnClickListener(this);
        llCall.setOnClickListener(this);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(HomeActivity.this, ivMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.home_menu, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(HomeActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        if (savedInstanceState == null) {
            BottomChatFragment mBottomChatFragment = new BottomChatFragment();
            initFragment(mBottomChatFragment);
        }

        tvUserName.setText(userMe.getName());
        tvMobileNumber.setText("+" + userMe.getId());
        registerChatUpdates();
        updateFcmToken();
    }

    private void updateFcmToken() {
        OneSignal.addSubscriptionObserver(stateChanges -> {
            /*if (!stateChanges.getFrom().getSubscribed() && stateChanges.getTo().getSubscribed()) {
                usersRef.child(userMe.getId()).child("userPlayerId").setValue(stateChanges.getTo().getUserId());
                helper.setMyPlayerId(stateChanges.getTo().getUserId());
            }*/
            if (!stateChanges.getFrom().isSubscribed() && stateChanges.getTo().isSubscribed()) {
                usersRef.child(userMe.getId()).child("userPlayerId").setValue(stateChanges.getTo().getUserId());
                helper.setMyPlayerId(stateChanges.getTo().getUserId());
            }
        });
        OSDeviceState status = OneSignal.getDeviceState();
        //OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        //if ()
        if (status != null && status.isSubscribed() && status.getUserId() != null) {
            usersRef.child(userMe.getId()).child("userPlayerId").setValue(status.getUserId());
            helper.setMyPlayerId(status.getUserId());
        }
        //usersRef.child(userMe.getId()).child("userPlayerId").setValue(OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
    }

    private void registerChatUpdates() {
        if (myInboxRef == null) {
            myInboxRef = inboxRef.child(userMe.getId());
            myInboxRef.addChildEventListener(chatChildEventListener);
        }
    }

    private void initFragment(Fragment mFragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mHomeContainer, mFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    protected void onDestroy() {
        //markOnline(false);
        if (myInboxRef != null && chatChildEventListener != null)
            myInboxRef.removeEventListener(chatChildEventListener);
        super.onDestroy();
    }

    private ChildEventListener chatChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (mContext != null) {
                Message newMessage = dataSnapshot.getValue(Message.class);
                if (newMessage != null && newMessage.getId() != null && newMessage.getChatId() != null) {

                    if (newMessage.getAttachmentType() == AttachmentTypes.NONE_NOTIFICATION) {
                        setNotificationMessageNames(newMessage);
                    }

                    Chat newChat = new Chat(newMessage, newMessage.getSenderId().equals(userMe.getId()));
                    if (!newChat.isGroup()) {
                        newChat.setChatName(getNameById(newChat.getUserId()));
//                            for (User user : myUsers) {
//                                if (user.getId().equals(newChat.getUserId())) {
//                                    newChat.setChatName(user.getNameToDisplay());
//                                    break;
//                                }
//                            }
                    }
                    //if (adapter != null) {
                        Fragment mFragment = getSupportFragmentManager().findFragmentById(R.id.mHomeContainer);
                        if (mFragment instanceof BottomChatFragment) {
                            ((BottomChatFragment)mFragment).addMessage(newChat);
                        }
                        /*BottomChatFragment fragment = (BottomChatFragment) adapter.getItem(newChat.isGroup() ? 1 : 0);
                        fragment.addMessage(newChat);*/
                    //}
                }
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (mContext != null) {
                Message updatedMessage = dataSnapshot.getValue(Message.class);
                if (updatedMessage != null && updatedMessage.getId() != null && updatedMessage.getChatId() != null) {
                    if (updatedMessage.getAttachmentType() == AttachmentTypes.NONE_NOTIFICATION) {
                        setNotificationMessageNames(updatedMessage);
                    }

                    Chat newChat = new Chat(updatedMessage, updatedMessage.getSenderId().equals(userMe.getId()));
                    if (!newChat.isGroup()) {
                        newChat.setChatName(getNameById(newChat.getUserId()));
//                            for (User user : myUsers) {
//                                if (user.getId().equals(newChat.getUserId())) {
//                                    newChat.setChatName(user.getNameToDisplay());
//                                    break;
//                                }
//                            }
                    }
                    /*if (adapter != null) {
                        MyChatsFragment fragment = (MyChatsFragment) adapter.getItem(newChat.isGroup() ? 1 : 0);
                        fragment.addMessage(newChat);
                    }*/
                    Fragment mFragment = getSupportFragmentManager().findFragmentById(R.id.mHomeContainer);
                    if (mFragment instanceof BottomChatFragment) {
                        ((BottomChatFragment)mFragment).addMessage(newChat);
                    }
                }
            }
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layoutChat) {
            BottomChatFragment mBottomChatFragment = new BottomChatFragment();
            initFragment(mBottomChatFragment);
        } else if (view.getId() == R.id.layoutService) {
            BottomServiceFragment mBottomServiceFragment = new BottomServiceFragment();
            initFragment(mBottomServiceFragment);
        } else if (view.getId() == R.id.layoutCalls) {
            BottomCallFragment mBottomCallFragment = new BottomCallFragment();
            initFragment(mBottomCallFragment);
        }
    }

    @Override
    public void onChatItemClick(Chat chat, int position, View userImage) {
        /*if (chat.isGroup() && chat.isLatest()) {
            ArrayList<Message> newGroupForwardList = new ArrayList<>();
            Message newMessage = new Message();
            newMessage.setBody(getString(R.string.invitation_group));
            newMessage.setAttachmentType(AttachmentTypes.NONE_NOTIFICATION);
            newMessage.setAttachment(null);
            newGroupForwardList.add(newMessage);
            openChat(ChatActivity.newIntent(mContext, newGroupForwardList, chat), userImage);
        } else {
            openChat(ChatActivity.newIntent(mContext, messageForwardList, chat), userImage);
        }*/
        //openChat(ChatActivity.newIntent(mContext, messageForwardList, chat), userImage);
        openChat(ChatActivity.newIntent(HomeActivity.this, messageForwardList, chat), userImage);


    }
    private void openChat(Intent intent, View userImage) {

        /*if (userImage == null) {
            userImage = usersImage;
        }*/

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, userImage, "userImage");
        startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD, activityOptionsCompat.toBundle());
        //startActivityForResult(intent,REQUEST_CODE_CHAT_FORWARD);

//        if (Build.VERSION.SDK_INT > 21) {
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, userImage, "userImage");
//            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD, options.toBundle());
//        } else {
//            startActivityForResult(intent, REQUEST_CODE_CHAT_FORWARD);
//            overridePendingTransition(0, 0);
//        }


    }

    @Override
    public void placeCall(boolean callIsVideo, User user) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_CODE_CHAT_FORWARD):
                if (resultCode == Activity.RESULT_OK) {
                    //show forward dialog to choose users
                    messageForwardList.clear();
                    ArrayList<Message> temp = data.getParcelableArrayListExtra("FORWARD_LIST");
                    messageForwardList.addAll(temp);
                    /*userSelectDialogFragment = UserSelectDialogFragment.newUserSelectInstance(myUsers);
                    FragmentManager manager = getSupportFragmentManager();
                    Fragment frag = manager.findFragmentByTag(USER_SELECT_TAG);
                    if (frag != null) {
                        manager.beginTransaction().remove(frag).commit();
                    }
                    userSelectDialogFragment.show(manager, USER_SELECT_TAG);*/
                }
                break;
        }
    }
}