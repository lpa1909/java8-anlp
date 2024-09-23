package org.planning.SpringBootProject.repository;

import org.planning.SpringBootProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
