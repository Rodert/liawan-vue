package com.javapub.liawan.framework.aspectj;

import com.javapub.liawan.common.annotation.DemonstrationSwitch;
import com.javapub.liawan.common.constant.HttpStatus;
import com.javapub.liawan.common.core.domain.AjaxResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/5/19 16:23
 * @Version: 1.0
 * @Description: 切面类
 */

@Aspect
@Component
public class DemonstrationSwitchAspect {

    private static final Logger logger = LoggerFactory.getLogger(DemonstrationSwitchAspect.class);

    /**
     * 切点
     */
    @Pointcut("@annotation(demonstrationSwitch)")
    public void demonstrationPoint(DemonstrationSwitch demonstrationSwitch) {
    }

    /**
     * 拦截拥有注解的接口
     *
     * @param joinPoint
     * @param demonstrationSwitch
     * @return
     */
    @Around(value = "demonstrationPoint(demonstrationSwitch)")
    public AjaxResult doBefore(ProceedingJoinPoint joinPoint, DemonstrationSwitch demonstrationSwitch) {
        if (demonstrationSwitch.onOff()) {
            return AjaxResult.error(HttpStatus.CONFLICT, "演示环境不支持写操作，\n 跟着教程5分钟搭建本地环境（还提供视频教程哦）。\n https://gitee.com/rodert/liawan-vue");
        }
        try {
            return AjaxResult.success(joinPoint.proceed());
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage());
        }
    }
}
