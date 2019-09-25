package com.example.demo;

import lombok.Data;

@Data
public class ResponseVO {

    private String message;
    public ResponseVO() {}
    public ResponseVO(String m) {
        this.message = m;
    }
}
