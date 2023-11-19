package com.maiko.usercenterdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiko.usercenterdemo.mapper.FriendsMapper;
import com.maiko.usercenterdemo.model.domain.Friends;
import com.maiko.usercenterdemo.service.FriendsService;
import org.springframework.stereotype.Service;

/**
* @author Maiko7
* @description 针对表【friends(好友表)】的数据库操作Service实现
* @createDate 2023-11-19 14:08:20
*/
@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends>
    implements FriendsService {

}




