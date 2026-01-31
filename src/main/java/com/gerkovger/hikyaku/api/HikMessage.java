package com.gerkovger.hikyaku.api;

import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.gerkovger.hikyaku.api.Constants.*;

@Getter
public class HikMessage {

    private final MsgType messageType;

    private final long correlationId;

    private byte[] payload;

    public HikMessage(MsgType type, long correlationId, String payload) {
        this(type, correlationId, payload.getBytes(StandardCharsets.UTF_8));
    }

    public HikMessage(MsgType type, long correlationId, byte[] payload) {
        this.messageType = type;
        this.correlationId = correlationId;
        this.payload = payload;
    }

    public String payloadAsString() {
        return new String(payload);
    }

    public void writeTo(DataOutputStream out) throws IOException {
        final short FLAGS = 0;

        int payloadLen = payload != null ? payload.length : 0;
        int frameLen = HEADER_LEN + payloadLen;

        out.writeShort(MAGIC);
        out.writeByte(VERSION);
        out.writeByte(messageType.code);
        out.writeShort(FLAGS);
        out.writeShort(HEADER_LEN);
        out.writeInt(frameLen);
        out.writeLong(correlationId);

        if (payloadLen > 0) {
            out.write(payload);
        }

        out.flush();
    }


}
