package com.xhnj.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BatchNoVO {
    @NotNull(message = "totalTrans param cannot be empty")
    private Integer totalTrans;

    @NotNull(message = "totalAmt param cannot be empty")
    private BigDecimal totalAmt;

    private String password;
}
