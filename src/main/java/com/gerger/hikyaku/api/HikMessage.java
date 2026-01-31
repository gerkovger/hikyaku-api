package com.gerger.hikyaku.api;

import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.gerger.hikyaku.api.Constants.*;

@Getter
public class HikMessage {

    public static HikMessage parse(byte[] rawMessage) {
        try (DataInputStream in =
                     new DataInputStream(new ByteArrayInputStream(rawMessage))) {

            short magic = in.readShort();
            if (magic != MAGIC) {
                throw new IllegalArgumentException("Bad magic: " + Integer.toHexString(magic & 0xFFFF));
            }

            int version = in.readUnsignedByte(); // unused for now
            byte typeByte = in.readByte();
            int flags = in.readUnsignedShort();  // unused for now
            int headerLen = in.readUnsignedShort();
            int frameLen = in.readInt();
            long correlationId = in.readLong();

            if (headerLen < MIN_HEADER_LEN) {
                throw new IllegalArgumentException("Invalid headerLen: " + headerLen);
            }
            if (frameLen < headerLen || frameLen > rawMessage.length) {
                throw new IllegalArgumentException("Invalid frameLen: " + frameLen);
            }

            int payloadLen = frameLen - headerLen;
            byte[] payload = new byte[payloadLen];
            in.readFully(payload);

            MsgType messageType = MsgType.of(typeByte);

            return new HikMessage(messageType, correlationId, payload);

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse HikMessage", e);
        }
    }

    private final MsgType messageType;

    private final long correlationId;

    private byte[] payload;

    public HikMessage(MsgType type, String payload) {
        this(type, 42L, payload.getBytes(StandardCharsets.UTF_8));
    }

    private HikMessage(MsgType type, long correlationId, byte[] payload) {
        this.messageType = type;
        this.correlationId = correlationId;
        this.payload = payload;
    }

}
