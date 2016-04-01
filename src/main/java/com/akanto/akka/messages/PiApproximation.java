package com.akanto.akka.messages;

import scala.concurrent.duration.Duration;

public class PiApproximation {
    private final double pi;
    private final Duration duration;

    public PiApproximation(double pi, Duration duration) {
        this.pi = pi;
        this.duration = duration;
    }

    public double getPi() {
        return pi;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "PiApproximation{" +
                "pi=" + pi +
                ", duration=" + duration +
                '}';
    }
}
