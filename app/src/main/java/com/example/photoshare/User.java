package com.example.photoshare;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
/*
用于熟悉bmob操作用
该项目中并没有使用到该类
Android大作业
 */
public class User extends BmobObject {

    private String nickname;

    //密码
    private String pwd;



    private String country;


    private Integer score;


    private Integer steal;


    private Integer foul;



    private Integer fault;



    private Integer age;



    private Integer gender;



    private BmobGeoPoint address;



    private BmobFile avatar;



    private List<String> alias;


    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public User setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getSteal() {
        return steal;
    }

    public User setSteal(Integer steal) {
        this.steal = steal;
        return this;
    }

    public Integer getFoul() {
        return foul;
    }

    public User setFoul(Integer foul) {
        this.foul = foul;
        return this;
    }

    public Integer getFault() {
        return fault;
    }

    public User setFault(Integer fault) {
        this.fault = fault;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public User setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public BmobGeoPoint getAddress() {
        return address;
    }

    public User setAddress(BmobGeoPoint address) {
        this.address = address;
        return this;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public User setAvatar(BmobFile avatar) {
        this.avatar = avatar;
        return this;
    }

    public List<String> getAlias() {
        return alias;
    }

    public User setAlias(List<String> alias) {
        this.alias = alias;
        return this;
    }


}
