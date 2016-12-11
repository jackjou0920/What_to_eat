package com.example.jackjou.whattoeat;
import android.support.v7.widget.helper.MyItemTouchHelper;
/**
 * Created by JackJou on 2016/12/1.
 */

public class DefaultItemTouchHelper extends MyItemTouchHelper{

    private DefaultItemTouchHelpCallback itemTouchHelpCallback;

    public DefaultItemTouchHelper(DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener) {
        super(new DefaultItemTouchHelpCallback(onItemTouchCallbackListener));
        itemTouchHelpCallback = (DefaultItemTouchHelpCallback) getCallback();
    }

    //設置是否可以被拖拽
    public void setDragEnable(boolean canDrag) {
        itemTouchHelpCallback.setDragEnable(canDrag);
    }

    //設置是否可以被滑动
    public void setSwipeEnable(boolean canSwipe) {
        itemTouchHelpCallback.setSwipeEnable(canSwipe);
    }

}
