package com.hellobaby.library.data.model;

import java.util.List;

/**
 * 作者：ShuWen
 * 时间： 2017/12/29.10:47
 * 描述：
 */

public class SchoolGradeModel {

    /**
     * actived : false
     * gradeId : 101
     * name : 小班
     * children : [{"actived":false,"gradeId":101,"classId":1,"name":"普通班33","schoolId":1},{"actived":false,"gradeId":101,"classId":2,"name":"vip班1","schoolId":1},{"actived":false,"gradeId":101,"classId":5,"name":"普通班2","schoolId":1},{"actived":false,"gradeId":101,"classId":7,"name":"普通班1","schoolId":1}]
     */

    private boolean actived;
    private int gradeId;
    private String name;
    private boolean isSelect = false;
    /**
     * actived : false
     * gradeId : 101
     * classId : 1
     * name : 普通班33
     * schoolId : 1
     */

    private List<ChildrenBean> children;

    public boolean isActived() {
        return actived;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        private boolean actived;
        private int gradeId;
        private int classId;
        private String name;
        private int schoolId;
        private boolean isSelect = false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isActived() {
            return actived;
        }

        public void setActived(boolean actived) {
            this.actived = actived;
        }

        public int getGradeId() {
            return gradeId;
        }

        public void setGradeId(int gradeId) {
            this.gradeId = gradeId;
        }

        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }
    }
}
