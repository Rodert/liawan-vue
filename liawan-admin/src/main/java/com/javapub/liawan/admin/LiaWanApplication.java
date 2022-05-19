package com.javapub.liawan.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 *
 * @Author: JavaPub
 * @License: https://github.com/Rodert/liawan-vue https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/ http://liawan.javapub.net.cn/
 * @Date: 2022
 * @Version:
 * @Description:
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.javapub.liawan.*"})
public class LiaWanApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LiaWanApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  俩万启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }


}
