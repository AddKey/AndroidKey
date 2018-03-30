package com.addkey.keylibrary.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.addkey.keylibrary.utils.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:Created by lgy on 2018-03-29.
 * 邮箱:635286598@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void init();

    protected abstract int setLayoutRes();

    private static List<Activity> activities = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //默认带有EditText的界面不弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        setContentView(setLayoutRes());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        activities.add(this);
        init();
    }

    @Override
    protected void onDestroy() {
        activities.remove(this);
        super.onDestroy();
    }
    public void finishActivity() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }
    //   隐藏键盘
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideKeyboard(v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，
     * 来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            back();
            return  true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    //当点击头部返回键时实现按物理返回键的效果，例如打开软键盘点击物理返回键是先关闭软键盘
    public void back() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Instrumentation instrumentation  = new Instrumentation();
                instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            }
        };
        ThreadPoolUtils.getInstance().getFixedThreadPool(5).execute(runnable);
    }

    /**
     * activity跳转
     * @param clz
     */
    public void startActivity(Class<?> clz){
        startActivity(new Intent(this,clz));
    }
    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(new Intent(this, clz), requestCode);
    }
    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    //网络监听Receiver



    //view和Receive


    //Service


}
