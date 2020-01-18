package com.addkey.keylibrary.http.converter;

import com.addkey.keylibrary.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by lgy on 2019/1/17
 */
public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "CustomizeGsonResponseBo";

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomizeGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //把responsebody转为string
        String response = value.string();
        LogUtils.i(TAG,response);
        try {
            return adapter.fromJson(response);
        } finally {
            value.close();
        }
    }
}

