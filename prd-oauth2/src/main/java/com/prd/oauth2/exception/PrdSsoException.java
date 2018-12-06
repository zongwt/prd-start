package com.prd.oauth2.exception;

/**
 * @author zongwt
 */
public class PrdSsoException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public PrdSsoException(String msg) {
        super(msg);
    }

    public PrdSsoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PrdSsoException(Throwable cause) {
        super(cause);
    }

}
