package com.xhnj.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.common.BusinValidatorContext;
import com.xhnj.common.CommonPage;
import com.xhnj.common.CommonResult;
import com.xhnj.mapper.TAdminMapper;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TDismissBatch;
import com.xhnj.model.TWithholdCancle;
import com.xhnj.pojo.bo.AdminUserDetails;
import com.xhnj.pojo.vo.BatchNoVO;
import com.xhnj.service.TBatchCheckService;
import com.xhnj.service.TWithholdAgreeService;
import com.xhnj.service.TWithholdCancleService;
import com.xhnj.service.WithholdAgreeDismissBaseService;
import com.xhnj.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
 @Description 代扣协议取消
 *@author kang.li
 *@date 2021/9/25 20:36
 */
@Api(value = "代扣协议取消", tags = "代扣协议取消接口")
@RestController
@RequestMapping("/wad")
@Slf4j
public class WithholdAgreeDismissController {

    @Value("${mq.exchange}")
    private String exchange;

    @Autowired
    private TWithholdAgreeService withholdAgreeService;
    @Autowired
    private TWithholdCancleService withholdCancleService;

    @Autowired
    private WithholdAgreeDismissBaseService WithholdAgreeDismissBaseService;
    @Autowired
    private TBatchCheckService batchCheckService;


    @Resource
    private TAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** 存储用户输入密码错误次数*/
    private ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap();

    @ApiOperation(value = "分页查询授权取消批次")
    @GetMapping("/page")
    public CommonResult<CommonPage<TDismissBatch>> page(TDismissBatch dismissBatch, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdAgreeService.batchPage(dismissBatch,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "分页查询授权取消明细")
    @GetMapping("/getDetail/{batchNo}")
    public CommonResult<CommonPage<TWithholdCancle>> list(@PathVariable("batchNo")String batchNo, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage page = withholdCancleService.listPage(batchNo,pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation(value = "授权取消删除")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        int count =WithholdAgreeDismissBaseService.delete(id);
        if(count > 0)
            return CommonResult.success(count);
        return CommonResult.failed();
    }

    @ApiOperation(value = "取消授权excel上传")
    @PostMapping("/excelImport")
    public CommonResult uploadExcel(@RequestParam("file") MultipartFile file, BatchNoVO batchNoVO){

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
        context.set("password",batchNoVO.getPassword());
        context.set("totalTrans",batchNoVO.getTotalTrans());
        int count = withholdAgreeService.uploadExcel(file);
        JSONObject data=new JSONObject();
        data.put("batchNo",context.get("batchNo"));
        if(count > 0)
            return CommonResult.success(data);
        return CommonResult.failed();
    }

    @ApiOperation(value = "下载授权取消模板")
    @GetMapping("/download/wad")
    public void downloadExport(HttpServletResponse response){

        WithholdAgreeDismissBaseService.exportExcel(response);
    }

    @ApiOperation(value = "授权取消提交")
    @GetMapping("/batchCheckSub")
    public CommonResult batchCheckSub(@RequestParam List<String> batchId){
        log.info("batchId;"+batchId.toString());
        int count=batchCheckService.insertCheck(batchId);
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
