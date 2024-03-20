package com.example.MyCinemaApp.errs;

public class EmptySearchException extends RuntimeException{

    public EmptySearchException(String msg){
        super(msg);
    }
}
