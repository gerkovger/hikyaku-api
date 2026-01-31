package com.gerkovger.hikyaku.api.channel;

import com.gerkovger.hikyaku.api.HikMessage;
import com.gerkovger.hikyaku.api.HikMessageFactory;
import com.gerkovger.hikyaku.api.MsgType;
import com.gerkovger.hikyaku.api.exception.ConnectionFailedException;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public sealed abstract class HikNamedChannel implements HikChannel
        permits HikQueue, HikTopic {

    @Getter
    private final String name;

    protected final ChannelType type;

    protected final HikConnection connection;

    protected HikNamedChannel(ChannelType type, String name, HikConnection.Address address) {
        this.type = type;
        this.name = name;
        this.connection = new HikConnection(address);
    }

    protected void connect() throws IOException, ConnectionFailedException {
        connection.connect();
        var nameBytes = name.getBytes();
        var bb = ByteBuffer.allocate(nameBytes.length + 1);
        bb.put(type.code).put(nameBytes);
        var connMessage = new HikMessage(MsgType.CONNECT, 0L, bb.array());
        connection.sendMessage(connMessage);
        var in = new DataInputStream(connection.getIn());
        var ack = HikMessageFactory.readNextMessage(in);
        if (ack.getMessageType() != MsgType.ACK || !Objects.equals(ack.payloadAsString(), "connected")) {
            throw new ConnectionFailedException(ack.getMessageType() + "|" + ack.payloadAsString());
        }
    }

    public void publish(String message, long correlationId) throws IOException {
        var msg = new HikMessage(MsgType.MESSAGE, correlationId, message);
        connection.sendMessage(msg);
    }

}