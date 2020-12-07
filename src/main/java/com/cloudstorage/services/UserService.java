package com.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.cloudstorage.mappers.UserMapper;
import com.cloudstorage.models.Users;

@Service
public class UserService {
	private UserMapper userMapper;
	private HashService hashService;

	public UserService(UserMapper userMapper, HashService hashService) {
		super();
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	public boolean isUsernameAvailable(String username) {
		return userMapper.getUser(username) == null;
	}

	public Integer createUser(Users user) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.insert(new Users(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(),
				user.getLastname()));
	}

	public Users getUser(String username) {
		return userMapper.getUser(username);
	}
}
