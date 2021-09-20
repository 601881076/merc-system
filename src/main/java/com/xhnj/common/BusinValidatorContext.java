package com.xhnj.common;

import java.util.concurrent.ConcurrentHashMap;

/*
 @Description 业务校验器上下文，与当前线程绑定
 *@author kang.li
 *@date 2021/8/14 12:52   
 */
public class BusinValidatorContext extends ConcurrentHashMap<String, Object> {

    /**
     * 请求对象
     */
    public Object requestDto;

    protected static final ThreadLocal<? extends BusinValidatorContext> threadLocal = ThreadLocal.withInitial(() -> new BusinValidatorContext());

    /**
     * 获取当前线程的上下文
     *
     * @return
     */
    public static BusinValidatorContext getCurrentContext() {
        BusinValidatorContext context = threadLocal.get();
        return context;
    }


    /**
     * 设值
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        if (value != null) put(key, value);
        else remove(key);
    }


    /**
     * 获取String值
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return (String) get(key);
    }

    /**
     * 获取Integer值
     *
     * @param key
     * @return
     */
    public Integer getInteger(String key) {
        return (Integer) get(key);
    }

    /**
     * 获取Boolean值
     *
     * @param key
     * @return
     */
    public Boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    /**
     * 获取对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getClazz(String key) {
        return (T) get(key);
    }

    /**
     * 获取Long值
     *
     * @param key
     * @return
     */
    public Long getLong(String key) {
        return (Long) get(key);
    }


    public <T> T getRequestDto() {
        return (T) requestDto;
    }

    public void setRequestDto(Object requestDto) {
        this.requestDto = requestDto;
    }
}
