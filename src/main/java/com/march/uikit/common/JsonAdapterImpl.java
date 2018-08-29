package com.march.uikit.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.march.common.adapter.JsonAdapter;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/4/3
 * Describe :
 *
 * @author chendong
 */
public class JsonAdapterImpl implements JsonAdapter {

    private Gson sGson = new Gson();

    @Override
    public String toJson(Object object) {
        return sGson.toJson(object);
    }

    @Override
    public <T> T toObj(String json, Class<T> cls) {
        return sGson.fromJson(json, cls);
    }

    @Override
    public <T> List<T> toList(String json, Class<T> clazz) {
        return sGson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    @Override
    public <K, V> Map<K, V> toMap(String json, Class<K> kClazz, Class<V> vClazz) {
        return sGson.fromJson(json, new TypeToken<Map<K, V>>() {
        }.getType());
    }


}
