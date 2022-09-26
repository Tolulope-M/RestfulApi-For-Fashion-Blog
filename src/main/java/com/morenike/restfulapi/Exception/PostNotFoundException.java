package com.morenike.restfulapi.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter@AllArgsConstructor
public class PostNotFoundException extends RuntimeException{
    private String message;
}
