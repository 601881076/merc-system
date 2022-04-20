package com.mercsystem.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/*
 @Description
 *@author tanyi
 *@date 2021/8/10 9:28   
 */
@Component
public class BusinUtil {
   /* @Value("${system.secritmap}")
    private String secritmap;*/

   /* public int getSourceType(String restcode){
        if(StrUtil.isBlank(restcode)){
            return 99;
        }
        JSONObject secritJson = null;
        int sourcesystem = 99;
        JSONArray secritArray = JSONUtil.parseArray(secritmap);
        for (int i = 0; i < secritArray.size(); i++){
            secritJson = secritArray.getJSONObject(i);
            if(restcode.equals(secritJson.getStr("restcode"))){
                sourcesystem = secritJson.getInt("sourcesystem");
                break;
            }
        }
        return sourcesystem;
    }*/

    /**
     *
     * @param sourbd
     * @return  0->分转元;  1->元转分
     */
    public BigDecimal coverAmount(BigDecimal sourbd, int type){
        if(sourbd == null){
            return  new BigDecimal("0");
        }
        BigDecimal b = new BigDecimal("100");
        BigDecimal couverbd = sourbd.divide(b);
        if(type == 1){
            couverbd = sourbd.multiply(b);
        }
        return couverbd;
    }

    /**
     * 生成订单号
     * @return
     */
    public String generateOrderNo(){
        StringBuilder sb = new StringBuilder();
        sb.append("MDD")
                .append(DateUtil.format(DateUtil.date(), "yyyyMMdd"))
                .append(RandomUtil.randomNumbers(10));
        return sb.toString();
    }

    /**
     * 银行卡脱敏 （截取后4位）
     * @param cardNo 卡号
     * @return 脱敏后的卡号
     */
    public String maskBankCard(String cardNo) {
        if (StrUtil.isBlank(cardNo)) { return ""; }
        int length = cardNo.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i < beforeLength || i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }
        return sb.toString();
    }

    /**
     * 手机号脱敏 （中间四位隐藏）
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public String maskPhone(String phone) {
        if (StrUtil.isBlank(phone)) {
            return "";
        }
        char[] chars = phone.toCharArray();
        int i = 3;
        chars[i++] = '*';
        chars[i++] = '*';
        chars[i++] = '*';
        chars[i] = '*';
        return new String(chars);
    }

    /**
     * 生成流水号
     */
    public String getBatchNo(String format, int n){
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtil.format(DateUtil.date(), format))
                .append(RandomUtil.randomNumbers(n));
        return sb.toString();
    }

    public String getSeqNo(int count){
        String seqNo = "0000000" + count;
        if(count >= 10 && count < 100){
            seqNo = "00000" + count;
        } else if(count >= 100 && count < 1000){
            seqNo = "00000" + count;
        } else if(count >= 1000){
            seqNo = "0000" + count;
        }
        return seqNo;
    }
}
