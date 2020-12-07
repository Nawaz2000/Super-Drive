package com.cloudstorage.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.cloudstorage.models.Files;

@Mapper
public interface FileMapper {
	@Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
	Files getFileById(Integer fileid);

	@Select("SELECT * FROM FILES WHERE filename = #{filename}")
	Files getFileByName(String filename);

	@Select("SELECT * FROM FILES WHERE userid = #{userid}")
	List<Files> getAllFiles(Integer userid);

	@Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) VALUES (#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
	@Options(useGeneratedKeys = true, keyProperty = "fileid")
	Integer insert(Files file);

	@Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
	Integer delete(Integer fileid);
}
