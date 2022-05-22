package kz.iitu.armarketplace.service.impl;

import kz.iitu.armarketplace.entity.File;
import kz.iitu.armarketplace.entity.Product;
import kz.iitu.armarketplace.model.FileToSave;
import kz.iitu.armarketplace.repository.FileRepository;
import kz.iitu.armarketplace.repository.ProductRepository;
import kz.iitu.armarketplace.service.FileService;
import kz.iitu.armarketplace.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	private final ProductRepository productRepository;

	public void store(FileToSave fileToSave) throws IOException {

		Optional<Product> product = productRepository.findById(fileToSave.getProductId());

		for (MultipartFile image : fileToSave.getImages())	{
				File fileToDb = File.builder()
					.name(image.getOriginalFilename())
					.type(image.getContentType())
					.product(product.get())
					.image(ImageUtility.compressImage(image.getBytes()))
					.build();
				fileRepository.save(fileToDb);
			}
	}

	public File getFile(String id) {

		return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("there is no file with id: " + id));
	}

	public Stream<File> getAllFiles() {

		return fileRepository.findAll().stream();
	}

	@Override
	public List<File> getAllFilesByProduct(Long id) {

		return fileRepository.findAllByProductId(id);
	}
}
