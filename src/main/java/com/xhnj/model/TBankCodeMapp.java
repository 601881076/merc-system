package com.xhnj.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: lk
 * DateTime: 2021-12-06 20:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TBankCodeMapp implements Serializable {
    private static final long serialVersionUID = 1L;

    private String businCode;

    private String bankCode;

    private String bankName;

    private String bankEnName;

    private String bankFullName;

    private String url;

    private String enabled;

}
