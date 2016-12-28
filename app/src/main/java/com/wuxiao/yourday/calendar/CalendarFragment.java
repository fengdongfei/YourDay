package com.wuxiao.yourday.calendar;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wuxiao.yourday.R;
import com.wuxiao.yourday.base.BaseFragment;
import com.wuxiao.yourday.base.IPresenter;
import com.wuxiao.yourday.common.ThemeManager;
import com.wuxiao.yourday.databinding.FragmentCalenderBinding;
import com.wuxiao.yourday.home.HomeActivity;
import com.wuxiao.yourday.setting.SettingActivity;
import com.wuxiao.yourday.viewpager.FragmentVisibilityListener;

/**
 * Created by wuxiaojian on 16/12/5.
 */
public class CalendarFragment extends BaseFragment implements FragmentVisibilityListener, View.OnClickListener {

    private FrameLayout calender;
    private LinearLayout buttom_toolbar;
    private ImageView compile;
    private ImageView set;
    private FragmentCalenderBinding fragmentCalenderBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCalenderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender, container, false);
        calender = fragmentCalenderBinding.calender;
        fragmentCalenderBinding.setSetThemeBg(ThemeManager.getInstance().getBgDrawable(getActivity()));
        buttom_toolbar = (LinearLayout) fragmentCalenderBinding.getRoot().findViewById(R.id.buttom_toolbar);
        compile = (ImageView) fragmentCalenderBinding.getRoot().findViewById(R.id.compile);
        compile.setVisibility(View.VISIBLE);
        compile.setOnClickListener(this);
        set = (ImageView) fragmentCalenderBinding.getRoot().findViewById(R.id.set);
        set.setVisibility(View.VISIBLE);
        set.setOnClickListener(this);
        buttom_toolbar.setBackgroundColor(ThemeManager.getInstance().getThemeColor(getActivity()));
        CalendarWidget calendarWidget = new CalendarWidget(getActivity());
        calendarWidget.setTextColor(ThemeManager.getInstance().getThemeColor(getActivity()));
        calender.addView(calendarWidget);
        return fragmentCalenderBinding.getRoot();
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onFragmentVisible() {

    }

    @Override
    public void onFragmentInvisible() {
        calender.removeAllViews();
        CalendarWidget calendarWidget = new CalendarWidget(getActivity());
        calendarWidget.setTextColor(ThemeManager.getInstance().getThemeColor(getActivity()));
        calender.addView(calendarWidget);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.compile:
                HomeActivity home = (HomeActivity) getActivity();
                home.setHomeCurrentItem(2);
                break;
            case R.id.set:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }


    }
}
