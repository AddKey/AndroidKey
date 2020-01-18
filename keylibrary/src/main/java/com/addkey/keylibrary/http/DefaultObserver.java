package com.addkey.keylibrary.http;

import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

import com.addkey.keylibrary.utils.LogUtils;
import com.addkey.keylibrary.utils.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 通用的返回观察者类
 * @param <T>
 */
public abstract class DefaultObserver<T> implements Observer<T> {
    private static final String TAG = "DefaultObserver";
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        }else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFinish();
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    public void onFail(String message) {
        Log.e(TAG, "onFail: "+message);
        onError();
    }

    public void onError(){};
    public void onFinish(){}
    public void onNetLinkError(){}

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        onError();
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.show("链接失败,请检查网络连接", Toast.LENGTH_LONG);
                onNetLinkError();
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.show("链接超时", Toast.LENGTH_SHORT);
                break;

            case BAD_NETWORK:
                ToastUtils.show("网络失败", Toast.LENGTH_SHORT);
                break;

            case PARSE_ERROR:
//                ToastUtils.show("解析失败", Toast.LENGTH_SHORT);
                Log.e(TAG, "onException: 解析失败" );
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.show("未知错误", Toast.LENGTH_SHORT);
                break;
        }
    }


    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
