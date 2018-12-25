package com.example.miogk.gobang.domain;

/**
 * Created by Administrator on 2018/12/15.
 */

public class ConstellationWeekly extends ConstellationBase {
    private String name;//星座名字
    private String date;//日期
    private String weekth;//周数
    private String health;//健康指数
    private String job;//求职指数
    private String love;//爱情指数
    private String money;//金钱指数
    private String work;//工作指数
    private String resultcode;//返回码
    private String error_code;//错误码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekth() {
        return weekth;
    }

    public void setWeekth(String weekth) {
        this.weekth = weekth;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
