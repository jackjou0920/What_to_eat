package android.support.v7.widget.helper;

/**
 * Created by JackJou on 2016/12/1.
 */

public class MyItemTouchHelper extends ItemTouchHelper {

    public MyItemTouchHelper(Callback callback) {
        super(callback);
    }

    public Callback getCallback() {
        return mCallback;
    }
}
