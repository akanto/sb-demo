package com.akanto.hello;

public class Greeting {

    private final long id;
    private final String content;
    private final Long delay;

    public Greeting(long id, String content, Long delay) {
        this.id = id;
        this.content = content;
        this.delay = delay;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getDelay() {
        return delay;
    }
}