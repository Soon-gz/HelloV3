package com.abings.baby.teacher.ui.recipes;

import java.util.List;

/**
 * Created by zwj on 2016/12/27.
 * description :
 */

public class RecipesBean {
    private String date;
    private List<String> breakfasts;
    private List<String> lunchs;

    public RecipesBean(String date, List<String> breakfasts, List<String> lunchs) {
        this.date = date;
        this.breakfasts = breakfasts;
        this.lunchs = lunchs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getBreakfasts() {
        return breakfasts;
    }

    public void setBreakfasts(List<String> breakfasts) {
        this.breakfasts = breakfasts;
    }

    public List<String> getLunchs() {
        return lunchs;
    }

    public void setLunchs(List<String> lunchs) {
        this.lunchs = lunchs;
    }
}
