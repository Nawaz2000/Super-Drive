package com.cloudstorage.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cloudstorage.models.Credentials;

@Mapper
public interface CredentialMapper {
	@Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
	Credentials getCredentialById(Integer credentialid);

	@Select("SELECT * FROM CREDENTIALS WHERE url = #{url}")
	Credentials getCredentialByUrl(String url);

	@Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
	List<Credentials> getAllCredentials(Integer userid);

	@Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES (#{url},#{username},#{key},#{password},#{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialid")
	Integer insert(Credentials credential);

	@Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid}")
	Integer update(Credentials credential);

	@Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
	Integer delete(Integer credentialid);
}
