package com.cloudstorage.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudstorage.models.Credentials;
import com.cloudstorage.services.CredentialService;

@Controller
public class CredentialController {
	private CredentialService credentialService;

	public CredentialController(CredentialService credentialService) {
		super();
		this.credentialService = credentialService;
	}

	@PostMapping("/credentialUpload")
	public String credentialUpload(@ModelAttribute("formCredential") Credentials credential, Model model,
			Authentication auth) {
		Integer outcome = credentialService.addOrUpdateCredential(credential, auth);
		if (outcome > 0)
			model.addAttribute("success", true);
		else
			model.addAttribute("success", false);
		return "result";
	}

	@GetMapping("/credentialDelete")
	public String credentialDelete(@RequestParam("credentialid") Integer credentialid, Model model) {
		credentialService.deleteCredential(credentialid);
		model.addAttribute("success", true);
		return "result";
	}
}
