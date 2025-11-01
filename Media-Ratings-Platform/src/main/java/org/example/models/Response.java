package org.example.models;

import lombok.Getter;
import lombok.Setter;

public class Response {
    @Getter @Setter
    int statusCode;
    @Getter @Setter
    String body;
}
