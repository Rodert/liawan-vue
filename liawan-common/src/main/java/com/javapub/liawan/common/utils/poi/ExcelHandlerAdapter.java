package com.javapub.liawan.common.utils.poi;

/**
 * Excel数据格式处理适配器
 *
 * @Author: JavaPub
 * @License: https://github.com/Rodert/liawan-vue https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/ http://liawan.javapub.net.cn/
 * @Date: 2022
 * @Version:
 * @Description:
 */
public interface ExcelHandlerAdapter {
    /**
     * 格式化
     *
     * @param value 单元格数据值
     * @param args  excel注解args参数组
     * @return 处理后的值
     */
    Object format(Object value, String[] args);
}
