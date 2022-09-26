package com.morenike.restfulapi.DTO;

import lombok.Data;

@Data
public class PostDto {
    private String title;
    private String description;
    private int user_id;
    private String featuredImage;
}
