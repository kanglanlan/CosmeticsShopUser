package com.meida.cosmeticsshopuser.rongim.provider;

import android.content.Context;
import android.content.res.Resources;
import android.text.ClipboardManager;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.rongim.message.TestMessage;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utilities.OptionsPopupDialog;
import io.rong.imkit.widget.AutoLinkTextView;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 消息展示
 * Created by Beyond on 2016/12/5.
 */

@ProviderTag(messageContent = TestMessage.class, showReadState = true)
public class TestMessageProvider extends IContainerItemProvider.MessageProvider<TestMessage> {
    private static final String TAG = "TestMessageItemProvider";
    private Context baseContext;

    private static class ViewHolder {
        AutoLinkTextView message;
        TextView tv;
        ImageView img_Bg;
        boolean longClick;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        baseContext = context;
        // TODO: 2018/12/3  这个地方 换成自己的布局就会提示 暂不支持的消息类型  （暂未找到原因）用背景图代替
//        View view = LayoutInflater.from(context).inflate(R.layout.de_customize_message_red_packet, null);
        View view = LayoutInflater.from(context).inflate(io.rong.imkit.R.layout.rc_item_text_message, null);

        ViewHolder holder = new ViewHolder();
        holder.message = (AutoLinkTextView) view.findViewById(android.R.id.text1);
        holder.message.setBackground(context.getResources().getDrawable(R.mipmap.img_redpacket_msg));
//        holder.tv = (TextView) view.findViewById(R.id.tv_state_redrong);
//        holder.img_Bg = (ImageView) view.findViewById(R.id.tv_state_redrong);
        view.setTag(holder);
        return view;
    }

    @Override
    public Spannable getContentSummary(TestMessage data) {
        return null;
    }

    @Override
    public Spannable getContentSummary(Context context, TestMessage data) {
        baseContext = context;
        if (data == null)
            return null;

        String content = data.getContent();
        if (content != null) {
            if (content.length() > 100) {
                content = content.substring(0, 100);
            }
            return new SpannableString(AndroidEmoji.ensure(content));
        }
        return null;
    }

    @Override
    public void onItemClick(View view, int position, TestMessage content, UIMessage message) {
//        LUtils.showToask(baseContext, "拆红包!");
       /* String strRedID = content.getRedID() + "";
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {
            EventBus.getDefault().post(new MessageEvent(Params.EB_ShowRedPopu, -1, strRedID));
        } else {
            EventBus.getDefault().post(new MessageEvent(Params.EB_ShowRedPopu, -2, strRedID));
        }*/
    }

    private void ShowRedPopu(String strRedID) {

    }


    @Override
    public void onItemLongClick(final View view, int position, final TestMessage content, final UIMessage message) {

        TestMessageProvider.ViewHolder holder = (TestMessageProvider.ViewHolder) view.getTag();
        holder.longClick = true;
        if (view instanceof TextView) {
            CharSequence text = ((TextView) view).getText();
            if (text != null && text instanceof Spannable)
                Selection.removeSelection((Spannable) text);
        }

        String[] items;

        long deltaTime = RongIM.getInstance().getDeltaTime();
        long normalTime = System.currentTimeMillis() - deltaTime;
        boolean enableMessageRecall = false;
        int messageRecallInterval = -1;
        boolean hasSent = (!message.getSentStatus().equals(Message.SentStatus.SENDING)) && (!message.getSentStatus().equals(Message.SentStatus.FAILED));

        try {
            enableMessageRecall = RongContext.getInstance().getResources().getBoolean(io.rong.imkit.R.bool.rc_enable_message_recall);
            messageRecallInterval = RongContext.getInstance().getResources().getInteger(io.rong.imkit.R.integer.rc_message_recall_interval);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        if (hasSent
                && enableMessageRecall
                && (normalTime - message.getSentTime()) <= messageRecallInterval * 1000
                && message.getSenderUserId().equals(RongIM.getInstance().getCurrentUserId())
                && !message.getConversationType().equals(Conversation.ConversationType.CUSTOMER_SERVICE)
                && !message.getConversationType().equals(Conversation.ConversationType.APP_PUBLIC_SERVICE)
                && !message.getConversationType().equals(Conversation.ConversationType.PUBLIC_SERVICE)
                && !message.getConversationType().equals(Conversation.ConversationType.SYSTEM)
                && !message.getConversationType().equals(Conversation.ConversationType.CHATROOM)) {
            items = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_copy), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_recall)};
        } else {
            items = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_copy), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete)};
        }

        OptionsPopupDialog.newInstance(view.getContext(), items).setOptionsPopupDialogListener(new OptionsPopupDialog.OnOptionsItemClickedListener() {
            @Override
            public void onOptionsItemClicked(int which) {
                if (which == 0) {
                    @SuppressWarnings("deprecation")
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(((TestMessage) content).getContent());
                } else if (which == 1) {
                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, null);
                } else if (which == 2) {
                    RongIM.getInstance().recallMessage(message.getMessage(), getPushContent(view.getContext(), message));
                }
            }
        }).show();
    }

    @Override
    public void bindView(final View v, int position, final TestMessage content, final UIMessage data) {
        TestMessageProvider.ViewHolder holder = (TestMessageProvider.ViewHolder) v.getTag();
//        try {
//            holder.message.setBackground(baseContext.getResources().getDrawable(R.mipmap.img_redpacket_msg));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
