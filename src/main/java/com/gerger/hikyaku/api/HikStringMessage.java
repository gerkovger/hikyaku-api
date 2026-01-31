package com.gerger.hikyaku.api;

import com.gerger.hikyaku.api.exception.MalformedMessageException;

import java.time.Instant;

public class HikStringMessage {

    public static HikStringMessage parse(String rawMessage) throws MalformedMessageException {
        int i = rawMessage.indexOf("|");
        if (i == -1) throw new MalformedMessageException(rawMessage);
        try {
            var cTime = Long.parseLong(rawMessage.substring(0, i));
            var message = rawMessage.substring(i + 1);
            return new HikStringMessage(cTime, message);
        } catch (NumberFormatException e) {
            throw new MalformedMessageException(e);
        }
    }

    public static HikStringMessage create(String message) {
        return new HikStringMessage(Instant.now().toEpochMilli(), message);
    }

    private final long creationTime;

    private final String message;

    private HikStringMessage(long creationTime, String message) {
        this.creationTime = creationTime;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return creationTime + "|" + message.replace("|", "\\|");
    }

}
