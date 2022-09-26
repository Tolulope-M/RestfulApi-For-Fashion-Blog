package com.morenike.restfulapi.Response;

import com.morenike.restfulapi.Model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchCommentResponse {
    private String message;
    private LocalDateTime timeStamp;
    private List<Comment> comments;
}
