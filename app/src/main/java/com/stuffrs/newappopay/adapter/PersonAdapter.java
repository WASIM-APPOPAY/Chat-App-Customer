package com.stuffrs.newappopay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.chat.DemoChatActivity;
import com.stuffrs.newappopay.model.UserChat;
import com.stuffrs.newappopay.views.MyTextView;
import com.stuffrs.newappopay.views.MyTextViewBold;
import com.stuffrs.newappopay.views.MyTextViewBoldItalic;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {
    Context mCtx;
    ArrayList<UserChat> mList;

    public PersonAdapter(Context mCtx, ArrayList<UserChat> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_people, parent, false);
        return new PersonHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PersonHolder extends RecyclerView.ViewHolder {
        MyTextViewBold tvName;
        MyTextViewBoldItalic tvCount;
        MyTextView tvTime;


        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCount = itemView.findViewById(R.id.tvCount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentChat = new Intent(mCtx, DemoChatActivity.class);
                    intentChat.putExtra("name", mList.get(getAdapterPosition()).getName());
                    mCtx.startActivity(intentChat);
                }
            });

        }

        public void bind() {
            tvName.setText(mList.get(getAdapterPosition()).getName());
            tvTime.setText(mList.get(getAdapterPosition()).getTime());
            tvCount.setText(mList.get(getAdapterPosition()).getCount());
        }
    }
}
