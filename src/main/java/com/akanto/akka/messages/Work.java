package com.akanto.akka.messages;

import io.opentracing.ActiveSpan;

public class Work {
    private final int start;
    private final int nrOfElements;
    private final ActiveSpan activeSpan;

    public Work(int start, int nrOfElements, ActiveSpan activeSpan) {
        this.start = start;
        this.nrOfElements = nrOfElements;
        this.activeSpan = activeSpan;
    }

    public int getStart() {
        return start;
    }

    public int getNrOfElements() {
        return nrOfElements;
    }

    public ActiveSpan getActiveSpan() {
        return activeSpan;
    }

    @Override
    public String toString() {
        return "Work{" +
                "start=" + start +
                ", nrOfElements=" + nrOfElements +
                '}';
    }
}
