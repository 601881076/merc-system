package com.xhnj.component;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
 @Description 表格监听工具类
 *@author kang.li
 *@date 2021/9/19 14:55   
 */
@Slf4j
@Component
public class ExcelListener extends AnalysisEventListener {
    //可以通过实例获取该值
    private List<Object> datas = new ArrayList<Object>();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info("invok.....");
        datas.add(o);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
        // doSomething(o);//根据自己业务做处理
    }

    private void doSomething(Object object) {
        //1、入库调用接口，可在这里写，也可在业务层写
    }

    public List<Object> getDatas() {
        log.info("获取");
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("after..");
    }
}
