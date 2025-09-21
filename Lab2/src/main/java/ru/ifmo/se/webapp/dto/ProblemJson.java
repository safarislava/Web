package ru.ifmo.se.webapp.dto;

public class ProblemJson {
    public String title = "Wrong request";
    public String status = "400";
    public String detail;

    public ProblemJson(String detail) {
        this.detail = detail;
    }
}
