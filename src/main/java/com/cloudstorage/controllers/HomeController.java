package com.cloudstorage.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cloudstorage.models.Credentials;
import com.cloudstorage.models.Notes;
import com.cloudstorage.models.Users;
import com.cloudstorage.services.CredentialService;
import com.cloudstorage.services.EncryptionService;
import com.cloudstorage.services.FileService;
import com.cloudstorage.services.NoteService;
import com.cloudstorage.services.UserService;

@Controller
public class HomeController {
	private UserService userService;
	private NoteService noteService;
	private FileService fileService;
	private CredentialService credentialService;
	private EncryptionService encryptionService;

	public HomeController(UserService userService, NoteService noteService, FileService fileService,
			CredentialService credentialService, EncryptionService encryptionService) {
		super();
		this.userService = userService;
		this.noteService = noteService;
		this.fileService = fileService;
		this.credentialService = credentialService;
		this.encryptionService = encryptionService;
	}

	@GetMapping("/home")
	public String getHome(Model model, @ModelAttribute("formCredential") Credentials credential,
			@ModelAttribute("formNote") Notes note, Authentication auth) {
		Users user = userService.getUser(auth.getName());
		model.addAttribute("fileList", fileService.listAllFiles(user.getUserid()));
		model.addAttribute("noteList", noteService.getAllNotes(user.getUserid()));
		model.addAttribute("encryptionService", encryptionService);
		model.addAttribute("credentialList", credentialService.getAllCredentials(user.getUserid()));

//		HashMap<Integer, String> credentialMap = new HashMap<>();
//
//		List<Credentials> allCreds = credentialService.getAllCredentials(user.getUserid());
//		for (Credentials currCred : allCreds) {
//			Integer credentialid = currCred.getCredentialid();
//			credentialMap.put(credentialid, credentialService.getDecryptedPassword(credentialid, auth));
//			credentialMap.get(credentialid);
//		}
//		model.addAttribute("decryptedCredentialList", credentialMap);

		return "home";
	}

}
