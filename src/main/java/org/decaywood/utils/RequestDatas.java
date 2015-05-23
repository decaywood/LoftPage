package org.decaywood.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by decaywood on 2015/5/23.
 */
public class RequestDatas implements Map<String, String> {

    private Map<String, String> datas;
    private HttpServletRequest request;

    public RequestDatas(HttpServletRequest request) {

        this.datas = new HashMap<>();

        if(request == null) return;

        this.request = request;
        initDatas(request);
    }

    public RequestDatas() {
        this(null);
    }

    private void initDatas(HttpServletRequest request) {

        Map<String, String[]> properties = request.getParameterMap();
        properties.forEach((key, value) -> {
            Optional<String[]> optional = Optional.ofNullable(value);
            String[] values = optional.orElseGet(() -> new String[]{""});
            String realValue = Arrays.stream(values).collect(Collectors.joining(","));
            datas.put(key, realValue);
        });

    }


    @Override
    public int size() {
        return datas.size();
    }

    @Override
    public boolean isEmpty() {
        return datas.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return datas.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return datas.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return datas.get(key);
    }

    @Override
    public String put(String key, String value) {
        return datas.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return datas.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        datas.putAll(m);
    }

    @Override
    public void clear() {
        datas.clear();
    }

    @Override
    public Set<String> keySet() {
        return datas.keySet();
    }

    @Override
    public Collection<String> values() {
        return datas.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return datas.entrySet();
    }
}
