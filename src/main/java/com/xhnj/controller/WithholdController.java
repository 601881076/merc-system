package com.xhnj.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.mapper.TAdminMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TBatchDtl;
import com.xhnj.model.TBatchNo;
import com.xhnj.pojo.bo.AdminUserDetails;
import com.xhnj.pojo.query.WithholdParam;
import com.xhnj.pojo.vo.BatchNoVO;
import com.xhnj.service.TBatchCheckService;
import com.xhnj.service.TWithholdService;
import com.xhnj.service.WithholdBaseService;
import com.xhnj.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
 @Description 代扣
 *@author kang.li
 *@date 2021/9/18 16:14   
 */
@Api(value = "代扣", tags = "代扣接口")
@RestController
@RequestMapping("/wh")
@Slf4j
public class WithholdController {
    @Autowired
    private TWithholdService withholdService;
    @Autowired
    private WithholdBaseService withholdBaseService;
    @Autowired
    private TBatchCheckService batchCheckService;

    @Resource
    private TAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** 存储用户输入密码错误次数*/
    private ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap();

    @ApiOperation(value = "上传代扣excel")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file,@Validated BatchNoVO batchNoVO){

        // 校验密码输入次数是否超过三次
        String password = batchNoVO.getPassword();

        // 获取当前登录用户
        TAdmin user = UserUtil.getCurrentAdminUser();
        log.info("当前登录用户{}", user.toString());
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        log.info("用户输入密码错误次数{}", concurrentHashMap.get(userDetails.getUsername()));

        boolean flag = false;

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            // 判断输入密码次数是否超过3次
            flag = isBeyondThree(userDetails.getUsername());
            // 当输入密码次数大于3次时需要锁用户。
            if (flag) {
                adminMapper.updateUserStatusToDisableByUserName(user);
                throw new BadCredentialsException("输入密码次数超过3次，用户已被禁用");
            }

            throw new BadCredentialsException("密码不正确");
        } else {
            // 输入密码正确，删除该用户密码校验次数缓存
            concurrentHashMap.remove(userDetails.getUsername());
        }

        BusinValidatorContext context = BusinValidatorContext.getCurrentContext();
        context.set("totalTrans",batchNoVO.getTotalTrans());
        context.set("totalAmt",batchNoVO.getTotalAmt());
        context.set("password",batchNoVO.getPassword());
        int count = withholdService.uploadExcel(file);
        JSONObject data=new JSONObject();
        data.put("batchNo",context.get("batchNo"));
        if(count > 0)
            return CommonResult.success(data);
        return CommonResult.failed();
    }



    @ApiOperation(value = "下载扣款报告")
    @GetMapping("/export")
    public void excelExport(HttpServletResponse response, WithholdParam withholdParam){
        withholdBaseService.exportExcel(response,withholdParam);
    }


    @ApiOperation(value = "分页查询扣款批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TBatchNo>> page(TBatchNo batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        log.info("batchNo" + batchNo.toString());
        IPage page = withholdService.batchPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "批量导出")
    @GetMapping("/batchExport")
    public void batchExport(HttpServletResponse response,@RequestParam List<String> batchNo){
        withholdBaseService.batchExport(response,batchNo);
    }

    @ApiOperation(value = "分页查询扣款明细")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TBatchDtl>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /*@ApiOperation(value = "批次审核")
    @GetMapping("/check/{batchId}")
    public CommonResult check(@PathVariable("batchId")Long batchId){

        return CommonResult.success(null);
    }*/

    @ApiOperation(value = "下载扣款模板")
    @GetMapping("/download/wh")
    public void downloadExport(HttpServletResponse response){

        withholdBaseService.exportExcel(response);
    }

    @ApiOperation(value = "扣款批次删除")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        int count =withholdService.delete(id);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation(value = "批量批次审核")
    @GetMapping("/batchCheck")
    public CommonResult batchCheck(@RequestParam List<String> batchId){
        int count=batchCheckService.insert(batchId);
        if (count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    /**
     * 加载用户信息
     * @param username
     * @return
     */
    public UserDetails loadUserByUsername(String username) {
        //用户不存在则创建
        TAdmin admin = getAdminByUsername(username);
        return new AdminUserDetails(admin);
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    public TAdmin getAdminByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        return adminMapper.selectOne(wrapper);
    }

    /**
     * 判断当前用户输入密码是否超过三次
     * @param userName
     * @return
     */
    public Boolean isBeyondThree(String userName) {
        // 如果为空则代表第一次输入密码错误。
        if (null == concurrentHashMap.get(userName)) {
            concurrentHashMap.put(userName,1);
        } else {
            // 获取失败次数
            int failCount = concurrentHashMap.get(userName);

            if (3 > failCount) {
                // 输入密码小于3次 key += 1
                concurrentHashMap.put(userName,++failCount);
                return false;
            } else {
                // 输入密码错误大于3次
                return true;
            }
        }

        return false;
    }
}
