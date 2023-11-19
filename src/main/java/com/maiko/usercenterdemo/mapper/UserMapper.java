package com.maiko.usercenterdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maiko.usercenterdemo.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Maiko7
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-11-16 08:32:18
* @Entity com.maiko.usercenterdemo.model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




