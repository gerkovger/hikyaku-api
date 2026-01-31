package com.gerkovger.hikyaku.api.channel;

import java.io.IOException;

public sealed interface HikChannel permits HikNamedChannel {

    void publish(String msg, long correlationId) throws IOException;

}
