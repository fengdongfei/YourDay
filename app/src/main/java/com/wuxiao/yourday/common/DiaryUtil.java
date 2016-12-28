package com.wuxiao.yourday.common;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.wuxiao.yourday.common.popup.WeatherPopup.getMenu;

/**
 * Created by masai on 2016/12/27.
 */

public class DiaryUtil {

    @BindingAdapter({"setTheme"})
    public static void setCalendarTheme(View view, int color) {
        view.setBackgroundColor(color);
    }

    @BindingAdapter({"setWeatherIcon"})
    public static void setWeatherIcon(ImageView view, int resid) {
        int icon = getMenu(view.getContext()).get(resid).icon;
        view.setImageResource(icon);
    }

    @BindingAdapter({"setThemeBg"})
    public static void setThemeBg(View view, Drawable background) {
        view.setBackground(background);
    }

    @BindingAdapter({"setTextColor"})
    public static void setTextColor(TextView view, int resid) {
        view.setTextColor(resid);
    }

}
