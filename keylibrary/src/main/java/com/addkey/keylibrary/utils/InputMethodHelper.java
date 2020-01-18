package com.addkey.keylibrary.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Created by ligaoyuan
 * @date 2019-06-27
 */
public class InputMethodHelper {
    private InputMethodManager mInputMethodManager;
    private  static  InputMethodHelper sInputMethodHelper;

    public InputMethodHelper(Context context) {
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public synchronized static InputMethodHelper getInstance(Context context){
        synchronized (InputMethodHelper.class){
            if (sInputMethodHelper ==null){
                sInputMethodHelper = new InputMethodHelper(context);
            }
        }
        return sInputMethodHelper;
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public void showSoftInput(View view) {
        mInputMethodManager.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        mInputMethodManager.restartInput(view);
    }
    /**
     * 隐藏输入法
     */
    public void hideSoftInput(View view) {
        if (mInputMethodManager.isActive()) {
            Log.d("hickey", "hideSoftInput:" + "hideSoftInputFromWindow");
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void toggleSoftInput(){
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
