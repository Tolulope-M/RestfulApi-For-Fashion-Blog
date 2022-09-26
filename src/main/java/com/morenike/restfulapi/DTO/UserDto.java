package com.morenike.restfulapi.DTO;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String role;
    private String password;
}
