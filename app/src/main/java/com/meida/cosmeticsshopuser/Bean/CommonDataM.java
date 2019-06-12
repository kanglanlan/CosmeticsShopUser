package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by LFC
 * on 2018/8/30.
 */

public class CommonDataM
{

    private  int type;
    private  String name;
    private  String note;

    public boolean isComMeg() {
        return isComMeg;
    }

    public void setComMeg(boolean comMeg) {
        isComMeg = comMeg;
    }

    private  boolean isComMeg;

    public CommonDataM() {
    }

    public CommonDataM(String name) {
        this.name = name;
    }

    public CommonDataM(int type, String name, String note) {
        this.type = type;
        this.name = name;
        this.note = note;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note == null ? "" : note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
