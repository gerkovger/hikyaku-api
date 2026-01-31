package com.gerkovger.hikyaku.api.channel;

import com.gerkovger.hikyaku.api.exception.ConnectionFailedException;

import java.io.IOException;

public class HikChannelFactory {

    private final HikConnection.Address address;

    public HikChannelFactory(HikConnection.Address address) {
        this.address = address;
    }

    public HikTopic getTopic(String name) throws IOException, ConnectionFailedException {
        var t = new HikTopic(name, address);
        t.connect();
        return t;
    }

}
