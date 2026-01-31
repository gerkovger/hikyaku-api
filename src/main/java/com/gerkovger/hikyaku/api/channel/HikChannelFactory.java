package com.gerkovger.hikyaku.api.channel;

import com.gerkovger.hikyaku.api.exception.ConnectionFailedException;
import com.gerkovger.hikyaku.api.exception.UnknownChannelTypeException;

import java.io.IOException;

public class HikChannelFactory {

    private final HikConnection.Address address;

    public HikChannelFactory(HikConnection.Address address) {
        this.address = address;
    }

    public HikChannel getChannel(ChannelType channelType, String name) throws IOException, ConnectionFailedException, UnknownChannelTypeException {
        return switch (channelType) {
            case QUEUE -> getQueue(name);
            case TOPIC -> getTopic(name);
            default -> throw new UnknownChannelTypeException(name);
        };
    }

    public HikTopic getTopic(String name) throws IOException, ConnectionFailedException {
        var t = new HikTopic(name, address);
        t.connect();
        return t;
    }

    public HikQueue getQueue(String name) throws IOException, ConnectionFailedException {
        var q = new HikQueue(name, address);
        q.connect();
        return q;
    }

}
