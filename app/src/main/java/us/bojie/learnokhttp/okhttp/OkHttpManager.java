package us.bojie.learnokhttp.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by bojiejiang on 4/16/17.
 */

public class OkHttpManager {

    private static OkHttpManager sManager;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;

    private OkHttpManager() {
        initOkHttp();
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();

    }

    public static synchronized OkHttpManager getInstance() {
        if (sManager == null) {
            sManager = new OkHttpManager();
        }

        return sManager;
    }

    private void initOkHttp() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public void request(SimpleHttpClient client, final BaseCallBack callBack) {

        if (callBack == null) {
            throw new NullPointerException("callback is null");
        }

        mOkHttpClient.newCall(client.buildRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendOnFailureMessage(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    if (callBack.mType == null || callBack.mType == String.class) {
                        sendOnSuccessMessage(callBack, result);
                    } else {
                        sendOnSuccessMessage(callBack, mGson.fromJson(result, callBack.mType));
                    }
                    if (response.body() != null) {
                        response.body().close();
                    }
                } else {
                    sendOnErrorMessage(callBack, response.code());
                }
            }
        });
    }

    private void sendOnFailureMessage(final BaseCallBack callBack, final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });
    }

    private void sendOnErrorMessage(final BaseCallBack callBack, final int code) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(code);
            }
        });
    }

    private void sendOnSuccessMessage(final BaseCallBack callBack, final Object result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(result);
            }
        });
    }
}
