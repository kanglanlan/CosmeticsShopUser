package com.meida.cosmeticsshopuser.MyView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.R;


/**
 * Created by Administrator on 2017/12/4.
 * 评论doalog
 */

public class CommentEditDialog extends Dialog {
    private DialogViewListener listener;
    Context context;
    EditText commont_edittext;
    Button btn_sure;
    TextView tv_maxnum;
    int maxNum = 100;

    public interface DialogViewListener {
        void onListSureClick(View view, String content);
    }

    private TextChangeListener textChangeListener;
    public void setTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }
    public interface TextChangeListener{
        void onTextChange(String text);
    }

    private int layoutId = R.layout.dialog_comment_add;

    public CommentEditDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CommentEditDialog(@NonNull Context context,int layoutId,int maxNum) {
        super(context);
        this.context = context;
        this.layoutId = layoutId;
        this.maxNum = maxNum;
    }

    public void setDialogViewListener(DialogViewListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        setContentView(view);

        //获取当前Activity所在的窗体
        Window dialogWindow = getWindow();
        //设置Dialog从窗体中间弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        //解决弹出被底部导航栏遮挡问题
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //消除边距
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        //设置Dialog点击外部消失
        setCanceledOnTouchOutside(true);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        lp.width = wm.getDefaultDisplay().getWidth();
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        commont_edittext = view.findViewById(R.id.commont_edittext);
        showKeyboard(commont_edittext);
        tv_maxnum = view.findViewById(R.id.tv_maxnum);
        btn_sure = view.findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = commont_edittext.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    MUIToast.show(context, "请输入内容");
                    return;
                }
                listener.onListSureClick(view, content);
                cancel();
            }
        });
        commont_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.length();
                tv_maxnum.setText(length + "/" + maxNum);
                if (textChangeListener!=null){
                    textChangeListener.onTextChange(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    //对话框请求弹出键盘
    public void showKeyboard(EditText editText) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
        }
    }

    @Override
    public void dismiss() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //隐藏软键盘
        imm.hideSoftInputFromWindow(commont_edittext.getWindowToken(), 0);
        super.dismiss();
    }


    public void clearContent(){
        tv_maxnum.setText("0/"+maxNum);
        commont_edittext.setText("");
    }

}
