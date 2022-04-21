package kz.iitu.armarketplace.service.impl;

import kz.iitu.armarketplace.entity.FileEntity;
import kz.iitu.armarketplace.model.FileToSave;
import kz.iitu.armarketplace.repository.FileRepository;
import kz.iitu.armarketplace.repository.ProductRepository;
import kz.iitu.armarketplace.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	private final ProductRepository productRepository;

	public FileEntity store(FileToSave fileToSave) throws IOException {

		String fileName = StringUtils.cleanPath(fileToSave.getFile().getOriginalFilename());

		FileEntity fileToDb = FileEntity.builder()
			.data(fileToSave.getFile().getBytes())
			.mimeType(fileToSave.getFile().getContentType())
			.name(fileName)
			.products(productRepository.getById(fileToSave.getProductId()))
			.build();
		return fileRepository.save(fileToDb);
	}

	public FileEntity getFile(String id) {

		return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("there is no file with id: " + id));
	}

	public Stream<FileEntity> getAllFiles() {

		return fileRepository.findAll().stream();
	}
}
