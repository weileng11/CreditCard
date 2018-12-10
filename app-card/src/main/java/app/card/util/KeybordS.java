package app.card.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @class describe
 * @anthor ${bruce} QQ:275762645
 * @time 2018/1/2 10:47
 * @change
 * @chang time
 * @class describe
 */
public class KeybordS
{
    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param
     * @param
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);

            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

//    /**
//     * 只要有就关闭软键盘
//     *
//     * @param mContext
//     */
//    public static void hideKeyboard(Activity mContext) {
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {
//            if (mContext.getCurrentFocus().getWindowToken() != null) {
//                imm.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//    }

    /**
     * 强制隐藏软键盘
     */
    public static void hideKeyboard(Activity mContext) {
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        ((InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        mContext.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
