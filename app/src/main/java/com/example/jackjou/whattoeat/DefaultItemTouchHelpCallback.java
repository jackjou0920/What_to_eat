package com.example.jackjou.whattoeat;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by JackJou on 2016/12/1.
 */

public class DefaultItemTouchHelpCallback extends ItemTouchHelper.Callback {

    /*Item操作的回调*/
    private OnItemTouchCallbackListener onItemTouchCallbackListener;
    /*是否可以拖拽*/
    private boolean isCanDrag = false;
    /*是否可以被滑动*/
    private boolean isCanSwipe = false;

    public DefaultItemTouchHelpCallback(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    //设置Item操作的回调，去更新UI和數據源
    public void setOnItemTouchCallbackListener(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    //设置是否可以被拖拽
    public void setDragEnable(boolean canDrag) {
        isCanDrag = canDrag;
    }

    //设置是否可以被滑动
    public void setSwipeEnable(boolean canSwipe) {
        isCanSwipe = canSwipe;
    }

    //當Item被長按的時候是否可以被拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return isCanDrag;
    }

    //Item是否可以被滑動(H：左右滑動，V：上下滑動)
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isCanSwipe;
    }

    //當用户拖拽或者滑動Item的時候需要我們告訴系统滑動或者拖拽的方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) { // GridLayoutManager
            // flag如果值是0，相當於這個功能被關閉
            int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlag = 0;
            // create make
            return makeMovementFlags(dragFlag, swipeFlag);
        } else if (layoutManager instanceof LinearLayoutManager) { // linearLayoutManager
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();

            int dragFlag = 0;
            int swipeFlag = 0;

            // 為了方便理解，相當於分為橫著的ListView和豎著的ListView
            if (orientation == LinearLayoutManager.HORIZONTAL) { // 如果是横向的布局
                swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else if (orientation == LinearLayoutManager.VERTICAL) {// 如果是竖向的布局，相当于ListView
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlag, swipeFlag);
        }
        return 0;
    }

    //當Item被拖拽的時候被回調
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcViewHolder, RecyclerView.ViewHolder targetViewHolder) {
        if (onItemTouchCallbackListener != null) {
            return onItemTouchCallbackListener.onMove(srcViewHolder.getAdapterPosition(), targetViewHolder.getAdapterPosition());
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (onItemTouchCallbackListener != null) {
            onItemTouchCallbackListener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    public interface OnItemTouchCallbackListener {
        //當某個Item被滑動删除的時候
        void onSwiped(int adapterPosition);
        //當两個Item位置互换的時候被回調
        boolean onMove(int srcPosition, int targetPosition);
    }
}
