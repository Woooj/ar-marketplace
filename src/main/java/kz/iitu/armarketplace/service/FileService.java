package kz.iitu.armarketplace.service;

import kz.iitu.armarketplace.entity.FileEntity;
import kz.iitu.armarketplace.model.FileToSave;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileService {

	FileEntity store(FileToSave fileToSave) throws IOException;

	FileEntity getFile(String id);

	Stream<FileEntity> getAllFiles();

}
