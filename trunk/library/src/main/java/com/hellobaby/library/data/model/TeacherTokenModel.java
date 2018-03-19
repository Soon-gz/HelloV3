package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/11/16.
 */

public class TeacherTokenModel {

    /**
     * role : 1：公司    2：学校   3：老师
     * token : 8529dd6ccc304ef0bbb7551c91255ad4
     * role : 1
     */

    private String token;
    private int role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "TeacherTokenModel{" +
                "token='" + token + '\'' +
                ", role=" + role +
                '}';
    }
}
