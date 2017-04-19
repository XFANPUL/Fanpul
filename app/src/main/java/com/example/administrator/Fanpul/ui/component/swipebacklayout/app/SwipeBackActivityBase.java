package com.example.administrator.Fanpul.ui.component.swipebacklayout.app;


import com.example.administrator.Fanpul.ui.component.swipebacklayout.SwipeBackLayout;


public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    void scrollToFinishActivity();

}
