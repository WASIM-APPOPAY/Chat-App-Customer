package com.stuffrs.newappopay.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.Helper;
import com.stuffrs.newappopay.adapter.MoreAdapter;
import com.stuffrs.newappopay.model.ChatMore;
import com.stuffrs.newappopay.views.MyTextViewBold;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class DemoChatActivity extends AppCompatActivity {

    private ImageView ivMenuBottom;
    private ImageView ivMenu;
    private ImageView ivBack, ivMore;
    private MyTextViewBold tvNameChat;
    private ExpandableLayout expMore;
    private RecyclerView rvBottomChat;
    private int mCount = 0;
    private MoreAdapter mMoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        expMore = (ExpandableLayout) findViewById(R.id.expMore);
        rvBottomChat = (RecyclerView) findViewById(R.id.rvBottomChat);
        rvBottomChat.setLayoutManager(new GridLayoutManager(this, 4));
        ivBack = findViewById(R.id.ivBack);
        ivMore = findViewById(R.id.ivMore);
        tvNameChat = findViewById(R.id.tvNameChat);
        String name = getIntent().getStringExtra("name");
        tvNameChat.setText(name);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivMenu = (ImageView) findViewById(R.id.ivMenu);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(DemoChatActivity.this, ivMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.home_menu, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(DemoChatActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        ivMenuBottom = findViewById(R.id.ivMenuBottom);
        ivMenuBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expMore.isExpanded()) {
                    expMore.setExpanded(false);
                    mCount = 0;
                }
                showPictureialog();
            }
        });

        ArrayList<ChatMore> moreItems = Helper.getMoreItems();

        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCount == 0) {
                    expMore.setExpanded(true);
                    mCount = 1;
                } else {
                    expMore.setExpanded(false);
                    mCount = 0;
                }

                if (mMoreAdapter == null) {
                    mMoreAdapter = new MoreAdapter(moreItems,DemoChatActivity.this);
                }
                rvBottomChat.setAdapter(mMoreAdapter);


            }
        });
    }

    private void showPictureialog() {
        Dialog dialog = new Dialog(DemoChatActivity.this,
                android.R.style.Theme_Translucent_NoTitleBar);

        // Setting dialogue
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.setTitle(null);

        dialog.setContentView(R.layout.service_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
    }


}