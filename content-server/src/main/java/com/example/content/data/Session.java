package com.example.content.data;

import lombok.Data;

@Data
public class Session {
    //用户唯一标识
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId+ ":"+ userName;
    }
}
