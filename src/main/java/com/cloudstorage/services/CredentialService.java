package com.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cloudstorage.mappers.CredentialMapper;
import com.cloudstorage.models.Credentials;
import com.cloudstorage.models.Users;

@Service
public class CredentialService {
	private CredentialMapper credentialMapper;
	private EncryptionService encryptionService;
	private UserService userService;

	public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService,
			UserService userService) {
		super();
		this.credentialMapper = credentialMapper;
		this.encryptionService = encryptionService;
		this.userService = userService;
	}

	public Credentials getCredential(Integer credentialid) {
		return credentialMapper.getCredentialById(credentialid);
	}

	public Credentials getCredential(String url) {
		return credentialMapper.getCredentialByUrl(url);
	}

	public boolean isCredentialUrlAvailable(String url) {
		return credentialMapper.getCredentialByUrl(url) == null;
	}

	public String getDecryptedPassword(Integer credentialid, Authentication auth) {
		Credentials credential = credentialMapper.getCredentialById(credentialid);
		return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
	}

	public Integer addOrUpdateCredential(Credentials currCredential, Authentication auth) {
		Users user = userService.getUser(auth.getName());

		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		String encodedKey = Base64.getEncoder().encodeToString(key);
		String encryptedPassword = "";
//		String encryptedPassword = encryptionService.encryptValue(currCredential.getPassword(), encodedKey);
//		currCredential.setPassword(encryptedPassword);
//		currCredential.setKey(encodedKey);
		currCredential.setUserid(user.getUserid());

		if (currCredential.getCredentialid() != null) {
			Credentials prevCredential = getCredential(currCredential.getCredentialid());
			encryptedPassword = encryptionService.encryptValue(currCredential.getPassword(), prevCredential.getKey());
			currCredential.setKey(prevCredential.getKey());
			currCredential.setPassword(encryptedPassword);
			return this.credentialMapper.update(currCredential);
		} else {
			encryptedPassword = encryptionService.encryptValue(currCredential.getPassword(), encodedKey);
			currCredential.setPassword(encryptedPassword);
			currCredential.setKey(encodedKey);
			return this.credentialMapper.insert(currCredential);
		}
	}

	public void deleteCredential(Integer credentialid) {
		credentialMapper.delete(credentialid);
	}

	public List<Credentials> getAllCredentials(Integer userid) {
		return credentialMapper.getAllCredentials(userid);
	}
}
