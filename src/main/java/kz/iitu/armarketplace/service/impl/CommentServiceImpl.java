package kz.iitu.armarketplace.service.impl;

import kz.iitu.armarketplace.entity.CommentEntity;
import kz.iitu.armarketplace.entity.ProductEntity;
import kz.iitu.armarketplace.model.CommentDTO;
import kz.iitu.armarketplace.model.CommentToSave;
import kz.iitu.armarketplace.repository.CommentRepository;
import kz.iitu.armarketplace.repository.ProductRepository;
import kz.iitu.armarketplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	private final ProductRepository productRepository;

	@Override
	public void save(CommentToSave commentToSave) {

		commentRepository.save(convertToEntity(commentToSave));
	}

	@Override
	public List<CommentDTO> getAll() {

		return convertToDTO(commentRepository.findAll());
	}


	@Override
	public List<CommentDTO> getByProductId(Long productId) {

		return convertToDTO(commentRepository.findAllByProductId(productId));
	}

	@Override
	public CommentDTO getById(Long id) {

		CommentEntity commentEntity = commentRepository.findById(id).get();

		return CommentDTO.builder()
			.id(commentEntity.getId())
			.text(commentEntity.getText())
			.rating(commentEntity.getRating())
			.username(commentEntity.getUsername())
			.productId(commentEntity.getProduct().getId())
			.createdAt(commentEntity.getCreatedAt())
			.build();
	}


	private List<CommentDTO> convertToDTO(List<CommentEntity> all) {

		List<CommentDTO> result = new ArrayList<>();

		for (CommentEntity comment : all) {
			result.add(CommentDTO.builder()
					.id(comment.getId())
					.text(comment.getText())
					.rating(comment.getRating())
					.username(comment.getUsername())
					.productId(comment.getProduct().getId())
					.createdAt(comment.getCreatedAt())
				.build());
		}

		return result;
	}

	private CommentEntity convertToEntity(CommentToSave commentToSave) {

		ProductEntity product = productRepository.findById(commentToSave.productId).get();
		return CommentEntity.builder()
			.id(commentToSave.id)
			.rating(commentToSave.rating)
			.username(commentToSave.username)
			.text(commentToSave.text)
			.createdAt(new Date())
			.product(product)
			.build();
	}
}
