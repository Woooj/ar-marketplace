package kz.iitu.armarketplace.controller;

import kz.iitu.armarketplace.entity.FileEntity;
import kz.iitu.armarketplace.model.FileToSave;
import kz.iitu.armarketplace.payload.response.MessageResponse;
import kz.iitu.armarketplace.payload.response.ResponseFile;
import kz.iitu.armarketplace.repository.FileRepository;
import kz.iitu.armarketplace.service.FileService;
import kz.iitu.armarketplace.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileController {

//	private final FileService fileService;

	private final FileRepository fileRepository;

	@PostMapping("/upload/image")
	public ResponseEntity<String> uplaodImage(@RequestParam("image") MultipartFile file,
																						@RequestParam("productId") Long productId)
		throws IOException {

		fileRepository.save(FileEntity.builder()
			.name(file.getOriginalFilename())
			.type(file.getContentType())
			.productId(productId)
			.image(file.getBytes())
			.build());
		return ResponseEntity.status(HttpStatus.OK)
			.body("Image uploaded successfully: " +
				file.getOriginalFilename());
	}

	@GetMapping(path = {"/get/image/info/{name}"})
	public FileEntity getImageDetails(@PathVariable("name") String name) throws IOException {

		final Optional<FileEntity> dbImage = fileRepository.findByName(name);

		return FileEntity.builder()
			.name(dbImage.get().getName())
			.type(dbImage.get().getType())
			.image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
	}

	@GetMapping(path = {"/get/{name}"})
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

		final Optional<FileEntity> dbImage = fileRepository.findByName(name);

		return ResponseEntity
			.ok()
			.contentType(MediaType.valueOf(dbImage.get().getType()))
			.body(ImageUtility.decompressImage(dbImage.get().getImage()));
	}

	@GetMapping(path = {"/get-compressed/{name}"})
	public ResponseEntity<byte[]> getCompressedImage(@PathVariable("name") String name) throws IOException {

		final Optional<FileEntity> dbImage = fileRepository.findByName(name);

		return ResponseEntity
			.ok()
			.contentType(MediaType.valueOf(dbImage.get().getType()))
			.body(ImageUtility.compressImage(dbImage.get().getImage()));
	}


//	@PostMapping("/upload")
//	public ResponseEntity<MessageResponse> uploadFile(@RequestBody FileToSave fileToSave) {
//
//		String message = "";
//		try
//			{
//				fileService.store(fileToSave);
//				message = "Uploaded the file successfully: " + fileToSave.getFile().getOriginalFilename();
//				return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
//			} catch(Exception e)
//			{
//				message = "Could not upload the file: " + fileToSave.getFile().getOriginalFilename() + "!";
//				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
//			}
//	}

//	@GetMapping("/files")
//	public ResponseEntity<List<ResponseFile>> getListFiles() {
//
//		List<ResponseFile> files = fileService.getAllFiles().map(dbFile -> {
//			String fileDownloadUri = ServletUriComponentsBuilder
//				.fromCurrentContextPath()
//				.path("/files/")
//				.path(dbFile.getId())
//				.toUriString();
//			return new ResponseFile(
//				dbFile.getName(),
//				fileDownloadUri,
//				dbFile.getMimeType(),
//				dbFile.getData().length);
//		}).collect(Collectors.toList());
//		return ResponseEntity.status(HttpStatus.OK).body(files);
//	}

//	@GetMapping("/files/{id}")
//	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//
//		FileEntity fileDB = fileService.getFile(id);
//		return ResponseEntity.ok()
//			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
//			.body(fileDB.getData());
//	}
}
