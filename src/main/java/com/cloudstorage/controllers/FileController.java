package com.cloudstorage.controllers;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudstorage.models.Files;
import com.cloudstorage.models.Users;
import com.cloudstorage.services.FileService;
import com.cloudstorage.services.UserService;

@Controller
public class FileController {
	private FileService fileService;
	private UserService userService;

	public FileController(FileService fileService, UserService userService) {
		super();
		this.fileService = fileService;
		this.userService = userService;
	}

	@PostMapping("/fileUpload")
	public String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication auth)
			throws IOException {
		Users user = userService.getUser(auth.getName());
		Files currFile = new Files(null, file.getOriginalFilename(), file.getContentType(),
				Long.toString(file.getSize()), user.getUserid(), file.getBytes());

		if (file.isEmpty())
			model.addAttribute("error", "No file chosen!");
		else if (!fileService.isFileNameAvailable(currFile.getFilename()))
			model.addAttribute("error", "File of same name already exists!");
		else {
			fileService.addFile(currFile);
			model.addAttribute("success", true);
		}
		return "result";
	}

	@GetMapping("/fileDownload")
	public ResponseEntity<Resource> fileDownload(@RequestParam("filename") String filename, Model model) {
		Files currFile = fileService.getFile(filename);

		return ResponseEntity.ok()

				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + currFile.getFilename() + "\"")

				.body(new ByteArrayResource(currFile.getFiledata()));
	}

	@GetMapping("/fileDelete")
	public String fileDelete(Model model, @RequestParam Integer fileid) {
		fileService.deleteFile(fileid);
		model.addAttribute("success", true);
		return "result";
	}
}
