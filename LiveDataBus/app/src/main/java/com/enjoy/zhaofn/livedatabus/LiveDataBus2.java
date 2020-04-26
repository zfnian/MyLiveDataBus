package com.xiangxue.alvin.livedatabus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 享学课堂 Alvin
 * @package com.xiangxue.alvin.livedatabus
 * @fileName LiveDataBus2
 * @date on 2019/1/10
 * @qq 2464061231
 **/
public class LiveDataBus2 {

    private static LiveDataBus2 mInstance;
    private Map<String, MyMutableLiveData> mLiveDatas = new HashMap<>();

    private LiveDataBus2() {

    }
    public static LiveDataBus2 get() {
        if (mInstance == null) {
            synchronized (LiveDataBus2.class) {
                if (mInstance == null) {
                    mInstance = new LiveDataBus2();
                }
            }
        }
        return mInstance;
    }

    public <T> MyMutableLiveData<T> with(String key, Class<T> type) {
        if (!mLiveDatas.containsKey(key)) {
            mLiveDatas.put(key, new MyMutableLiveData());
        }
        return mLiveDatas.get(key);
    }

    public MyMutableLiveData<Object> with(String key) {
        return with(key,Object.class);
    }

    public <T> void post(String key, T t) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            with(key).setValue(t);
        } else {
            with(key).postValue(t);
        }
    }

    public static class MyMutableLiveData<T> extends MutableLiveData<T> {
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, observer);
            try {
                hook(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void hook(@NonNull Observer<T> observer) throws Exception{
            Class<LiveData> classLiveData = LiveData.class;
            Field fieldObservers = classLiveData.getDeclaredField("mObservers");
            fieldObservers.setAccessible(true);
            Object mObservers = fieldObservers.get(this);
            Class<?> classObservers = mObservers.getClass();

            Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
            methodGet.setAccessible(true);
            Object objectWrapperEntry = methodGet.invoke(mObservers, observer);
            Object objectWrapper = ((Map.Entry)objectWrapperEntry).getValue();
            Class<?> classObserverWrapper =objectWrapper.getClass().getSuperclass();

            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
            fieldLastVersion.setAccessible(true);
            Field fieldVersion = classLiveData.getDeclaredField("mVersion");
            fieldVersion.setAccessible(true);
            Object objectVersion = fieldVersion.get(this);
            fieldLastVersion.set(objectWrapper, objectVersion);
        }
    }

}
