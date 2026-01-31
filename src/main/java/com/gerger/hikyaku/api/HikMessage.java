package com.gerger.hikyaku.api;

import lombok.Getter;

import java.nio.charset.StandardCharsets;

@Getter
public class HikMessage {

    private final MsgType messageType;

    private final long correlationId;

    @Getter
    private byte[] payload;

    public HikMessage(MsgType type, String payload) {
        this.messageType = type;
        this.correlationId = 0L;
        this.payload = payload.getBytes(StandardCharsets.UTF_8);
    }

}
