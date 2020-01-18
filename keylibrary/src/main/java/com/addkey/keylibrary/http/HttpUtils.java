package com.addkey.keylibrary.http;

import android.content.Context;

import com.addkey.keylibrary.http.converter.CustomizeGsonConverterFactory;
import com.addkey.keylibrary.utils.LogUtils;

import java.io.File;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 网络请求工具类，使用方法：1.在application里进行初始化，赋值context和url
 *                      2.定义一个全局的UrlService.class（也就是你定义的api接口文件）的实现对象，
 *                        例如在application里定义一个静态变量 ：sHttp = HttpUtils.getService(UrlService.class)
 *                        使用时直接在调用的地方 sHttp.Login(username,password) 就可以了
 * @author Created by ligaoyuan
 * @date 2020-01-18
 */
public class HttpUtils {
    private static Integer DEFAULT_TIMEOUT = 10000;
    private static Context mContext;
    private static String mBaseUrl;
    private static Retrofit sRetrofit;

    /**
     * 初始化时直接把baseUrl赋值
     * @param context 为了创建缓存文件
     * @param httpBaseUrl 网络请求url
     */
    public static void init(Context context, String httpBaseUrl) {
        mContext = context;
        mBaseUrl = httpBaseUrl;
    }

    private static OkHttpClient getOkHttpClientBuilder() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    LogUtils.e("OKHttp-----", URLDecoder.decode(message, "utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("OKHttp-----", message);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(mContext.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//100MB

        return new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }


    /**
     * 使用时 在app定义一个全局的 接口实现对象，防止重复创建
     * @param api  正式应用定义的api接口
     * @param <T>  返回的api接口实现
     * @return
     */
    public static <T> T getService(Class<T> api) {
        if (sRetrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(getOkHttpClientBuilder())
                    .addConverterFactory(CustomizeGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(mBaseUrl);
            sRetrofit = builder.build();
        }
        return sRetrofit.create(api);
    }
}
