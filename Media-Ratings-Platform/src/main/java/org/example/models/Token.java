package org.example.models;

import lombok.Getter;
import lombok.Setter;

public class Token {
    @Getter @Setter
    private String tokenValue;

    public String CreateToken(String username){
        return "token_"+username;
    }
}
