package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/5/10.
 */

public class PrizeAddressModel {

    /**
     * shareImgeString : a_share.jpg
     * name : 朱老师
     * headImageurl : b1d237c64cad491b941aba60996a17c0.bmp
     * goodsName : 超市充值卡
     * iContactInfo : {"id":8,"name":"小宋","phoneNum":"15858686815","fullAddress":"海门大街","locationArea":"浙江 台州 椒江区","createId":4,"isDefault":null}
     */

    private String shareImgeString;
    private String name;
    private String headImageurl;
    private String goodsName;
    /**
     * id : 8
     * name : 小宋
     * phoneNum : 15858686815
     * fullAddress : 海门大街
     * locationArea : 浙江 台州 椒江区
     * createId : 4
     * isDefault : null
     */

    private IContactInfoBean iContactInfo;

    public String getShareImgeString() {
        return shareImgeString;
    }

    public void setShareImgeString(String shareImgeString) {
        this.shareImgeString = shareImgeString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public IContactInfoBean getIContactInfo() {
        return iContactInfo;
    }

    public void setIContactInfo(IContactInfoBean iContactInfo) {
        this.iContactInfo = iContactInfo;
    }

    public static class IContactInfoBean {
        private int id;
        private String name;
        private String phoneNum;
        private String fullAddress;
        private String locationArea;
        private int createId;
        private Object isDefault;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getLocationArea() {
            return locationArea;
        }

        public void setLocationArea(String locationArea) {
            this.locationArea = locationArea;
        }

        public int getCreateId() {
            return createId;
        }

        public void setCreateId(int createId) {
            this.createId = createId;
        }

        public Object getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(Object isDefault) {
            this.isDefault = isDefault;
        }
    }

    @Override
    public String toString() {
        return "PrizeAddressModel{" +
                "shareImgeString='" + shareImgeString + '\'' +
                ", name='" + name + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", iContactInfo=" + iContactInfo +
                '}';
    }
}
