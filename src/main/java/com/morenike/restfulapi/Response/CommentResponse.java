package com.morenike.restfulapi.Response;

import com.morenike.restfulapi.Model.Comment;
import com.morenike.restfulapi.Model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Comment comment;
    private Post post;
}
