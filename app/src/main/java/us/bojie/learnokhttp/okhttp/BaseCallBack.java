package us.bojie.learnokhttp.okhttp;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by bojiejiang on 4/16/17.
 */

public abstract class BaseCallBack<T> {

    public Type mType;

    public BaseCallBack() {
        mType = getSuperclassTypeParameter(this.getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public abstract void onSuccess(T t);
    public abstract void onError(int code);
    public abstract void onFailure(Call call, IOException e);

}
