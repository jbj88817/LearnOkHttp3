package us.bojie.learnokhttp.okhttp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by bojiejiang on 4/16/17.
 */

public class SimpleHttpClient {

    private static final String TAG = SimpleHttpClient.class.getSimpleName();
    private Builder mBuilder;

    private SimpleHttpClient(Builder builder) {
        mBuilder = builder;
    }

    public Request buildRequest() {
        Request.Builder builder = new Request.Builder();

        if (mBuilder.method == "GET") {
            builder.url(buildGetRequestParam());
            builder.get();
        } else if(mBuilder.method == "POST") {
            try {
                builder.post(buildRequestBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            builder.url(mBuilder.url);
        }
        return builder.build();
    }

    private RequestBody buildRequestBody() throws JSONException{
        if (mBuilder.isJSONParams) {
            JSONObject jsonObject = new JSONObject();
            for (RequestParam param : mBuilder.mRequestParams) {
                jsonObject.put(param.getKey(), param.getValue());
            }
            String json = jsonObject.toString();
            Log.d(TAG, "buildRequestBody: " + json);
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        } else {
            FormBody.Builder builder = new FormBody.Builder();

            for (RequestParam param : mBuilder.mRequestParams) {
                builder.add(param.getKey(), param.getValue() == null?"": param.getValue().toString());
            }
            return builder.build();
        }
    }

    private String buildGetRequestParam() {
        if (mBuilder.mRequestParams.size() <= 0) {
            return this.mBuilder.url;
        }

        Uri.Builder builder = Uri.parse(this.mBuilder.url).buildUpon();

        for (RequestParam requestParam : mBuilder.mRequestParams) {
            builder.appendQueryParameter(requestParam.getKey(),
                    requestParam.getValue() == null ? "": requestParam.getValue().toString());
        }
        String url = builder.build().toString();
        Log.d(TAG, "buildGetRequestParam: " + url);

        return url;
    }

    public void enqueue(BaseCallBack callBack) {
        OkHttpManager.getInstance().request(this, callBack);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static class Builder {

        private String url;
        private String method;
        private List<RequestParam> mRequestParams;
        private boolean isJSONParams = false;

        private Builder() {
            method = "GET";

        }

        public SimpleHttpClient build() {
            return new SimpleHttpClient(this);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            method = "GET";
            return this;
        }

        // post Form
        public Builder post() {
            method = "POST";
            return this;
        }

        // post Json
        public Builder json() {
            isJSONParams = true;
            return post();
        }

        public Builder addParam(String key, Object value) {
            if (mRequestParams == null) {
                mRequestParams = new ArrayList<>();
            }
            mRequestParams.add(new RequestParam(key, value));

            return this;
        }
    }
}
