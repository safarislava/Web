package ru.ifmo.se.server.dto;

public class ProblemJson {
    public String title = "Wrong request";
    public String status = "400";
    public String detail;

    public ProblemJson(String detail) {
        this.detail = detail;
    }
}
