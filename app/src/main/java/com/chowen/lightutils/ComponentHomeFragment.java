package com.chowen.lightutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chowen.cn.library.pagekit.FragmentWrapper;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhouwen
 * @version 0.1
 * @since 16/7/23
 */
public class ComponentHomeFragment extends FragmentWrapper {

    private SparseArray<Fragment> mFragmentArray = new SparseArray<>();

    private List<String> mFragmentTitle = new ArrayList<>();

    private ViewPager mViewPager;

    private TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_test, null);
        initFragments();
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initFragments() {
        mFragmentArray.append(0, new BaseComponentFragment());
        mFragmentArray.append(1, new BizComponentFragment());
        mFragmentArray.append(2, new CommonWidgetFragment());
        mFragmentArray.append(3, new CommonFrameFragment());

        mFragmentTitle.add("基础组件");
        mFragmentTitle.add("业务组件");
        mFragmentTitle.add("通用控件");
        mFragmentTitle.add("通用框架");
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

