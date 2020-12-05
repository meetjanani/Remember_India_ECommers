package com.rememberindia.remember4u.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rememberindia.remember4u.Fragment.Login_Fragment;
import com.rememberindia.remember4u.Fragment.SignUp_Fragment;
import com.rememberindia.remember4u.R;

import java.util.ArrayList;
import java.util.List;

public class Login_Activity extends AppCompatActivity{

    private static final String[] pageTitle = {"SIGN IN","SIGN UP"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        setTitle("Welcome");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Welcome");
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_loginsignup4);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_loginsignup4);
        LoginSignup4Adapter adapter = new LoginSignup4Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);






    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnCreateAccount:
//
//                Intent in = new Intent(Login_Activity.this , MainActivity.class);
//                startActivity(in);
//                break;
//        }
//    }

    public class LoginSignup4Adapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public LoginSignup4Adapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            fragments.add(new Login_Fragment());
            fragments.add(new SignUp_Fragment());


        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int arrayPos) {
            return pageTitle[arrayPos];
        }
    }

}
