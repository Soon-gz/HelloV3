package com.hellobaby.library.data.model;

import android.support.annotation.NonNull;

import com.hellobaby.library.Const;

import java.io.Serializable;

/**
 *
 */
public class Contact implements Comparable<Contact>, Serializable {
    private int id;
    private String pinyin;
    private char firstChar;
    private String name;
    private String phone;
    private String headImageurl;
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    //    private UserModel userModel;//宝宝的家庭联系人
//    private ClassTeacherItem classTeacherItem;//这个无意义
    private boolean isTeacher = false;


    public boolean isTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//    public ClassTeacherItem getClassTeacherItem() {
//        return classTeacherItem;
//    }
//
//    public void setClassTeacherItem(ClassTeacherItem classTeacherItem) {
//        this.classTeacherItem = classTeacherItem;
//    }
//
//
//    public UserModel getUserContact() {
//        return userModel;
//    }
//
//    public void setUserContact(UserModel userModel) {
//        this.userModel = userModel;
//    }


    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
        String first = pinyin.substring(0, 1);
        if (first.matches("[A-Za-z]")) {
            firstChar = first.toUpperCase().charAt(0);
        } else {
            firstChar = '#';
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    /**
     * @return 教师头像
     */
    public String getTeacherHeadImageurlAbs() {
        return Const.URL_TeacherHead + headImageurl;
    }

    /**
     * @return 宝宝头像的缩图
     */
    public String getBabyHeadImageurlAbs() {
        return Const.URL_BabyHead + headImageurl;
    }


    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public char getFirstChar() {
        return firstChar;
    }

    @Override
    public int compareTo(@NonNull Contact another) {
        return this.pinyin.compareTo(another.getPinyin());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Contact) {
            if (((Contact) o).isTeacher) {
                return this.getName().equals(((Contact) o).getName());
            } else {
                return this.id == ((Contact) o).getId();
            }

        } else {
            return super.equals(o);
        }
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", pinyin='" + pinyin + '\'' +
                ", firstChar=" + firstChar +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", isTeacher=" + isTeacher +
                '}';
    }
}