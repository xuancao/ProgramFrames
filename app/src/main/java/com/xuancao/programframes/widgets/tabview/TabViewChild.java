package com.xuancao.programframes.widgets.tabview;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class TabViewChild {
    private int imageViewSelIcon;
    private int imageViewUnSelIcon;
    private int tabViewSelIcon;
    private int tabViewUnSelIcon;
    private String textViewText;
    private Fragment mFragment;

    public TabViewChild(int imageViewSelIcon, int imageViewUnSelIcon,
                        String textViewText, Fragment mFragment) {
        this.imageViewSelIcon = imageViewSelIcon;
        this.imageViewUnSelIcon=imageViewUnSelIcon;
        this.textViewText = textViewText;
        this.mFragment=mFragment;
    }

    public TabViewChild(String textViewText, Fragment mFragment, Bundle bundle) {
        this.textViewText = textViewText;
        this.mFragment=mFragment;
        this.mFragment.setArguments(bundle);
    }


    public TabViewChild(int imageViewSelIcon, int imageViewUnSelIcon,
                        int tabViewSelIcon, int tabViewUnSelIcon,
                        String textViewText, Fragment mFragment) {
        this.imageViewSelIcon = imageViewSelIcon;
        this.imageViewUnSelIcon=imageViewUnSelIcon;
        this.tabViewSelIcon = tabViewSelIcon;
        this.tabViewUnSelIcon =tabViewUnSelIcon;
        this.textViewText = textViewText;
        this.mFragment=mFragment;
    }


    public int getImageViewSelIcon() {
        return imageViewSelIcon;
    }


    public void setImageViewSelIcon(int imageViewSelIcon) {
        this.imageViewSelIcon = imageViewSelIcon;
    }


    public int getImageViewUnSelIcon() {
        return imageViewUnSelIcon;
    }


    public void setImageViewUnSelIcon(int imageViewUnSelIcon) {
        this.imageViewUnSelIcon = imageViewUnSelIcon;
    }

    public int getTabViewSelIcon() {
        return tabViewSelIcon;
    }

    public int getTabViewUnSelIcon() {
        return tabViewUnSelIcon;
    }


    public String getTextViewText() {
        return textViewText;
    }


    public void setTextViewText(String textViewText) {
        this.textViewText = textViewText;
    }


    public Fragment getmFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }
}
