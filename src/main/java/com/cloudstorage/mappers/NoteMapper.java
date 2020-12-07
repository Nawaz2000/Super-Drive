package com.cloudstorage.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cloudstorage.models.Notes;

@Mapper
public interface NoteMapper {
	@Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
	Notes getNoteById(Integer noteid);

	@Select("SELECT * FROM NOTES WHERE notetitle = #{notetitle}")
	Notes getNoteByTitle(String notetitle);

	@Select("SELECT * FROM NOTES WHERE userid = #{userid}")
	List<Notes> getAllNotes(Integer userid);

	@Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES (#{notetitle},#{notedescription},#{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "noteid")
	Integer insert(Notes note);

	@Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE userid = #{userid}")
	Integer update(Notes note);

	@Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
	Integer delete(Integer noteid);
}
