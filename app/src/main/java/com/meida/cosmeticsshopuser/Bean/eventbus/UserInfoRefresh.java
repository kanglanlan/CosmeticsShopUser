package com.meida.cosmeticsshopuser.Bean.eventbus;

/**
 * Created by Administrator on 2018/12/29 0029.
 */

public class UserInfoRefresh {

    /*修改用户信息同步*/
    private String headPath = "";
    private String nickName = "";
    private String sex;
    private String birthday = "";

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
