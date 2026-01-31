package com.gerkovger.hikyaku.api;

public enum MsgType {

    UNKNOWN(0),
    CONNECT(1),
    SUBSCRIBE(2),
    PUBLISH(3),
    MESSAGE(4),
    ACK(5),
    PING(6),
    PONG(7);

    public final byte code;
    MsgType(int code) {
        this.code = (byte) code;
    }

    public static MsgType of(byte code) {
        for (MsgType t : values()) if (t.code == code) return t;
        return UNKNOWN;
    }

}
