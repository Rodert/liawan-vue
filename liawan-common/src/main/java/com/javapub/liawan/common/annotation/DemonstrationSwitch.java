package com.javapub.liawan.common.annotation;

import java.lang.annotation.*;

/**
 * 演示开关
 * <p>
 * 在线预览系统不允许做更新操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemonstrationSwitch {

    /**
     * 开关
     *
     * @return
     */
    boolean onOff() default true;

}
