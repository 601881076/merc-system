package com.mercsystem.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.mercsystem.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.lang.reflect.Field;
import java.util.*;

/*
 @Description
 *@author tanyi
 *@date 2021/2/24 10:11
 */
@Component
@Slf4j
public class FieldRepeatValidatorUtils {
    /**
     * 实体类中id字段
     */
    private String idColumnName;

    /**
     * 实体类中id的值
     */
    private Object idColumnValue;

    /**
     * 校验字段值 - 字符串、数字、对象...
     */
    private static Object fieldValue;

    /**
     * 实体类对象值
     */
    private static Object object;

    /**
     *
     * @param fields 验证的字段数组
     * @param message 如果不满足返回的消息
     * @param o 实体类
     * @return
     */
    public boolean fieldRepeat(String [] fields,String message,Object o){
        try {
            // 没有校验的值返回true
            if(fields != null && fields.length == 0){
                return true;
            }
            checkUpdateOrSave(o);
            checkRepeat(fields,o,message);
            return true;
        }catch (Exception e){
//            String msg = "验证字段是否重复报错";
//            log.error(msg,e);
            String msg = "";
            if(e instanceof BusinessException)
                msg = ((BusinessException) e).getMsg();
            throw new BusinessException(msg);
        }
    }

    /**
     * 通过传入的实体类中 @TableId 注解的值是否为空，来判断是更新还是保存
     * 将值id值和id列名赋值
     * id的值不为空 是更新 否则是插入
     * @param o 被注解修饰过的实体类
     * @return
     */
    public void checkUpdateOrSave(Object o) throws Exception{
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field f:fields) {
            // 设置私有属性可读
            f.setAccessible(true);
            if(f.isAnnotationPresent(TableId.class)){
                TableId tableId = f.getAnnotation(TableId.class);
                idColumnName = tableId.value();
                idColumnValue = f.get(o);
            }
        }
    }

    /**
     * 通过传入的字段值获取数据是否重复
     * @param fields
     * @param o
     * @param message
     * @return
     */
    public void checkRepeat(String [] fields, Object o, String message){
        Model model = (Model) o;
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String, Object> queryMap = getColumns(fields,o);
        Iterator<Map.Entry<String, Object>> it = queryMap.entrySet().iterator();

        List list = null;
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            queryWrapper.eq(entry.getKey(), entry.getValue());

            list = model.selectList(queryWrapper);
            if (idColumnValue == null){
                if (!CollectionUtils.isEmpty(list)){
                    throw new BusinessException(message);
                }
            } else {
                if(!(idColumnValue instanceof Long)){
                    throw new BusinessException("id不是Long类型");
                }
                FieldRepeatValidatorUtils.object = model.selectById((Long) idColumnValue);
                if (!CollectionUtils.isEmpty(list) && object != null){
                    Field[] sefields = object.getClass().getDeclaredFields();
                    String fName = null;
                    for (Field f : sefields) {
                        f.setAccessible(true);
                        fName = f.getName();
                        if ( f.isAnnotationPresent(TableField.class) && !"updateTime".equals(fName)) {
                            try {
                                fieldValue = f.get(object);
                                if(fieldValue != null && !(fieldValue.toString().equals(queryMap.get(fName).toString())) || list.size() > 1){
                                    throw new BusinessException(message);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 多条件判断唯一性，将我们的属性和值组装在map中，方便后续拼接条件
     * @param fields
     * @param o
     * @return
     */
    public Map<String,Object> getColumns(String [] fields, Object o){
        Field[] fieldList = o.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field f : fieldList) {
            f.setAccessible(true);
            // 判断字段是否包含在数组中，如果存在，则将它对应的列字段放入map中
            if(ArrayUtils.contains(fields, f.getName())){
                getMapData(map,f,o);
            }
        }
        return map;
    }

    /**
     * 得到查询条件
     * @param map  列字段
     * @param f 字段
     * @param o 传入的对象
     */
    private void getMapData( Map<String,Object> map,Field f,Object o){
        try {
            if(f.isAnnotationPresent(TableField.class)){
                TableField tableField = f.getAnnotation(TableField.class);
                Object val = f.get(o);
                map.put(tableField.value(), val);
            }
        }catch (IllegalAccessException i){
            throw new BusinessException("获取字段的值报错");
        }
    }
}
