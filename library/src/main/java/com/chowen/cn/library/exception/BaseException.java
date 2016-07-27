package com.chowen.cn.library.exception;

/**
 * Created by uc on 2016/6/19.
 */
public class BaseException extends Exception{

    private static final long serialVersionUID = -1L;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }
}
