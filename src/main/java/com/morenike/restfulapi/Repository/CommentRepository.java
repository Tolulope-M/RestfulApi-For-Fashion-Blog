package com.morenike.restfulapi.Repository;

import com.morenike.restfulapi.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByCommentContaining(String keyword);
}
