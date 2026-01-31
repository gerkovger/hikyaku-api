package com.gerkovger.hikyaku.api.channel;

public final class HikQueue extends HikNamedChannel {

    public HikQueue(String name, HikConnection.Address address) {
        super(ChannelType.QUEUE, name, address);
    }

}
