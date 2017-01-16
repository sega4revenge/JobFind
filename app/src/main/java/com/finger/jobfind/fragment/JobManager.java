package com.finger.jobfind.fragment;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finger.jobfind.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by VinhNguyen on 12/29/2016.
 */

public class JobManager extends Fragment  {
    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentTabHost mTabHost;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.jobmanager,container,false);
         fragmentManager = getFragmentManager();
         fragmentTransaction = fragmentManager.beginTransaction();
        JobSaveFragment fragment = new JobSaveFragment();
        fragmentTransaction.add(R.id.content, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        BottomBar bottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_jobsave) {
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    JobSaveFragment fragment = new JobSaveFragment();
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }else if(tabId == R.id.tab_jobapply){
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    RecruitmentFragment fragment2 = new RecruitmentFragment();
                    fragmentTransaction.replace(R.id.content, fragment2);
                    fragmentTransaction.commitAllowingStateLoss();
                }else{
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    NotificationFragment fragment2 = new NotificationFragment();
                    fragmentTransaction.replace(R.id.content, fragment2);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        });
//        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);
//        setupTabIcons();
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//             int pos =tab.getPosition();
//                switch (pos){
//                    case 0:
//                        JobSaveFragment fragment = new JobSaveFragment();
//                        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.content, fragment);
//                        fragmentTransaction.commitAllowingStateLoss();
//                        break;
//                    case 1:
//                        RecruitmentFragment fragment2 = new RecruitmentFragment();
//                        FragmentTransaction fragmentTransaction2= getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction2.replace(R.id.content, fragment2);
//                        fragmentTransaction2.commitAllowingStateLoss();
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        return view;
    }

}
