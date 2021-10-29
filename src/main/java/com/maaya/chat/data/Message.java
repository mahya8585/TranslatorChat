package com.maaya.chat.data;

public class Message {
    private String name;
    private String statement;

    public Message(String name, String statement) {
        this.name = name;
        this.statement = statement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
