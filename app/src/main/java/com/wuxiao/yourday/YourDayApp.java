package com.wuxiao.yourday;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.wuxiao.yourday.common.AMapLocationManager;
import com.wuxiao.yourday.common.GreenDaoManager;


/**
 * Created by wuxiaojian on 16/12/5.
 */
public class YourDayApp extends Application {


    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        GreenDaoManager.getInstance();
        AMapLocationManager.getInstance(this);
        Fresco.initialize(this);
        UMConfigure.init(mContext,UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        UMConfigure.setLogEnabled(true);
    }

    public static Context getContext() {
        return mContext;
    }


}
