package com.zz.personal.Utils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * Created by zz on 2017.07.07.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        return JSON.toJSONString(object);
    }

    public static Map<String, Object> toMap(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, Map.class);
    }

    public static <T> T toObject(String json, Class<T> classOfT) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, classOfT);
    }

    public static <T> List<T> toList(String json, Class<T> classOfT) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseArray(json, classOfT);
    }
}
