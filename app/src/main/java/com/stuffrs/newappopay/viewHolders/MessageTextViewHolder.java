package com.stuffrs.newappopay.viewHolders;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.ClickableMovementMethod;
import com.stuffrs.newappopay.Utils.LinkTransformationMethod;
import com.stuffrs.newappopay.interfaces.OnMessageItemClick;
import com.stuffrs.newappopay.model.AttachmentTypes;
import com.stuffrs.newappopay.model.Message;
import com.vanniktech.emoji.EmojiTextView;


/**
 * Created by mayank on 11/5/17.
 */

public class MessageTextViewHolder extends BaseMessageViewHolder {
    private EmojiTextView text;

    private Message message;

    public MessageTextViewHolder(View itemView, View newMessageView, OnMessageItemClick itemClickListener) {
        super(itemView, newMessageView, itemClickListener);
        text = itemView.findViewById(R.id.text);

        text.setTransformationMethod(new LinkTransformationMethod());
        text.setMovementMethod(ClickableMovementMethod.getInstance());
        // Reset for TextView.fixFocusableAndClickableSettings(). We don't want View.onTouchEvent()
        // to consume touch events.
        text.setClickable(false);
        text.setLongClickable(false);

        itemView.setOnClickListener(v -> {
            if (message != null && message.getAttachmentType() == AttachmentTypes.NONE_NOTIFICATION)
                return;
            onItemClick(true);
        });
        itemView.setOnLongClickListener(v -> {
            if (message != null && message.getAttachmentType() == AttachmentTypes.NONE_NOTIFICATION)
                return true;
            onItemClick(false);
            return true;
        });
    }

    @Override
    public void setData(Message message, int position) {
        super.setData(message, position);
        this.message = message;
        LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) text.getLayoutParams();
        if (message.getAttachmentType() == AttachmentTypes.NONE_TEXT) {
            text.setGravity(isMine() ? Gravity.END : Gravity.START);
            textParams.gravity = isMine() ? Gravity.END : Gravity.START;
        }
        text.setLayoutParams(textParams);
        text.setTextColor(ContextCompat.getColor(context, message.getAttachmentType() == AttachmentTypes.NONE_NOTIFICATION ? R.color.card_notification_color_text : isMine() ? android.R.color.white : android.R.color.black));
        text.setText(message.getBody());
        time.setVisibility(message.getAttachmentType() == AttachmentTypes.NONE_NOTIFICATION ? View.GONE : View.VISIBLE);
    }

}
