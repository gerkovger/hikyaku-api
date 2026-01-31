package com.gerkovger.hikyaku.api.channel;

import com.gerkovger.hikyaku.api.HikMessage;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

import static com.gerkovger.hikyaku.api.Constants.*;

public class HikConnection {

    private final String connectionId = UUID.randomUUID().toString();

    private final String ip;
    private final int port;

    @Getter
    private DataOutputStream out;
    @Getter
    private DataInputStream in;


    HikConnection(Address address) {
        this.ip = address.ip();
        this.port = address.port();
    }

    void connect() throws IOException {
        Socket clientSocket = new Socket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }

    void sendMessage(HikMessage message) throws IOException {
        short headerLen = 20;
        int frameLen = headerLen + message.getPayload().length;
        short flags = 0;

        out.writeShort(MAGIC);
        out.writeByte(VERSION);
        out.writeByte(message.getMessageType().code);
        out.writeShort(flags);
        out.writeShort(headerLen);
        out.writeInt(frameLen);
        out.writeLong(message.getCorrelationId());
        out.write(message.getPayload());
        out.flush();
    }

    public record Address(String ip, int port) {}

}
