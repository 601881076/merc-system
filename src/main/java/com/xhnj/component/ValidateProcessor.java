package com.xhnj.component;

import cn.hutool.extra.spring.SpringUtil;
import com.xhnj.annotation.BusinValidator;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.proxy.ValidatorTemplateProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;

/*
 @Description 执行器
 *@author kang.li
 *@date 2021/8/14 13:06   
 */
@Slf4j
@Component
public class ValidateProcessor {
    /**
     * 执行业务类型对应的校验器
     *
     * @param validateType
     */
    public void validate(String validateType) {
        if (StringUtils.isEmpty(validateType)) {
            throw new IllegalArgumentException("validateType cannot be null");
        }
        long start = System.currentTimeMillis();
        log.info("start validate，validateType={}，ValidatorContext={}", validateType, BusinValidatorContext.getCurrentContext().toString());
        List<ValidatorTemplateProxy> validatorList = getValidatorList(validateType);
        if (CollectionUtils.isEmpty(validatorList)) {
            log.info("validatorList is empty");
            return;
        }
        ValidatorTemplateProxy validateProcessorProxy;
        for (ValidatorTemplateProxy validatorTemplate : validatorList) {
            validateProcessorProxy = validatorTemplate;
            log.info("{} is running", validateProcessorProxy.getValidatorTemplate().getClass().getSimpleName());
            validatorTemplate.validate();
        }
        log.info("end validate，validateType={}，ValidatorContext={}，time consuming {} ms", validateType,
                BusinValidatorContext.getCurrentContext().toString(), (System.currentTimeMillis() - start));
    }


    /**
     * 根据Validator注解的validateType获取所有带有该注解的校验器
     *
     * @param validateType
     * @return
     */
    private List<ValidatorTemplateProxy> getValidatorList(String validateType) {
        List<ValidatorTemplateProxy> validatorTemplateList = new LinkedList<>();
        Map<String, Object> map = SpringUtil.getApplicationContext().getBeansWithAnnotation(BusinValidator.class);
        String[] validateTypes;
        int validateOrder;
        Annotation annotation;
        for (Map.Entry<String, Object> item : map.entrySet()) {
            annotation = item.getValue().getClass().getAnnotation(BusinValidator.class);
            if(annotation == null && "withholdBatchValidator".equals(item.getKey())){
                annotation = WithholdBatchValidator.class.getAnnotation(BusinValidator.class);
            }
            validateTypes = ((BusinValidator) annotation).validateTypes();
            validateOrder = ((BusinValidator) annotation).validateOrder();
            if (item.getValue() instanceof BusinValidatorTemplate) {
                if (Arrays.asList(validateTypes).contains(validateType)) {
                    validatorTemplateList.add(new ValidatorTemplateProxy((BusinValidatorTemplate) item.getValue(), validateType, validateOrder));
                }
            } else {
                log.info("{}not extend from ValidatorTemplate", item.getKey());
            }
        }
        Collections.sort(validatorTemplateList);
        return validatorTemplateList;
    }
}
