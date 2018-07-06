package com.xkupc.framework.sequence.exception;

/**
 * @author xk
 * @create 2018-07-06 14:37
 * @description
 **/
public class SequenceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public SequenceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public SequenceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public SequenceException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public SequenceException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
