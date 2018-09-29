package com.xuancao.programframes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.xuancao.programframes.R;

/**
 * 加载框
 */
public class PLoadingView extends RelativeLayout implements ILoading {

    public PLoadingView(Context context) {
        super(context);
        initialize();
    }

    public PLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @Override
    public void show() {
        this.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        this.setVisibility(View.GONE);
        setClickable(false);
        setClickable(true);
    }

    @Override
    public void onError() {
    }

    @Override
    public void onDataEmpty() {
    }

    @Override
    public void setOnRetryAction(Runnable action) {

    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(
                R.layout.p_view_loading_layuot, this);
        setClickable(false);
        this.setVisibility(View.GONE);
    }
}
