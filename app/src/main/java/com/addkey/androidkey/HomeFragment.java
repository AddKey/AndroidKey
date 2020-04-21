package com.addkey.androidkey;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.addkey.keylibrary.base.BaseFragment;
import com.bumptech.glide.util.LogTime;

import javax.security.auth.login.LoginException;

/**
 * @author Created by ligaoyuan
 * @date 2020-04-20
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private Button mButton;
    private MainActivity mMainActivity;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        mButton = getFragmentView().findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.addFragment(IndexFragment.getInstance());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBackPressedInFragment() {
        super.onBackPressedInFragment();
        Log.i(TAG, "onBackPressedInFragment: ");
        mMainActivity.getSupportFragmentManager().popBackStack();
    }
}
