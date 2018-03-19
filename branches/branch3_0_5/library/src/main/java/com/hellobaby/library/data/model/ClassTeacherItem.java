package com.hellobaby.library.data.model;

import java.io.Serializable;

public class ClassTeacherItem implements Serializable {
    //    {"state":"0","result":[{"name":"苍老师","mobile_phone":"18628023869"}]}
    private String name;
    private String mobile_phone;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }
}
