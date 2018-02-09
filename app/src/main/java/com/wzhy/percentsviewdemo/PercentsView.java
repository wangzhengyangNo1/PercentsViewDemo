package com.wzhy.percentsviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhegnyang on 2018-2-8 0008.
 */

public class PercentsView extends LinearLayout {

    private Context context;
    private PopupWindow mNoticeWidow;
    private LayoutInflater mInflater;

    private float mLimitPercent = 0;
    private List<Integer> mColorList;
    private View mContentView;

    public PercentsView(Context context) {
        this(context, null);
    }

    public PercentsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        mNoticeWidow = new PopupWindow(context);
        mInflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        setNoticeView(R.layout.layout_notice);
        mNoticeWidow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mNoticeWidow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mNoticeWidow.setOutsideTouchable(true);
        mNoticeWidow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mClickedItemView.getLayoutParams().height = mItemHeight;
                requestLayout();
            }
        });
        initColorList();
    }

    public void initColorList(){
        mColorList = new ArrayList<>();
        mColorList.add(Color.parseColor("#ff4c23"));
        mColorList.add(Color.parseColor("#ff7919"));
        mColorList.add(Color.parseColor("#ff9f19"));
        mColorList.add(Color.parseColor("#e6d739"));
        mColorList.add(Color.parseColor("#74d941"));
        mColorList.add(Color.parseColor("#0fc0c5"));
        mColorList.add(Color.parseColor("#3d99f5"));
        mColorList.add(Color.parseColor("#7961f2"));
        mColorList.add(Color.parseColor("#aa80ff"));
        mColorList.add(Color.parseColor("#cd9aff"));
        mColorList.add(Color.parseColor("#e667e6"));
        mColorList.add(Color.parseColor("#ff59ac"));
    }

    public void setNoticeView(@LayoutRes int layoutRes){
        mContentView = mInflater.inflate(layoutRes, this, false);
        mNoticeWidow.setContentView(mContentView);
    }


    public void setNoticeView(View noticeView){
        if (null != noticeView) {
            mNoticeWidow.setContentView(noticeView);
        }
    }

    public void addItem(float percent, @ColorInt int color, int index){
        Button tvItem = new Button(context);
        LayoutParams lp = new LayoutParams(0, mItemHeight, percent);
        tvItem.setBackgroundColor(color);
        String text = "";
        if (percent >= mLimitPercent) {
            text = String.valueOf(Math.round(100 * percent)).concat("%");
        }
        tvItem.setText(text);
        tvItem.setTag(index);
        tvItem.setTextColor(Color.WHITE);
        //lp.setMargins(1, 0, 1, 0);
        tvItem.setLines(1);
        tvItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, 16);
        tvItem.setGravity(Gravity.CENTER);
        tvItem.setOnClickListener(getOnClickListener());
        addView(tvItem, lp);
    }

    private int mItemHeight = 60;


    public void setItemHeight(int height){
        this.mItemHeight = height;
    }

    /**
     * 设置百分比列表
     * @param percentList
     */
    public void setPercentList(List<Float> percentList){
        if (percentList == null) return;
        if (getChildCount() > 0) {
            if (mContentView != null) {
                mNoticeWidow.dismiss();
            }
            updatePercentList(percentList);
        } else {

            for (int i = 0; i < percentList.size(); i++) {
                float percent = percentList.get(i);
                int color = mColorList.get(i % mColorList.size());
                addItem(percent, color, i);
            }
            requestLayout();
        }
    }

    /**
     * 更新数据列表
     * @param percentList 百分比数据列表
     */
    public void updatePercentList(List<Float> percentList){
        if (percentList == null) return;

        if (mContentView != null) {
            mNoticeWidow.dismiss();
        }

        int diff = getChildCount() - percentList.size();

        if (diff > 0) {
            for (int i = percentList.size(); i < getChildCount(); i++) {
                removeViewAt(i);
            }
        } else if (diff < 0) {
            for (int i = getChildCount(); i < percentList.size(); i++) {
                addItem(percentList.get(i), mColorList.get(i % mColorList.size()), i);
            }
        }

        for (int i = 0; i < percentList.size(); i++) {
            Float percent = percentList.get(i);
            TextView child = (TextView) getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.weight = percent;
            String text = "";
            if (percent >= mLimitPercent) {
                text = String.valueOf(Math.round(100 * percent)).concat("%");
            }
            child.setText(text);

        }
        requestLayout();

    }

    /**
     * 设置百分比限制值，小于这个值的百分数不显示
     * @param limitPercent 百分比限制值
     */
    public void setLimitPercent(float limitPercent){
        this.mLimitPercent = limitPercent;
        requestLayout();
    }

    private TextView mClickedItemView = null;

    private OnClickListener mOnClickListener;

    public OnClickListener getOnClickListener(){
        if (mOnClickListener == null) {
            mOnClickListener = new OnClickListener() {
                @Override
                public void onClick(final View v) {
//                    if (mClickedItemView != null) {
//                        mNoticeWidow.dismiss();
//                        mClickedItemView.getLayoutParams().height = mItemHeight;
//                    }
                    if (mNoticeWidow .equals(v)) {

                    }


                    mClickedItemView = (TextView) v;
                    mClickedItemView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                    requestLayout();

                    if (mOnItemSelectedListener != null && mContentView != null) {
                        int tag = (int) v.getTag();
                        mOnItemSelectedListener.onItemSelected(mContentView, tag);
                    }

                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            mNoticeWidow.showAtLocation(
//                                    PercentsView.this, Gravity.TOP | Gravity.START,
//                                    PercentsView.this.getLeft() + v.getLeft() + v.getWidth() - 240,
//                                    PercentsView.this.getTop()
//                            );
                            mNoticeWidow.showAsDropDown(v, v.getWidth() - 246, -(getHeight() + 142));

                        }
                    }, 50);
                }
            };
        }

        return mOnClickListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View contentView, int index);
    }
    private OnItemSelectedListener mOnItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.mOnItemSelectedListener = listener;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
