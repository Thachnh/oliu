package com.vhackclub.oliu.event_planner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.vhackclub.oliu.R;

/**
 * Created by geruk on 7/25/15.
 */
public class ListFriendlyViewPager extends ViewPager {

    public ListFriendlyViewPager(Context context) {
        super(context);
        setPageMargin(context.getResources().getDimensionPixelSize(R.dimen.pager_margin));
    }

    public ListFriendlyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageMargin(context.getResources().getDimensionPixelSize(R.dimen.pager_margin));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            return;
        }
        View firstChild = getChildAt(0);
        int measuredHeight = firstChild.getMeasuredHeight();
        int padding = getPaddingBottom() + getPaddingTop();
        if (isMeasureValid(measuredHeight)) {
            setMeasuredDimension(getMeasuredWidth(), measuredHeight + padding);
            return;
        }
        int childWidthSize = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.AT_MOST);
        int childHeightMeasureSpec = MeasureSpec.UNSPECIFIED;
        firstChild.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        int newMeasureSpect = MeasureSpec.makeMeasureSpec(
                firstChild.getMeasuredHeight() + getPaddingTop() + getPaddingBottom(),
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newMeasureSpect);

    }

    private boolean isMeasureValid(int size) {
        return size > 0 && size < 0xffff;
    }
}
