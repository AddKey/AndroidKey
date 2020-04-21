package com.addkey.keylibrary.base;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Created by ligaoyuan
 * @date 2020-03-26
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private View mFragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mFragmentView  = inflater.inflate(getLayoutId(), container, false);
        init(savedInstanceState);
        return mFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    onBackPressedInFragment();
                    return true;
                }
                return false;
            }
        });
    }

    public View getFragmentView(){
        return mFragmentView;
    }
    protected abstract void init(Bundle savedInstanceState);


    protected abstract int getLayoutId();


    public  void onBackPressedInFragment(){
        Log.i(TAG, "onBackPressedInFragment: ");
    }

}
