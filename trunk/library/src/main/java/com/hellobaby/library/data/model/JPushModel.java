package com.hellobaby.library.data.model;


/**
 * Created by ShuWen on 2017/2/22.
 */

public class JPushModel {

    /**
     * careUserId : 10
     * type : 想要关注你的宝宝
     * alert : 超神他爹想要关注你的宝宝超神悟空1
     */

    private JsonObjectBean jsonObject;

    public JsonObjectBean getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObjectBean jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static class JsonObjectBean {
        private int careUserId;
        private String type;
        private String alert;
        private int babyId;

        public int getBabyId() {
            return babyId;
        }

        public void setBabyId(int babyId) {
            this.babyId = babyId;
        }

        public int getCareUserId() {
            return careUserId;
        }

        public void setCareUserId(int careUserId) {
            this.careUserId = careUserId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        @Override
        public String toString() {
            return "JsonObjectBean{" +
                    "careUserId=" + careUserId +
                    ", type='" + type + '\'' +
                    ", alert='" + alert + '\'' +
                    ", babyId=" + babyId +
                    '}';
        }
    }
}
