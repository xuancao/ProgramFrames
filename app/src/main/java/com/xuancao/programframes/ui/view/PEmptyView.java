package com.xuancao.programframes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xuancao.programframes.R;


/**
 * 空视图
 */
public class PEmptyView extends RelativeLayout {

    protected Context mContext;
    protected ImageView mEmpty;

    OnClickEmptyListener mListener;

    public PEmptyView(Context context) {
        super(context);
        onInitialize(context);
    }

    public PEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitialize(context);
    }

    public PEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitialize(context);
    }

    private void onInitialize(Context context) {
        mContext = context;
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);
        mEmpty = (ImageView) findViewById(R.id.empty_view);
        this.setVisibility(View.GONE);
    }

    public void setEmptyVisibility(int visibility) {
        this.setVisibility(visibility);
    }

    public void show() {
        this.setVisibility(View.VISIBLE);
    }


    public void hide() {
        this.setVisibility(View.GONE);
    }

    public void setEmptyDrawable(int drawable) {
        mEmpty.setImageResource(drawable);
        this.setVisibility(View.VISIBLE);
    }

    public void setOnClickListener(OnClickEmptyListener listener) {
        mListener = listener;
        mEmpty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null)
                    mListener.call(v);
            }
        });
    }

    protected int getLayoutResId() {
        return R.layout.p_view_empty_layuot;
    }

    public interface OnClickEmptyListener {
        void call(View v);
    }

}
