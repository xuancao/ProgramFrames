package com.xuancao.programframes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuancao.programframes.R;
import com.xuancao.programframes.utils.ClickCommonUtil;


/**
 * 顶部导航栏
 *
 */
public class PTitleBarView extends RelativeLayout {

    protected Context mContext;
    protected RelativeLayout mLayout;
    protected TextView mTitle;
    protected TextView mLeftBtn;
    protected TextView mRightBtn;

    protected ITitleBar mITitleBar;

    public PTitleBarView(Context context) {
        super(context);
        onInitialize(context);
    }

    public PTitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitialize(context);
    }

    public PTitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitialize(context);
    }

    private void onInitialize(Context context) {
        mContext = context;
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);
        mLayout = (RelativeLayout) findViewById(R.id.p_title_bar_root_layout);
        mTitle = (TextView) findViewById(R.id.p_title_bar_title);
        mLeftBtn = (TextView) findViewById(R.id.p_title_bar_left_btn);
        mRightBtn = (TextView) findViewById(R.id.p_title_bar_right_btn);
        this.setVisibility(View.GONE);
    }

    public void setBackgroundColorResource(int color) {
        mLayout.setBackgroundColor(color);
    }

    public void setPageTitle(String title) {
        mTitle.setText(title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mITitleBar != null) {
                    mITitleBar.onTitlePressed();
                }
            }
        });
        this.setVisibility(View.VISIBLE);
    }


    public void setPageLeftBackDrawable(Context context, int drawable) {
        if (drawable == -1) {
        } else if (drawable == 0) {
            mLeftBtn.setCompoundDrawables(null, null,
                    null, null);
        } else {
            mLeftBtn.setCompoundDrawables(ClickCommonUtil.getDrawableResource(context, drawable), null,
                    null, null);
        }
        mLeftBtn.setVisibility(View.VISIBLE);
        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mITitleBar != null) {
                    mITitleBar.onTitleLeftTipPressed();
                }
            }
        });
        this.setVisibility(View.VISIBLE);
    }

    public void setBackDrawable(Context context, int drawable, final ITitleBar titleBar) {

        mLeftBtn.setCompoundDrawables(ClickCommonUtil.getDrawableResource(context, drawable), null, null, null);
        mLeftBtn.setVisibility(View.VISIBLE);
        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mITitleBar != null) {
                    mITitleBar.onTitleLeftTipPressed();
                } else {
                    titleBar.onTitleLeftTipPressed();
                }
            }
        });
        this.setVisibility(View.VISIBLE);
    }

    public void setPageRightBtn(Context context, int drawable, String tip) {
        if (drawable == -1) {
        } else if (drawable == 0) {
            mRightBtn.setCompoundDrawables(null, null,
                    null, null);
        } else {
            mRightBtn.setCompoundDrawables(ClickCommonUtil.getDrawableResource(context, drawable), null,
                    null, null);
        }
        mRightBtn.setText(tip);
        mRightBtn.setVisibility(View.VISIBLE);
        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mITitleBar != null) {
                    mITitleBar.onTitleRightTipPressed();
                }
            }
        });
        this.setVisibility(View.VISIBLE);
    }


    public void setPageRightBtn(Context context, int drawable, String tip, int color) {
        mRightBtn.setTextColor(color);
        setPageRightBtn(context,drawable,tip);
    }

    protected int getLayoutResId() {
        return R.layout.p_view_titlebar_layuot;
    }

    public void setTitleBar(ITitleBar iTitlebar) {
        this.mITitleBar = iTitlebar;
    }


    public void setPageStyle() {
        mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.common_color_FFFFFF));
        mTitle.setTextColor(mContext.getResources().getColor(R.color.common_color_333333));
        mLeftBtn.setCompoundDrawables(ClickCommonUtil.getDrawableResource(mContext, R.mipmap.ic_back_)
                , null, null, null);
    }
}
