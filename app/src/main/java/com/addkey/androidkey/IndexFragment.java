package com.addkey.androidkey;

import android.os.Bundle;
import android.util.Log;

import com.addkey.keylibrary.base.BaseFragment;

/**
 * @author Created by ligaoyuan
 * @date 2020-04-20
 */
public class IndexFragment extends BaseFragment {
    private static final String TAG = "IndexFragment";
    private MainActivity mMainActivity;
    public static IndexFragment getInstance(){
        return new IndexFragment();
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public void onBackPressedInFragment() {
        super.onBackPressedInFragment();
        Log.i(TAG, "onBackPressedInFragment: ");
        mMainActivity.getSupportFragmentManager().popBackStack();
    }
}
