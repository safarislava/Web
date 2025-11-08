package ru.ifmo.se.api.dto.responses;

import java.util.Arrays;

public class ProblemJson {
    public String title = "Wrong request";
    public String status = "400";
    public String detail;
    public String stacktrace;

    public ProblemJson(String detail, Exception e) {
        this.detail = detail;
        this.stacktrace = Arrays.toString(e.getStackTrace());
    }
}
