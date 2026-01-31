package com.gerkovger.hikyaku.api;

import java.io.*;

import static com.gerkovger.hikyaku.api.Constants.*;
import static com.gerkovger.hikyaku.api.Constants.MAGIC;
import static com.gerkovger.hikyaku.api.Constants.HEADER_LEN;

public class HikMessageFactory {

    public static HikMessage readNextMessage(DataInputStream in) throws IOException {
        // Read fixed header fields
        short magic = in.readShort();
        if (magic != MAGIC) throw new IOException("Bad magic: " + Integer.toHexString(magic & 0xFFFF));

        int version = in.readUnsignedByte();   // keep for future
        byte typeByte = in.readByte();
        int flags = in.readUnsignedShort();    // keep for future
        int headerLen = in.readUnsignedShort();
        int frameLen = in.readInt();
        long correlationId = in.readLong();

        // Validate
        if (headerLen < HEADER_LEN) throw new IOException("Invalid headerLen: " + headerLen);
        if (frameLen < headerLen) throw new IOException("Invalid frameLen: " + frameLen);
        if (frameLen > MAX_FRAME_LEN) throw new IOException("Frame too large: " + frameLen);

        int payloadLen = frameLen - headerLen;

        // If you ever extend the header beyond 20, skip extra header bytes:
        int extraHeader = headerLen - HEADER_LEN;
        if (extraHeader > 0) {
            in.skipNBytes(extraHeader);
        }

        byte[] payload = in.readNBytes(payloadLen);
        if (payload.length != payloadLen) throw new EOFException();

        MsgType type = MsgType.of(typeByte);
        return new HikMessage(type, correlationId, payload);
    }

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

            if (headerLen < HEADER_LEN) {
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

}
