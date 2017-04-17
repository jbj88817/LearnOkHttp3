package us.bojie.learnokhttp.okhttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bojiejiang on 4/16/17.
 */

public class SampleHttpClient {


    private SampleHttpClient() {}

    public void enqueue(BaseCallBack callBack) {

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

        public SampleHttpClient build() {
            return new SampleHttpClient();
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
