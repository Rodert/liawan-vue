package com.javapub.liawan.common.exception.job;

/**
 * 计划策略异常
 *
 * @Author: JavaPub
 * @License: https://github.com/Rodert/liawan-vue https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/ http://liawan.javapub.net.cn/
 * @Date: 2022
 * @Version:
 * @Description:
 */
public class TaskException extends Exception {
    private static final long serialVersionUID = 1L;

    private Code code;

    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }

    public TaskException(String msg, Code code, Exception nestedEx) {
        super(msg, nestedEx);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    public enum Code {
        TASK_EXISTS, NO_TASK_EXISTS, TASK_ALREADY_STARTED, UNKNOWN, CONFIG_ERROR, TASK_NODE_NOT_AVAILABLE
    }
}