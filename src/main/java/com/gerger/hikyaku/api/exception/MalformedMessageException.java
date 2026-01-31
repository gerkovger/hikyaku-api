package com.gerger.hikyaku.api.exception;

public class MalformedMessageException extends Exception {

    public MalformedMessageException(String msg) {
        super(msg);
    }

    public MalformedMessageException(Exception e) {
        super(e);
    }

}
