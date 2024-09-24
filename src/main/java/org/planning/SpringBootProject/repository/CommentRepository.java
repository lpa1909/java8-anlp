package org.planning.SpringBootProject.repository;

import org.planning.SpringBootProject.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    @Query("Select c from Comment c where c.productId = :id")
    List<Comment> getCommentById(@Param("id") String id);

    @Modifying
    @Query(value = "INSERT INTO Comments (id, details, star, userId, productId, createAt) values (:id, :detail, :star, :userId, :productId, :createAt)", nativeQuery = true)
    void createComment(@Param("id") String id,@Param("detail") String detail, @Param("star") String star, @Param("userId") String userId, @Param("productId") String productId, @Param("createAt") Date createAt);

    @Query("SELECT c FROM Comment c WHERE c.productId = :productCode")
    Page<Comment> getCommentById(String productCode, Pageable pageable);
}
