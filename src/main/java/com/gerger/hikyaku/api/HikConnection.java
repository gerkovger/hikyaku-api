package com.gerger.hikyaku.api;

import java.io.*;
import java.net.Socket;

import static com.gerger.hikyaku.api.Constants.*;

public class HikConnection {

    public static HikConnection createConnection(String ip, int port) {
        return new HikConnection(ip, port);
    }

    private final String ip;

    private final int port;

    private Socket clientSocket;

    private DataOutputStream out;
    private BufferedReader in;


    private HikConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() throws IOException {
        clientSocket = new Socket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(HikMessage message) throws IOException {
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

}
