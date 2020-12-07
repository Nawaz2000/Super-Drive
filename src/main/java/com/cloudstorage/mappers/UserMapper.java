package com.cloudstorage.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.cloudstorage.models.Users;

@Mapper
public interface UserMapper {
	@Select("SELECT * FROM USERS WHERE username = #{username}")
	Users getUser(String username);

	@Insert("INSERT INTO USERS (username,salt,password,firstname,lastname)"
			+ "VALUES(#{username},#{salt},#{password},#{firstname},#{lastname})")
	@Options(useGeneratedKeys = true, keyProperty = "userid")
	Integer insert(Users user);
}
