package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/7/3.
 */

public class AttendenceTeacherModel {

    /**
     * teacherId : 3
     * schoolId : 1
     * teacherName : 李松阳123456
     * teacherPassword : F59BD65F7EDAFB087A81D4DCA06C4910
     * phoneNum : 18989619204
     * headImageurl : 20918bc81de741cc96f65bf8b22bc89c.jpg
     * createTime : 2017-05-08 14:34:05
     * smsCode : qwe
     * token : toke
     * tokenExpiredate : 2016-12-14 14:43:38
     * position : èä½
     * teacherEmail : 12160@qq.com
     * isPublic : 1
     * lastLoginTime : 2017-07-03 15:07:14
     * status : 1
     * flag : 3
     * adminFlag : 0
     */

    private TeacherBean teacher;

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public static class TeacherBean {
        private int teacherId;
        private int schoolId;
        private String teacherName;
        private String teacherPassword;
        private String phoneNum;
        private String headImageurl;
        private String createTime;
        private String smsCode;
        private String token;
        private String tokenExpiredate;
        private String position;
        private String teacherEmail;
        private int isPublic;
        private String lastLoginTime;
        private int status;
        private int flag;
        private int adminFlag;

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherPassword() {
            return teacherPassword;
        }

        public void setTeacherPassword(String teacherPassword) {
            this.teacherPassword = teacherPassword;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getHeadImageurl() {
            return headImageurl;
        }

        public void setHeadImageurl(String headImageurl) {
            this.headImageurl = headImageurl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(String smsCode) {
            this.smsCode = smsCode;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenExpiredate() {
            return tokenExpiredate;
        }

        public void setTokenExpiredate(String tokenExpiredate) {
            this.tokenExpiredate = tokenExpiredate;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTeacherEmail() {
            return teacherEmail;
        }

        public void setTeacherEmail(String teacherEmail) {
            this.teacherEmail = teacherEmail;
        }

        public int getIsPublic() {
            return isPublic;
        }

        public void setIsPublic(int isPublic) {
            this.isPublic = isPublic;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getAdminFlag() {
            return adminFlag;
        }

        public void setAdminFlag(int adminFlag) {
            this.adminFlag = adminFlag;
        }

        @Override
        public String toString() {
            return "TeacherBean{" +
                    "teacherId=" + teacherId +
                    ", schoolId=" + schoolId +
                    ", teacherName='" + teacherName + '\'' +
                    ", teacherPassword='" + teacherPassword + '\'' +
                    ", phoneNum='" + phoneNum + '\'' +
                    ", headImageurl='" + headImageurl + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", smsCode='" + smsCode + '\'' +
                    ", token='" + token + '\'' +
                    ", tokenExpiredate='" + tokenExpiredate + '\'' +
                    ", position='" + position + '\'' +
                    ", teacherEmail='" + teacherEmail + '\'' +
                    ", isPublic=" + isPublic +
                    ", lastLoginTime='" + lastLoginTime + '\'' +
                    ", status=" + status +
                    ", flag=" + flag +
                    ", adminFlag=" + adminFlag +
                    '}';
        }
    }
}
