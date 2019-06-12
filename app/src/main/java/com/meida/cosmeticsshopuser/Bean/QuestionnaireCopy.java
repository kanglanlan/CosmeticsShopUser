package com.meida.cosmeticsshopuser.Bean;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/12/24 0024.
 */

public class QuestionnaireCopy extends BaseBean{

    private List<Question> data;

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

    public static class Question{
        private String id;
        private String orderindex;
        private String title;
        private String status;
        private List<Option> item;
        private String radiocheck;/*1 单选 2 多选*/


        public String getRadiocheck() {
            return radiocheck;
        }

        public void setRadiocheck(String radiocheck) {
            this.radiocheck = radiocheck;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderindex() {
            return orderindex;
        }

        public void setOrderindex(String orderindex) {
            this.orderindex = orderindex;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Option> getItem() {
            return item;
        }

        public void setItem(List<Option> item) {
            this.item = item;
        }
    }


    public static class Option{
        private String id;
        private String qid;
        private String orderindex;
        private String title;
        private String tagid;
        private CompoundButton radioButton;


        public CompoundButton getRadioButton() {
            return radioButton;
        }

        public void setRadioButton(CompoundButton radioButton) {
            this.radioButton = radioButton;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public String getOrderindex() {
            return orderindex;
        }

        public void setOrderindex(String orderindex) {
            this.orderindex = orderindex;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTagid() {
            return tagid;
        }

        public void setTagid(String tagid) {
            this.tagid = tagid;
        }
    }

}
