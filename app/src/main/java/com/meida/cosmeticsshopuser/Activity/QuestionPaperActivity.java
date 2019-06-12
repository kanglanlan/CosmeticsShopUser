package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.LoginBean;
import com.meida.cosmeticsshopuser.Bean.Questionnaire;
import com.meida.cosmeticsshopuser.Bean.QuestionnaireCopy;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.QuestionPaperAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestionPaperActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private QuestionPaperAdapter adapter;
    private List<QuestionnaireCopy.Question> data = new ArrayList<>();
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper);
        changeTitle("调查问卷","跳过");
        initView();
        initEvent();
        initData();

    }


    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        adapter = new QuestionPaperAdapter(baseContext,R.layout.item_qp_problem,data);
        recyclerView.setAdapter(adapter);
        submit = (Button) findViewById(R.id.submit);
    }

    private void initEvent(){
        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getData();
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnswer();
            }
        });

    }

    private void initData(){
        /*initTestData();
        adapter.notifyDataSetChanged();*/
        getData();
    }


    /*获取题目*/
    private void getData() {
        mRequest01 = getRequest(Params.getQuestionnaireList, true);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<Questionnaire>(baseContext, true, Questionnaire.class) {
                    @Override
                    public void doWork(Questionnaire result, String code) {
                        List<Questionnaire.Question> beans = result.getData();
                        if (beans!=null && beans.size()>0){
                            data.clear();
                            for (int i = 0; i<beans.size(); i++){
                                QuestionnaireCopy.Question que = new QuestionnaireCopy.Question();
                                que.setId(beans.get(i).getId());
                                que.setTitle(beans.get(i).getTitle());
                                que.setRadiocheck(beans.get(i).getRadiocheck());
                                List<QuestionnaireCopy.Option> options = new ArrayList<>();
                                for (int j = 0; j<beans.get(i).getItem().size(); j++){
                                    QuestionnaireCopy.Option option = new QuestionnaireCopy.Option();
                                    option.setId(beans.get(i).getItem().get(j).getId());
                                    option.setQid(beans.get(i).getItem().get(j).getQid());
                                    option.setTitle(beans.get(i).getItem().get(j).getTitle());
                                    options.add(option);
                                }
                                que.setItem(options);
                                data.add(que);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, false, true);
    }

    /*提交答卷*/
    private void submitAnswer(){
        if (!adapter.getAnswer()){
            return;
        }
        mRequest02 = getRequest(Params.submitQuestionnaireAnswer,true);
        mRequest02.add("itemid",adapter.getProblems());
        mRequest02.add("answer",adapter.getAnswers());
       /* LoggerUtil.e("itemid",adapter.getProblems());
        LoggerUtil.e("answer",adapter.getAnswers());*/
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<BaseBean>(baseContext,true,BaseBean.class) {
                    @Override
                    public void doWork(BaseBean data, String code) {
                        ActionDialog dialog = new ActionDialog(baseContext,"感谢您参与我们的调查问卷",2);
                        dialog.setOnlyConfirmListeer(new ActionDialog.OnOnlyConfirmListeer() {
                            @Override
                            public void onOnlyConfirm() {
                                finish();
                            }
                        });
                        dialog.show();
                    }
                },false,true);

    }

}
