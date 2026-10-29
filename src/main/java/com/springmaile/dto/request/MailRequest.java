package com.springmaile.dto.request;

import lombok.Data;

@Data
public class MailRequest {

    private String to;
    private String name;

    public MailRequest() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
