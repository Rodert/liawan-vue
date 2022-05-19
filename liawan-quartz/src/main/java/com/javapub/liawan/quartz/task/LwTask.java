package com.javapub.liawan.quartz.task;

import com.javapub.liawan.common.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @Author: JavaPub
 * @License: https://github.com/Rodert/liawan-vue https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/ http://liawan.javapub.net.cn/
 * @Date: 2022
 * @Version:
 * @Description:
 */
@Component("lwTask")
public class LwTask {
    public void lwMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void lwParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void lwNoParams() {
        System.out.println("执行无参方法");
    }
}
