package controller;

import jakarta.servlet.http.HttpServletRequest;

public class Request {
    private final HttpServletRequest request;

    public Request(HttpServletRequest request) {
        this.request = request;
    }

    public String getParameter(String name) {
        return request.getParameter(name);
    }
}
