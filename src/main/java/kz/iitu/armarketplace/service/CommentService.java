package kz.iitu.armarketplace.service;

import kz.iitu.armarketplace.model.CommentDTO;
import kz.iitu.armarketplace.model.CommentToSave;

import java.util.List;

public interface CommentService {

	void save(CommentToSave commentToSave);

	List<CommentDTO> getAll();

	List<CommentDTO> getByProductId(Long productId);

	CommentDTO getById(Long id);

}
