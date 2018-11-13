package com.example.demo.core.validate;

public interface Validator {
    public boolean isValidator();
    Result validate(String text);
}
