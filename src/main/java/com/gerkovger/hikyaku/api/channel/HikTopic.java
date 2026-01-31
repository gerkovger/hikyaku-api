package com.gerkovger.hikyaku.api.channel;

public final class HikTopic extends HikNamedChannel {

    HikTopic(String name, HikConnection.Address address) {
        super(ChannelType.TOPIC, name, address);
    }

}
