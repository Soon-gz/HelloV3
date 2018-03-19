package com.hellobaby.library.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/9/23.
 * description :
 */

public class JsonUtils {

    private static Gson gson = new Gson();

    /**
     * Json2一个T
     * @param json
     * @param clzz
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJson(String json, Class<T> clzz) {
        List<T> ps = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            ps.add(gson.fromJson(elem, clzz));
        }
        return ps;
    }
}
