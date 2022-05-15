package com.javapub.liawan.common.exception.user;

import com.javapub.liawan.common.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author javapub.liawan.
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
