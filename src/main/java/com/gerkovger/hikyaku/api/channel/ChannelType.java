package com.gerkovger.hikyaku.api.channel;

public enum ChannelType {

    UNKNOWN(0),
    TOPIC(1),
    QUEUE(2);

    public final byte code;
    ChannelType(int code) {
        this.code = (byte) code;
    }

    public static ChannelType of(byte code) {
        for (ChannelType t : values()) if (t.code == code) return t;
        return UNKNOWN;
    }


}
