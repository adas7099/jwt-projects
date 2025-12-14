package com.example.jwt.book.enums;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private String desc;
    Role(String desc){
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }
}
