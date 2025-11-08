package ru.ifmo.se.api.dto.responses;

import java.util.Arrays;

public class ProblemJson {
    public final String title = "Wrong request";
    public final String status = "400";
    public final String detail;
    public final String stacktrace;

    public ProblemJson(String detail, Exception e) {
        this.detail = detail;
        this.stacktrace = Arrays.toString(e.getStackTrace());
    }
}
