package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.QuestionnaireCopy;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */
/*              R.layout.item_qp_option
                R.layout.item_qp_problem        */
public class QuestionPaperAdapter extends CommonAdapter<QuestionnaireCopy.Question> {
    private List<QuestionnaireCopy.Question> datas = new ArrayList<>();
    Context mContext;

    public QuestionPaperAdapter(Context context, int layoutId, List<QuestionnaireCopy.Question> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final QuestionnaireCopy.Question bean, int position) {
        TextView problem = holder.getView(R.id.problem);
        problem.setText(String.format("%s、%s", String.valueOf(position + 1), datas.get(position).getTitle()));
        RadioGroup radioGroup = holder.getView(R.id.radioParent2);
        radioGroup.removeAllViews();
        LinearLayout radioParent = holder.getView(R.id.radioParent);
        radioParent.removeAllViews();
        /*1 单选 2 多选*/
        if ("1".equals(bean.getRadiocheck())){
            if (datas.get(position).getItem()!=null){
                for (int i = 0;i<datas.get(position).getItem().size(); i++){
                    /*View optionView = LayoutInflater.from(mContext).inflate(R.layout.item_qp_option2,null);
                    RadioButton optionTv = optionView.findViewById(R.id.radioOption2);
                    optionTv.setId(optionTv.getId()+position+i);*/
                    RadioButton optionTv = new RadioButton(mContext);
                    optionTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.selector_check_bg,0,0,0);
                    optionTv.setCompoundDrawablePadding(DisplayUtil.dp2px(10));
                    optionTv.setButtonDrawable(null);
                    optionTv.setTextSize(14);
                    optionTv.setTextColor(Color.parseColor("#333333"));
                    optionTv.setPadding(DisplayUtil.dp2px(15),DisplayUtil.dp2px(0),
                            DisplayUtil.dp2px(15),DisplayUtil.dp2px(15));
                    optionTv.setText(datas.get(position).getItem().get(i).getTitle());
                    Log.e("optionTv",optionTv.getId()+"");
                    radioGroup.addView(optionTv,i,
                            new RadioGroup.LayoutParams
                                    (RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT));
                    datas.get(position).getItem().get(i).setRadioButton(optionTv);
                }
            }
        }else{
            if (datas.get(position).getItem()!=null){
                for (int i = 0;i<datas.get(position).getItem().size(); i++){
                    View optionView = LayoutInflater.from(mContext).inflate(R.layout.item_qp_option,null);
                    CheckBox optionTv = optionView.findViewById(R.id.radioOption);
                    optionTv.setId(optionTv.getId()+position+i);
                    optionTv.setText(datas.get(position).getItem().get(i).getTitle());
                    Log.e("optionTv",optionTv.getId()+""+bean.getItem().get(i).getId());
                    radioParent.addView(optionTv,i,
                            new RadioGroup.LayoutParams
                                    (RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT));
                    datas.get(position).getItem().get(i).setRadioButton(optionTv);
                }
            }
        }

    }



    String answers = "",problems = "";
    public boolean getAnswer(){
        answers = "";
        problems = "";
        String lastProblemId = "";
        for (int i = 0; i<datas.size(); i++){
            problems = String.format("%s,%s", problems, datas.get(i).getId());
            boolean hasCheck = false;
            for (int j = 0; j<datas.get(i).getItem().size(); j++){
                CompoundButton radioButton = datas.get(i).getItem().get(j).getRadioButton();
                if (radioButton!=null && radioButton.isChecked()){
                    hasCheck = true;
                    if (lastProblemId.equals(datas.get(i).getId())){
                        answers = String.format("%s-%s", answers, datas.get(i).getItem().get(j).getId());
                    }else{
                        lastProblemId = datas.get(i).getId();
                        answers = String.format("%s,%s", answers, datas.get(i).getItem().get(j).getId());
                    }
                }
            }

            if (!hasCheck){
                answers = String.format("%s,%s", answers, "");
                lastProblemId = datas.get(i).getId();
            }

        }
        LoggerUtil.e("answers",answers);
        LoggerUtil.e("problems",problems);
        return true;
    }

    public String getAnswers() {
        //Log.e("answers ^^",answers);
        if ( (!TextUtils.isEmpty(answers)) && (answers.length()>1) ){
            //answers = answers.substring(1,answers.length());
            return answers.substring(1,answers.length());
        }
        return "";
    }

    public String getProblems() {
        //Log.e("problems  ^^",problems);
        if ( (!TextUtils.isEmpty(problems)) && (problems.length()>1) ){
            return problems.substring(1,problems.length());
        }
        return "";
    }
}

