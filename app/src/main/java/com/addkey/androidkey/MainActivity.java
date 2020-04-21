package com.addkey.androidkey;


import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.fragment.app.FragmentTransaction;

import com.addkey.keylibrary.base.BaseActivity;
import com.addkey.keylibrary.base.BaseFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
//        addFragment(HomeFragment.getInstance());
        Button btn = findViewById(R.id.btn_home);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(HomeFragment.getInstance());
            }
        });

    }

    public void addFragment(BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_home, fragment).addToBackStack("homeAct").commit();

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_main;
    }

}
