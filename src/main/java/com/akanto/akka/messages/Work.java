package com.akanto.akka.messages;

public class Work {
    private final int start;
    private final int nrOfElements;

    public Work(int start, int nrOfElements) {
        this.start = start;
        this.nrOfElements = nrOfElements;
    }

    public int getStart() {
        return start;
    }

    public int getNrOfElements() {
        return nrOfElements;
    }

    @Override
    public String toString() {
        return "Work{" +
                "start=" + start +
                ", nrOfElements=" + nrOfElements +
                '}';
    }
}
