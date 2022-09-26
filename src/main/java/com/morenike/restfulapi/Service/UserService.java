package com.morenike.restfulapi.Service;

import com.morenike.restfulapi.DTO.*;
import com.morenike.restfulapi.Response.*;
import com.morenike.restfulapi.ServiceImpl.UserServiceImpl;

public interface UserService {
    RegisterResponse register(UserDto userDto);

    LoginResponse login(LoginDto loginDto);

    CreatePostResponse createPost(PostDto postDto);

    CommentResponse comment(int user_id , int post_id , CommentDto commentDto);

    LikeResponse like(int user_id , int post_id , LikeDto likeDto);

    SearchCommentResponse searchComment(String keyword);

    SearchPostResponse searchPost(String keyword);
}
