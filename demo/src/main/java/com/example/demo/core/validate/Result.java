package com.example.demo.core.validate;

public class Result {
    public boolean isError;
    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}

