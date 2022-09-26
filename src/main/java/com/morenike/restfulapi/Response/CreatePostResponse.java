package com.morenike.restfulapi.Response;

import com.morenike.restfulapi.Model.Comment;
import com.morenike.restfulapi.Model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreatePostResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Post post;
}
