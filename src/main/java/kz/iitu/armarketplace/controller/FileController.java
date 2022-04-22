package kz.iitu.armarketplace.controller;

import kz.iitu.armarketplace.entity.FileEntity;
import kz.iitu.armarketplace.model.FileToSave;
import kz.iitu.armarketplace.payload.response.MessageResponse;
import kz.iitu.armarketplace.payload.response.ResponseFile;
import kz.iitu.armarketplace.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product-photos")
public class FileController {

	private final FileService fileService;

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
