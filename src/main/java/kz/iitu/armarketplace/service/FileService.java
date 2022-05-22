package kz.iitu.armarketplace.service;

import kz.iitu.armarketplace.entity.File;
import kz.iitu.armarketplace.model.FileToSave;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {

	void store(FileToSave fileToSave) throws IOException;

	File getFile(String id);

	Stream<File> getAllFiles();

	List<File> getAllFilesByProduct(Long id);
}
