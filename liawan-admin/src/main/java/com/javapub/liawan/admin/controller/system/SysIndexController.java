package com.javapub.liawan.admin.controller.system;

import com.javapub.liawan.common.config.LiaWanConfig;
import com.javapub.liawan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author ruoyi
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Autowired
    private LiaWanConfig liaWanConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", liaWanConfig.getName(), liaWanConfig.getVersion());
    }
}
