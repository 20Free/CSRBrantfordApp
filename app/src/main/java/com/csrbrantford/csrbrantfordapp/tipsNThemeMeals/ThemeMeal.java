package com.csrbrantford.csrbrantfordapp.tipsNThemeMeals;

import android.graphics.drawable.BitmapDrawable;

import org.json.JSONObject;

/**
 * Created by Jonathan on 7/11/2016.
 */
public class ThemeMeal {

    String title;
    BitmapDrawable openThemeMeal;
    JSONObject themeMealObject;

    public ThemeMeal (String title, BitmapDrawable openThemeMeal, JSONObject themeMealObject){
        this.title = title;
        this.openThemeMeal = openThemeMeal;
        this.themeMealObject = themeMealObject;
    }

    public String getTitle() {
        return title;
    }

    public BitmapDrawable getOpenThemeMeal() {
        return openThemeMeal;
    }

    public JSONObject getThemeMealObject() {
        return themeMealObject;
    }
}
