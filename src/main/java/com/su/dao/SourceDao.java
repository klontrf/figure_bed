package com.su.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SourceDao {
    @Insert("insert into source (user_id,src_path) values (#{userId},#{srcPath})")
    boolean savePic(@Param("userId") Integer userId,@Param("srcPath") String srcPath);
    @Delete("delete from source where src_path=#{filePath}")
    boolean delPic(@Param("filePath") String filePath);
}
