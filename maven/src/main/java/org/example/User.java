package org.example;

import java.io.PrintWriter;

public class User {
    private final String nickname;
    private final PrintWriter writer;
    private final String clientInfo;
    private final long joinTime;

    public User(String nickname, PrintWriter writer, String clientInfo) {
        this.nickname = nickname;
        this.writer = writer;
        this.clientInfo = clientInfo;
        this.joinTime = System.currentTimeMillis();
    }

    public String getNickname() {
        return nickname;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public long getOnlineTime() {
        return System.currentTimeMillis() - joinTime;
    }

    @Override
    public String toString() {
        return nickname + " (" + clientInfo + ")";
    }
}