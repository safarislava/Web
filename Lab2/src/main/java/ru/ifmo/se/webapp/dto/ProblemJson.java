package ru.ifmo.se.webapp.dto;

public class ProblemJson {
    public String title = "Wrong request";
    public String status = "400";
    public String detail;
    //TODO exception trace

    public ProblemJson(String detail) {
        this.detail = detail;
    }
}
