package us.bojie.learnokhttp.okhttp;

import java.io.IOException;

/**
 * Created by bojiejiang on 4/16/17.
 */

public interface BaseCallBack<T> {
    public void onSuccess(T t);
    public void onError(int code);
    public void onFailure(IOException e);

}
