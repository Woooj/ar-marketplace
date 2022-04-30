package kz.iitu.armarketplace.service;

import kz.iitu.armarketplace.entity.FileEntity;
import kz.iitu.armarketplace.model.FileToSave;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {

	void store(FileToSave fileToSave) throws IOException;

	FileEntity getFile(String id);

	Stream<FileEntity> getAllFiles();

	void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException;

	List<FileEntity> getAllFilesByProduct(Long id);
}
