package com.example.miogk.gobang.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2018/12/13.
 */

public class ConstellationYear extends ConstellationBase {
    private String name;
    private String date;
    private String year;
    private Mima mima;
    private String[] career;
    private String[] love;
    private String[] health;
    private String[] finance;
    private String luckeyStone;
    private String future;
    private String resultcode;
    private String error_code;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Mima getMima() {
        return mima;
    }

    public void setMima(Mima mima) {
        this.mima = mima;
    }

    public String[] getCareer() {
        return career;
    }

    public void setCareer(String[] career) {
        this.career = career;
    }

    public String[] getLove() {
        return love;
    }

    public void setLove(String[] love) {
        this.love = love;
    }

    public String[] getHealth() {
        return health;
    }

    public void setHealth(String[] health) {
        this.health = health;
    }

    public String[] getFinance() {
        return finance;
    }

    public void setFinance(String[] finance) {
        this.finance = finance;
    }

    public String getLuckeyStone() {
        return luckeyStone;
    }

    public void setLuckeyStone(String luckeyStone) {
        this.luckeyStone = luckeyStone;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
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

    public class Mima implements Serializable{
        public String info;
        public String[] text;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String[] getText() {
            return text;
        }

        public void setText(String[] text) {
            this.text = text;
        }
    }
}