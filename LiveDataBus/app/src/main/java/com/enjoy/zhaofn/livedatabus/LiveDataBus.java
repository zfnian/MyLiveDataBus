package com.xiangxue.alvin.livedatabus;

import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 享学课堂 Alvin
 * @package com.xiangxue.alvin.livedatabus
 * @fileName LiveDataBus
 * @date on 2019/1/9
 * @qq 2464061231
 **/
public class LiveDataBus {

    private final Map<String, MutableLiveData<Object>> bus;
    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingleHolder {
        private static final LiveDataBus DATA_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingleHolder.DATA_BUS;
    }

    public <T> MutableLiveData<T> getChannel(String target, Class<T> type) {
        if (!bus.containsKey(target)) {
            bus.put(target,new MutableLiveData<Object>());
        }
        return (MutableLiveData<T>) bus.get(target);
    }

    public MutableLiveData<Object> getChannel(String target) {
        return getChannel(target, Object.class);
    }
}
