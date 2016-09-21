package com.frank.just4fun.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frank.just4fun.R;
import com.frank.just4fun.base.BaseActivity;
import com.frank.just4fun.base.SwipeBackActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AboutMeActivity extends SwipeBackActivity {

    @InjectView(R.id.toolbar_return_text)
    ImageView mToolbarReturnText;
    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_me);
        ButterKnife.inject(this);

        mToolbarReturnText.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText("我的简介");

    }

    @OnClick(R.id.toolbar_return_text)
    public void onClick() {
        finish();
    }
}
