package com.meida.cosmeticsshopuser.rongim;

import android.content.Context;
import android.content.Intent;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class SealNotificationReceiver extends PushMessageReceiver {
    /* push 通知到达事件*/
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {

        return false; // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    /* push 通知点击事件 */
    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {

//        if (LUtils.isBackground(context))

//        message.getExtra().get
       /* if (ActivityStack.getScreenManager().isContainsActivity(LoginByYZM_A.class)) {
            ActivityStack.getScreenManager().popAllActivitys();
        }

        Intent intent = new Intent(context, LoginByYZM_A.class);
        intent.putExtra("Login_Type", "1");
        context.startActivity(intent);*/
//        if (LUtils.IsUserLogin(context)) {
//            Map<String, Boolean> supportedConversation = new HashMap<>();
//            supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);//非聚合式显示 private 类型的会话。
//            RongIM.getInstance().startConversationList(context, supportedConversation);
//        }
        return false; // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }
}