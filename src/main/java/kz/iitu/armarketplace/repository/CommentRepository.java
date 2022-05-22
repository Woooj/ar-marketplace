package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByProductId(@NonNull Long productId);

}
