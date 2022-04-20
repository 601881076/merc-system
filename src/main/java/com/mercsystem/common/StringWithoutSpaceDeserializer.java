package com.mercsystem.common;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @ClassName StringWithoutSpaceDeserializer.java
 * @Description: 处理前端传过来的空字符串参数
 * @ProjectName com.mercsystem.common
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:00
*/
public class StringWithoutSpaceDeserializer extends StdDeserializer<String> {
    private static final long serialVersionUID = -6972065572263950443L;

    public StringWithoutSpaceDeserializer(Class<String> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        if(StrUtil.isBlank(p.getText())){
            return null;
        }
        return p.getText().trim();
    }
}
