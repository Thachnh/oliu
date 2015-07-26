package com.vhackclub.oliu.event_planner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.vhackclub.oliu.R;

/**
 * Created by geruk on 7/25/15.
 */
public class ListFriendlyViewPager extends ViewPager {

    private boolean mLastTouchIntercepted = false;
    private boolean mEnableHscrollReliableSwiping = false;
    private int mTouchDownStartX, mTouchDownStartY;
    private boolean mIsStartPointRecorded;
    private int mVerticalDragThreshold;
    private int mHorizontalDragThreshold;

    public ListFriendlyViewPager(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setPageMargin(context.getResources().getDimensionPixelSize(R.dimen.pager_margin));
        final ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mVerticalDragThreshold = viewConfiguration.getScaledTouchSlop();
        mHorizontalDragThreshold = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        mIsStartPointRecorded = false;
    }

    public ListFriendlyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
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


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!doesViewPagerHaveContent()) {
            return false;
        }

        if (!mLastTouchIntercepted) {
            // if onTouchEvent fires but the ViewPager has not intercepted the touch event, it means
            // that the touch was started on an empty bit inside this ViewPager (in which case we get a
            // single call to onInterceptTouchEvent and all following calls to onTouchEvent). If we
            // return false here, the view pager will stop receiving all touch events, but if we let it
            // handle everything normally, the viewpager will steal all touch events.

            // this lets us continues receiving touch events (by returning true) but the view pager
            // won't actually react to them until the proper touch slops have been exceeded.
            onInterceptTouchEvent(ev);
            return true;
        }

        // If the ViewPager is not going to handle this touch event, then ask the parent ListView to
        // intercept it
        boolean viewPagerHandled = super.onTouchEvent(ev);
        if (!viewPagerHandled) {
            getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        }
        if (mEnableHscrollReliableSwiping) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_MOVE:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                default:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
            }
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // This will record the starting point of a motion.
                // We need to handle this or else we don't get the subsequent ACTION_MOVE.
                mTouchDownStartX = (int) ev.getRawX();
                mTouchDownStartY = (int) ev.getRawY();
                mIsStartPointRecorded = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                // If the whole unit is a click target, we will not get ACTION_DOWN.
                // In that case, we need to initialize the starting point in the first
                // ACTION_MOVE.
                if (!mIsStartPointRecorded) {
                    mIsStartPointRecorded = true;
                    mTouchDownStartX = (int) ev.getRawX();
                    mTouchDownStartY = (int) ev.getRawY();
                    return true;
                }
                int distanceX = Math.abs((int) ev.getRawX() - mTouchDownStartX);
                int distanceY = Math.abs((int) ev.getRawY() - mTouchDownStartY);
                // Mostly  horizontal movement and distance greater than threshold.
                if (distanceX > distanceY && distanceX > mHorizontalDragThreshold) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                } else if (distanceY > distanceX && distanceY > mVerticalDragThreshold) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                } else {
                    return true;
                }
            default:
                // We get here when the motion is finished (MOTION_UP) or
                // cancelled (ACTION_CANCEL)
                mIsStartPointRecorded = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                return false;
        }
    }

    public boolean doesViewPagerHaveContent() {
        PagerAdapter adapter = getAdapter();
        return adapter != null && adapter.getCount() > 0;
    }
}
