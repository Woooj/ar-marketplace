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

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	private final ProductRepository productRepository;

	@Transactional
	public FileEntity store(FileToSave fileToSave) throws IOException {


		FileEntity fileToDb = FileEntity.builder()
			.productId(fileToSave.getProductId())
			.path(fileToSave.getProductId() + "/" + fileToSave.getName())
			.build();
		return fileRepository.save(fileToDb);
	}

	public FileEntity getFile(String id) {

		return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("there is no file with id: " + id));
	}

	public Stream<FileEntity> getAllFiles() {

		return fileRepository.findAll().stream();
	}
	public void saveFile(String uploadDir, String fileName,
															MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	@Override
	public List<FileEntity> getAllFilesByProduct(Long id) {

		return fileRepository.findAllByProductId(id);
	}
}
