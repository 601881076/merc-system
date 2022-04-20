package com.mercsystem.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BusinessException.java
 * @Description: 业务异常处理
 * @ProjectName com.mercsystem.common.exception
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:00
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private String msg;
}
