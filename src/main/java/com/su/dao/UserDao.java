package com.su.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.su.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper

public interface UserDao extends BaseMapper<User> {
   @Select("select src_path from source where user_id=#{id}")
   List<String> getUserSource(@Param("id") int id);
}