package com.chowen.lightutils;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;

import com.chowen.cn.library.ioc.annotations.field.InjectChildView;
import com.chowen.cn.library.ioc.annotations.field.InjectContentView;
import com.chowen.cn.library.log.Logger;
import com.chowen.lightutils.DBsimple.DBFragment;
import com.chowen.lightutils.base.BaseActivity;
import com.chowen.lightutils.httpsimple.HttpFragment;
import com.chowen.lightutils.iocsimple.IocSimpleFragment;
import com.chowen.lightutils.widget.WidgetFragment;

import java.util.ArrayList;
import java.util.List;

@InjectContentView(value = R.layout.activity_main_test)
public class MainActivity extends BaseActivity {

    private SparseArray<Fragment> mFragmentArray = new SparseArray<>();

    private List<String> mFragmentTitle = new ArrayList<>();

    @InjectChildView(R.id.container)
    private ViewPager mViewPager;

    @InjectChildView(R.id.toolbar)
    private Toolbar toolbar;

    @InjectChildView(R.id.tabs)
    private TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("chowen>>>>");
        initFragments();
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initFragments() {
        mFragmentArray.append(0, new DBFragment());
        mFragmentArray.append(1, new HttpFragment());
        mFragmentArray.append(2, new IocSimpleFragment());
        mFragmentArray.append(3, new WidgetFragment());


        mFragmentTitle.add("数据库");
        mFragmentTitle.add("网络");
        mFragmentTitle.add("View注解");
        mFragmentTitle.add("View控件");
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentArray.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentArray.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitle.get(position);
        }
    }
}
