package com.cursosdedesarrollo.rabbitmqexamples.model;

import lombok.Data;

@Data
public class StringResponse {
    private String msg;
    private String error;

    public StringResponse(String msg, String error){
        this.error = error;
        this.msg = msg;
    }
    public StringResponse(String msg){
        this.error = "";
        this.msg = msg;
    }
}
