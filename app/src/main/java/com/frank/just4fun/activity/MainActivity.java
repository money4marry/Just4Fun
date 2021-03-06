package com.frank.just4fun.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.just4fun.R;
import com.frank.just4fun.base.BaseActivity;
import com.frank.just4fun.fragment.HomeFragment;
import com.frank.just4fun.fragment.SecondFragment;
import com.umeng.fb.FeedbackAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Context mContext = MainActivity.this;
//    private FeedbackAgent agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(R.string.app_name);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // 监听Drawer拉出、隐藏
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 设置MenuItem默认选中项
        navigationView.getMenu().getItem(0).setChecked(true);

        switchFragment(new HomeFragment());

        // 友盟用户反馈
//        agent = new FeedbackAgent(mContext);
//        agent.sync();
//        agent.closeAudioFeedback();

    }

    // 跳转Fragment
    private void switchFragment(Fragment newFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, newFragment).commit();
    }

    private void switchFragment(Fragment newFragment, String url) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("url", url);
        newFragment.setArguments(args);
        fragmentTransaction.replace(R.id.main_fragment, newFragment).commit();
    }

    long exitTime = 0;

    // 返回键
    @Override
    public void onBackPressed() {
        //如果侧栏开启首先关闭侧边栏
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), R.string.main_exit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    // 菜单键
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return false;
    }

    //onConfigurationChanged事件并不是只有屏幕方向改变才可以触发，
    // 其他的一些系统设置改变也可以触发，比如打开或者隐藏键盘。
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    // NavigationItem点击
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            switchFragment(new HomeFragment());
            mToolbarTitle.setText(R.string.app_name);
        } else if (id == R.id.nav_meitu) {
            switchFragment(new SecondFragment(), "http://www.juzimi.com/meitumeiju?page=");
            mToolbarTitle.setText(R.string.nav_meitu_text);
        } else if (id == R.id.nav_shouxie) {
            switchFragment(new SecondFragment(), "http://www.juzimi.com/meitumeiju/shouxiemeiju?page=");
            mToolbarTitle.setText(R.string.nav_shouxie_text);
        } else if (id == R.id.nav_duibai) {
            switchFragment(new SecondFragment(), "http://www.juzimi.com/meitumeiju/jingdianduibai?page=");
            mToolbarTitle.setText(R.string.nav_duibai_text);
        } else if (id == R.id.nav_share) {
            /** 分享 **/
            String shareContent = mContext.getResources().getString(
                    R.string.share_content);
            Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
            intent.setType("text/plain"); // 分享发送的数据类型
            intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
            startActivity(Intent.createChooser(intent, "分享"));// 目标应用选择对话框的标题
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(mContext, AboutActivity.class));
        }

        // 关闭侧栏
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
