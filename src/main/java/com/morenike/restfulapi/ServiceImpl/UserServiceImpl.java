package com.morenike.restfulapi.ServiceImpl;

import com.morenike.restfulapi.DTO.*;
import com.morenike.restfulapi.Exception.PostAlreadyLikedException;
import com.morenike.restfulapi.Exception.PostNotFoundException;
import com.morenike.restfulapi.Exception.UserNotFoundException;
import com.morenike.restfulapi.Model.Comment;
import com.morenike.restfulapi.Model.Like;
import com.morenike.restfulapi.Model.Post;
import com.morenike.restfulapi.Model.User;
import com.morenike.restfulapi.Repository.CommentRepository;
import com.morenike.restfulapi.Repository.LikeRepository;
import com.morenike.restfulapi.Repository.PostRepository;
import com.morenike.restfulapi.Repository.UserRepository;
import com.morenike.restfulapi.Response.*;
import com.morenike.restfulapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public UserServiceImpl(LikeRepository likeRepository, UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }



    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Override
    public RegisterResponse register(UserDto userDto) {
        User user =  new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        userRepository.save(user);

        return new RegisterResponse("success" , LocalDateTime.now() , user);
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        User guest = findUserByEmail(loginDto.getEmail());
        LoginResponse loginResponse = null;
        if (guest != null){
            if (guest.getPassword().equals(loginDto.getPassword())){
                loginResponse = new LoginResponse("success" , LocalDateTime.now());
            }
        }else {
            loginResponse = new LoginResponse("password MisMatch" , LocalDateTime.now());
        }
        return loginResponse;
    }

    @Override
    public CreatePostResponse createPost(PostDto postDto) {

        Post post = new Post();
        User user = findUserById(postDto.getUser_id());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setSlug(makeSlug(postDto.getTitle()));
        post.setFeaturedImage(postDto.getFeaturedImage());
        post.setUser(user);
        postRepository.save(post);
        return new CreatePostResponse("success", LocalDateTime.now(), post);
    }

    @Override
    public CommentResponse comment(int user_id, int post_id, CommentDto commentDto) {
        User user = findUserById(user_id);
        Post post = findPostById(post_id);
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return new CommentResponse("success" , LocalDateTime.now() , comment , post);
    }

    @Override
    public LikeResponse like(int user_id, int post_id, LikeDto likeDto) {
        Like like = new Like();
        User user = findUserById(user_id);
        Post post = findPostById(post_id);
        LikeResponse likeResponse = null;
        Like duplicateLike = likeRepository.findLikeByUserIdAndPostId(user_id , post_id);
        if (duplicateLike == null){
            like.setLiked(likeDto.isLiked());
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            List<Like> likeList = likeRepository.likeList(post_id);
            likeResponse = new LikeResponse("success" , LocalDateTime.now() , like , likeList.size());
        }else {
            likeRepository.delete(duplicateLike);
            throw  new PostAlreadyLikedException("This post has been liked, It is now Unliked :(");
        }


        return likeResponse;
    }

    @Override
    public SearchCommentResponse searchComment(String keyword) {
        List<Comment> commentList = commentRepository.findByCommentContaining(keyword);
        return new SearchCommentResponse("success" , LocalDateTime.now() , commentList);
    }

    @Override
    public SearchPostResponse searchPost(String keyword) {
        List<Post> postList = postRepository.findByTitleContaining(keyword);
        return new SearchPostResponse("success" , LocalDateTime.now() , postList);
    }

    public User findUserById(int id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User With ID: " + id + " Not Found "));
    }

    public Post findPostById(int id){
        return postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post With ID: " + id + " Not Found "));
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(()-> new UserNotFoundException("User With email: " + email + " Not Found "));
    }

    public String makeSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
