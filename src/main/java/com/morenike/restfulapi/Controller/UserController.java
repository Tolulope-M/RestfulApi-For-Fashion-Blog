package com.morenike.restfulapi.Controller;

import com.morenike.restfulapi.DTO.LikeDto;
import com.morenike.restfulapi.DTO.CommentDto;
import com.morenike.restfulapi.DTO.PostDto;
import com.morenike.restfulapi.DTO.UserDto;
import com.morenike.restfulapi.Response.CommentResponse;
import com.morenike.restfulapi.Response.CreatePostResponse;
import com.morenike.restfulapi.Response.LikeResponse;
import com.morenike.restfulapi.Response.RegisterResponse;
import com.morenike.restfulapi.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping( value = "/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserDto userDto){
        log.info("Successfully Registered {} " , userDto.getEmail());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register").toUriString());
        return ResponseEntity.created(uri).body( userService.register(userDto));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CreatePostResponse> create(@RequestBody PostDto postDto){
        log.info("Successfully Created A post With Title:  {} " , postDto.getTitle());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/create").toUriString());
        return ResponseEntity.created(uri).body( userService.createPost(postDto));
    }

    @PostMapping(value = "/comment/{user_id}/{post_id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable(value = "user_id") Integer user_id, @PathVariable(value = "post_id") Integer post_id, @RequestBody CommentDto commentDto){
        log.info("Successfully commented :  {} " , commentDto.getComment());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/comment").toUriString());
        return ResponseEntity.created(uri).body( userService.comment(user_id , post_id , commentDto));
    }

    @PostMapping(value = "/like/{user_id}/{post_id}")
    public ResponseEntity<LikeResponse> like(@PathVariable(value = "user_id") Integer user_id, @PathVariable(value = "post_id") Integer post_id, @RequestBody LikeDto likeDto){
        log.info("Successfully liked :  {} " , likeDto.isLiked());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/like").toUriString());
        return ResponseEntity.created(uri).body( userService.like(user_id , post_id , likeDto));
    }




}
