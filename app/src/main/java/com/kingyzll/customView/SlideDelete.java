package com.kingyzll.customView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SlideDelete extends ViewGroup {

    private View leftView;
    private View midView;
    private View rightView;
    private ViewDragHelper helper;

    //三个构造器超重要
    public SlideDelete(Context context) {
        this(context, null);
    }

    public SlideDelete(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDelete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = ViewDragHelper.create(this, callback);
    }


    //实现滑动
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            //left左边距离
            //左负右正

            if (child == leftView) {
                if (left > 0) {
                    left = 0;
                } else if (left <= -rightView.getMeasuredWidth()) {
                    left = -rightView.getMeasuredWidth();
                }
            } else if (child == midView) {
                if (left >= leftView.getMeasuredWidth()) {
                    left = leftView.getMeasuredWidth();
                } else if (left <= -rightView.getMeasuredWidth()) {
                    left = 0;
                }
            } else if (child == rightView) {
                if (left <= midView.getMeasuredWidth() - rightView.getMeasuredWidth()) {
                    left = midView.getMeasuredWidth() - rightView.getMeasuredWidth();
                } else if (left >= getMeasuredWidth()) {
                    left = getMeasuredWidth();
                }
            }
            return left;
        }


        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx,
                                          int dy) {
            //dx dy 移动了多少的x和y
            if (changedView == leftView) {
                midView.layout(midView.getLeft() + dx, 0, midView.getRight() + dx,
                        midView.getBottom() + dy);
                rightView.layout(rightView.getLeft() + dx, 0, getRight() + dx,
                        rightView.getBottom() + dy);
            } else if (changedView == midView) {
                leftView.layout(leftView.getLeft() + dx, 0, leftView.getRight() + dx,
                        leftView.getBottom() + dy);
                rightView.layout(rightView.getLeft() + dx, 0, getRight() + dx,
                        rightView.getBottom() + dy);
            } else if (changedView == rightView) {
                leftView.layout(leftView.getLeft() + dx, 0, leftView.getRight() + dx,
                        leftView.getBottom() + dy);
                midView.layout(midView.getLeft() + dx, 0, midView.getRight() + dx,
                        midView.getBottom() + dy);
            }
        }


        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (releasedChild == midView) {
                if (rightView.getLeft() < getMeasuredWidth() - rightView.getMeasuredWidth() / 2) {
                    helper.smoothSlideViewTo(rightView,
                            getMeasuredWidth() - rightView.getMeasuredWidth(), 0);
                    invalidate();
                } else {
                    helper.smoothSlideViewTo(rightView, getMeasuredWidth(), 0);
                    invalidate();
                }
            } else {
                helper.smoothSlideViewTo(rightView, getMeasuredWidth(), 0);
                invalidate();
            }
        }

    };


    @Override
    public void computeScroll() {
        if (helper.continueSettling(true)) {
            // invalidate();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        leftView = getChildAt(0);
        midView = getChildAt(1);
        rightView = getChildAt(2);

        int leftL = 0;
        int leftT = 0;
        int leftR = leftView.getMeasuredWidth();
        int leftB = leftView.getMeasuredHeight();
        leftView.layout(leftL, leftT, leftR, leftB);

        int midL = leftView.getMeasuredWidth();
        int midT = 0;
        int midR = midView.getMeasuredWidth();
        int midB = midView.getMeasuredHeight();
        midView.layout(midL, midT, midR, midB);

        int rightL = getMeasuredWidth();
        int rightT = 0;
        int rightR =getMeasuredWidth() + rightView.getMeasuredWidth();
        int rightB = rightView.getMeasuredHeight();
        rightView.layout(rightL, rightT, rightR, rightB);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //回调处理
        helper.processTouchEvent(event);
        return true;
    }
}

