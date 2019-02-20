package com.suyu.tomo.exception;

public class TestException extends RuntimeException
{
    private String cause;

    public TestException(Throwable throwable, String cause) {
        super(cause);
        this.cause = cause;
    }

    public TestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String cause1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.cause = cause1;
    }

    public TestException(String cause) {
        this.cause = cause;
    }


    public String getCurCause() {
        return cause;
    }

    @Override
    public String toString() {
        return "TestException{" +
                "cause='" + cause + '\'' +
                '}';
    }
}
