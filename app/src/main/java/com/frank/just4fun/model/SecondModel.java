package com.frank.just4fun.model;

import java.io.Serializable;

public class SecondModel implements Serializable {
    private String mTitle;
    private String mImageUrl;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIamgeUrl() {
        return mImageUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        mImageUrl = iamgeUrl;
    }
}
