package com.wuxiao.yourday.home;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wuxiao.yourday.R;
import com.wuxiao.yourday.calendar.CalendarFragment;
import com.wuxiao.yourday.common.ThemeManager;
import com.wuxiao.yourday.diary.DiaryFragment;
import com.wuxiao.yourday.note.NoteFragment;
import com.wuxiao.yourday.tab.HomeTabLayout;
import com.wuxiao.yourday.tab.OnTabSelectListener;
import com.wuxiao.yourday.viewpager.FragmentViewPager;
import com.wuxiao.yourday.viewpager.adapters.FragmentStatePagerAdapter;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import rx.Observable;

public class HomeActivity extends AppCompatActivity {


    private String[] mTitles = {"Entries", "Calender", "Diary"};
    private HomeTabLayout home_tl;
    private FragmentViewPager home_vp;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_tl = (HomeTabLayout) findViewById(R.id.home_tl);
        home_vp = (FragmentViewPager) findViewById(R.id.home_vp);

        mFragments.add(NoteFragment.newInstance());
        mFragments.add(CalendarFragment.newInstance());
        mFragments.add(DiaryFragment.newInstance());
        load_home();

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.e(TAG, "{accept}permission.name=" + permission.name);
                        Log.e(TAG, "{accept}permission.granted=" + permission.granted);
//                        if(permission.name.equals(Manifest.permission.READ_PHONE_STATE) && permission.granted){
//                            // 已经获取权限
//                            Toast.makeText(HomeActivity.this, "已经获取权限", Toast.LENGTH_SHORT).show();
//                        }

                        //执行顺序——【多个权限的情况，只有所有的权限均允许的情况下granted==true】
                        if (permission.granted) {
                            Toast.makeText(HomeActivity.this, "已获取权限，可以干想干的咯", Toast.LENGTH_LONG);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Toast.makeText(HomeActivity.this, "用户拒绝了该权限，没有选中『不再询问』", Toast.LENGTH_LONG);
                        } else {
                            Toast.makeText(HomeActivity.this, "用户拒绝了该权限，并且选中『不再询问』", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "{accept}");//可能是授权异常的情况下的处理
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "{run}");//执行顺序——2
                    }
                });

// rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CAMERA)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean) {
//                            Toast.makeText(HomeActivity.this, "已获取权限，可以干想干的咯", Toast.LENGTH_LONG)
//                                    .show();
//                        } else {
//                            //只有用户拒绝开启权限，且选了不再提示时，才会走这里，否则会一直请求开启
//                            Toast.makeText(HomeActivity.this, "主人，我被禁止啦，去设置权限设置那把我打开哟", Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    }
//                });


    }

    private void load_home() {
        home_vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        home_tl.setIndicatorColor(ThemeManager.getInstance().getThemeColor(this));
        home_tl.setTextUnselectColor(ThemeManager.getInstance().getThemeColor(this));
        home_tl.setDividerColor(ThemeManager.getInstance().getThemeColor(this));

        home_tl.setTabData(mTitles);
        home_tl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                home_vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        home_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                home_tl.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        home_vp.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment instantiateFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }


    }


    public void setHomeCurrentItem(int position) {
        home_vp.setCurrentItem(position);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        home_vp.notifyPagerVisible();
    }

    // *********************************************************************************************
    @Override
    public void onPause() {
        super.onPause();
        home_vp.notifyPagerInvisible();
    }

}
