package com.akanto.akka.messages;

import io.opentracing.ActiveSpan;

public class Calculate {

    private final ActiveSpan activeSpan;

    public Calculate(ActiveSpan activeSpan) {
        this.activeSpan = activeSpan;
    }

    public ActiveSpan getActiveSpan() {
        return activeSpan;
    }

    @Override
    public String toString() {
        return "Calculate{}";
    }
}
