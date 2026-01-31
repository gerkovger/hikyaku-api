package com.gerkovger.hikyaku.api;

import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Getter
public class HikMessage {

    private final MsgType messageType;

    private final long correlationId;

    private byte[] payload;

    HikMessage(MsgType type, long correlationId, String payload) {
        this(type, correlationId, payload.getBytes(StandardCharsets.UTF_8));
    }

    public String payloadAsString() {
        return new String(payload);
    }

    public HikMessage(MsgType type, long correlationId, byte[] payload) {
        this.messageType = type;
        this.correlationId = correlationId;
        this.payload = payload;
    }

    public void writeTo(DataOutputStream out) throws IOException {
        final short MAGIC = (short) 0x4252;
        final byte VERSION = 1;
        final short FLAGS = 0;
        final short HEADER_LEN = 20;

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
