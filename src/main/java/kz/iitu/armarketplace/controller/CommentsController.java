package kz.iitu.armarketplace.controller;


import kz.iitu.armarketplace.model.CommentDTO;
import kz.iitu.armarketplace.model.CommentToSave;
import kz.iitu.armarketplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentsController {

	private final CommentService commentService;


	@PostMapping("/save")
	public void saveComment(@RequestBody CommentToSave commentToSave) {

		commentService.save(commentToSave);
	}

	@GetMapping("/get-all")
	public List<CommentDTO> getAllComments() {

		return commentService.getAll();
	}

	@GetMapping("/get-for-product")
	public List<CommentDTO> getCommentsForProduct(@RequestParam("productId") Long productId) {

		return commentService.getByProductId(productId);
	}

	@GetMapping("/get-comment")
	public CommentDTO getCommentById(@RequestParam("id") Long id) {

		return commentService.getById(id);
	}

}
