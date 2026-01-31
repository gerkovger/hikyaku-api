package com.gerkovger.hikyaku.api.channel;

import com.gerkovger.hikyaku.api.HikMessage;
import com.gerkovger.hikyaku.api.MsgType;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;

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

    protected void connect() throws IOException {
        connection.connect();
        var nameBytes = name.getBytes();
        var bb = ByteBuffer.allocate(nameBytes.length + 1);
        bb.put(type.code).put(nameBytes);
        var connMessage = new HikMessage(MsgType.CONNECT, 0L, bb.array());
        connection.sendMessage(connMessage);
    }

}