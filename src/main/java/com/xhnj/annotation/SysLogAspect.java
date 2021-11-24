package com.xhnj.annotation;

import com.xhnj.model.TLog;
import com.xhnj.service.SysLogService;
import com.xhnj.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
* @Description:    系统日志：切面处理类
* @Author:         tan_yi
* @CreateDate:     2021/11/7 16:27
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/7 16:27
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/

@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.xhnj.annotation.MyLog)")
    public void logPoinCut() {
        log.info("切面11");
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        TLog tLog = new TLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            // 操作动作
            String value = myLog.operate();
            //保存获取的操作
            tLog.setOperate(value);

            // 对象类型
            tLog.setObjectType(myLog.objectType());

            // 对象名称
            tLog.setObjectName(myLog.objectName());

            // 对象描述 -- 功能描述
            tLog.setDescript(myLog.descript());
        }

        //获取请求的类名
        /*String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        tLog.setObjectName(className + "." + methodName);*/

        //请求的参数
        /*Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        tLog.setParams(params);*/

        //获取用户名
        Optional.ofNullable(UserUtil.getCurrentAdminUser()).ifPresent(e -> tLog.setUserName(UserUtil.getCurrentAdminUser().getUsername()));

        //获取用户ip地址
        /*HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLog.setIp(IPUtils.getIpAddr(request));*/

        log.info("用户{} 进行了{} 操作", tLog.getUserName(), tLog.getDescript());
        //调用service保存SysLog实体类到数据库
        sysLogService.insertAdmin(tLog);
    }
}
