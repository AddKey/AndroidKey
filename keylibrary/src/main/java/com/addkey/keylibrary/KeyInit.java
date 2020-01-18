package com.addkey.keylibrary;

import android.content.Context;

import com.addkey.keylibrary.http.HttpUtils;
import com.addkey.keylibrary.utils.ToastUtils;

/**
 * @author Created by ligaoyuan
 * @date 2020-01-18
 */
public class KeyInit {
    public static void init(Context context,String httpBaseUrl){
        ToastUtils.setContext(context);
        HttpUtils.init(context,httpBaseUrl);
    }
}
